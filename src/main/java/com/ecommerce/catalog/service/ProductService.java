package com.ecommerce.catalog.service;

import com.ecommerce.catalog.entity.Product;
import com.ecommerce.catalog.exception.ProductNotFoundException;
import com.ecommerce.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class ProductService {
	
	private final ProductValidator productValidator;
    private final ProductRepository productRepository;

    public ProductService(ProductValidator productValidator, ProductRepository productRepository) {
        this.productValidator = productValidator;
		this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
    	productValidator.validate(product);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Product getProduct(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }


    public Product updateProduct(UUID id, Product product) {
        Product existing = getProduct(id);
        productValidator.validate(product);
        
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setCategory(product.getCategory());
        existing.setActive(product.isActive());
        return productRepository.save(existing);
    }

    public Product deactivateProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        product.setActive(false);
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

}
