package com.ecommerce.catalog.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecommerce.catalog.entity.Product;
import com.ecommerce.catalog.exception.InvalidProductException;

@Component
public class ProductValidator {

	List<String> validCategories = List.of("Mobile", "Electronics");
	
    public void validate(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(100)) < 0) {
            throw new InvalidProductException("Price must be at least 100");
        }

        if (!validCategories.contains(product.getCategory())) {
            throw new InvalidProductException("Invalid category. Allowed: Mobile, Electronics");
        }
    }
}