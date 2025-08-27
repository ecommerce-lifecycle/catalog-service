package com.ecommerce.catalog.service;

import com.ecommerce.catalog.entity.Product;
import com.ecommerce.catalog.exception.ProductNotFoundException;
import com.ecommerce.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
 
 
@Service
@Slf4j
public class ProductService {

	private final ProductValidator productValidator;

    private final ProductRepository productRepository;
 
    public ProductService(ProductValidator productValidator, ProductRepository productRepository) {
        this.productValidator = productValidator;
		this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
    	productValidator.validate(product);
    	log.debug("Creating product with data: {}", product);
        return productRepository.save(product);
    }
    public List<Product> getAllProducts() {
    	log.info("Fetching all products");
        return productRepository.findAll();
    }

    public Product getProduct(UUID id) {
    	log.info("Fetching product with id: {}", id);
    	       return productRepository.findById(id)
    	            .orElseThrow(() -> {
    	                log.error("Product not found for id: {}", id);
    	                return new ProductNotFoundException("Product not found");
    	            });
    	    }

    public Product updateProduct(UUID id, Product product) {
        productValidator.validate(product);
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
        existing.setActive(product.isActive());
        log.debug("Updated product data={}", existing);
        return productRepository.save(existing);
    }
 
    public Product deactivateProduct(UUID id) {
        log.info("Deactivating product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found for id: {}", id);
                    return new ProductNotFoundException("Product not found");

                });
        product.setActive(false);
        product.setUpdatedAt(LocalDateTime.now());
        log.debug("Product deactivated: {}", product);
        return productRepository.save(product);
    }
}

 