package com.ecommerce.catalog.service;

import com.ecommerce.catalog.entity.Product;
import com.ecommerce.catalog.events.ProductEventProducer;
import com.ecommerce.catalog.events.ProductPayload;
import com.ecommerce.catalog.exception.InvalidProductException;
import com.ecommerce.catalog.exception.ProductNotFoundException;
import com.ecommerce.catalog.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductService {

    private final ProductValidator productValidator;
    private final ProductRepository productRepository;
    private final ProductEventProducer producer;

    public ProductService(ProductValidator productValidator, ProductRepository productRepository, ProductEventProducer producer) {
        this.productValidator = productValidator;
        this.productRepository = productRepository;
		this.producer = producer;
    }

    // ---------------- CREATE ----------------
    @Transactional
    public Product createProduct(Product product) {
        if (product == null) {
            throw new InvalidProductException("Product cannot be null");
        }

        productValidator.validate(product);

        try {
            log.debug("Creating product with data: {}", product);
            Product saved =  productRepository.save(product);
            producer.productCreated(toPayload(saved));
            return saved;
        } catch (DataIntegrityViolationException e) {
            log.error("Constraint violation while saving product: {}", e.getMessage());
            throw e; 
        } catch (TransactionSystemException e) {
            log.error("Transaction failed while saving product: {}", e.getMessage());
            throw e;
        }
    }

    // ---------------- GET ALL ----------------
    public List<Product> getAllProducts() {
        try {
            log.info("Fetching all products");
            return productRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching products: {}", e.getMessage());
            throw e; // DataAccessException / LazyInitializationException bubble up
        }
    }

    // ---------------- GET BY ID ----------------
    public Product getProduct(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        try {
            log.info("Fetching product with id: {}", id);
            return productRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Product not found for id: {}", id);
                        return new ProductNotFoundException("Product not found");
                    });
        } catch (IllegalArgumentException e) {
            log.error("Invalid argument for getProduct: {}", e.getMessage());
            throw e; // caught in exception handler
        } catch (InvalidDataAccessApiUsageException e) {
            log.error("Invalid UUID format for product id: {}", id);
            throw e;
        } catch (EntityNotFoundException e) {
            log.error("Entity not found for id: {}", id);
            throw e;
        }
    }

    // ---------------- UPDATE ----------------
    @Transactional
    public Product updateProduct(UUID id, Product product) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (product == null) {
            throw new InvalidProductException("Product data cannot be null");
        }

        productValidator.validate(product);

        try {
            log.info("Updating product with id: {}", id);
            Product existing = productRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Product not found for id: {}", id);
                        return new ProductNotFoundException("Product not found");
                    });

            existing.setName(product.getName());
            existing.setDescription(product.getDescription());
            existing.setPrice(product.getPrice());
            existing.setCategory(product.getCategory());
            existing.setActive(product.getActive());
            existing.setUpdatedAt(LocalDateTime.now());

            Product updated = productRepository.save(existing);

            // Kafka event publish
            producer.productUpdated(toPayload(updated));

            return updated;

        } catch (OptimisticLockException e) {
            log.error("Concurrent update detected for id {}: {}", id, e.getMessage());
            throw e;
        } catch (DataIntegrityViolationException e) {
            log.error("Unique constraint violation while updating product: {}", e.getMessage());
            throw e;
        } catch (TransactionSystemException e) {
            log.error("Transaction failed while updating product: {}", e.getMessage());
            throw e;
        }
    }


    // ---------------- DEACTIVATE ----------------
    @Transactional
    public Product deactivateProduct(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        try {            
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Product not found for id: {}", id);
                        return new ProductNotFoundException("Product not found");
                    });

            if (!product.getActive()) {
                log.warn("Product with id {} is already deactivated", id);
                throw new IllegalStateException("Product is already deactivated");
            }
            
            log.info("Deactivating product with id: {}", id);
            product.setActive(false);
            product.setUpdatedAt(LocalDateTime.now());

            Product deactivated = productRepository.save(product);

            // Kafka event publish
            producer.productDeactivated(toPayload(deactivated));

            return deactivated;

        } catch (TransactionSystemException e) {
            log.error("Transaction failed while deactivating product: {}", e.getMessage());
            throw e;
        }
    }

    
    private ProductPayload toPayload(Product p) {
        return ProductPayload.builder()
                .productId(p.getProductId())
                .name(p.getName())
                .price(p.getPrice())
                .category(p.getCategory())
                .active(p.getActive())
                .build();
    }
}
