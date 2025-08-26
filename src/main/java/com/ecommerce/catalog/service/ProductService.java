package com.ecommerce.catalog.service;

import com.ecommerce.catalog.entity.Product;
import com.ecommerce.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateProduct(UUID id, Product product) {
        Product existing = getProduct(id);
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setCategory(product.getCategory());
        existing.setActive(product.isActive());
        return productRepository.save(existing);
    }

    public void deactivateProduct(UUID id) {
        Product existing = getProduct(id);
        existing.setActive(false);
        productRepository.save(existing);
    }
}
