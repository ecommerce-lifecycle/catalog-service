package com.ecommerce.catalog.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ecommerce.catalog.dto.ProductCreateRequestDto;
import com.ecommerce.catalog.dto.ProductUpdateRequestDto;
import com.ecommerce.catalog.exception.InvalidProductException;

@Component
public class ProductValidator {

    private final List<String> validCategories = List.of(
            "Mobile", "Electronics", "Laptop", "Tablet", "Smartwatch",
            "Headphones", "Television", "Camera", "Appliance",
            "Furniture", "Clothing", "Footwear"
    );

    public void validateCreateRequest(ProductCreateRequestDto dto) {
        validateCategory(dto.getCategory());
    }

    public void validateUpdateRequest(ProductUpdateRequestDto dto) {
        if (dto.getName() == null &&
            dto.getDescription() == null &&
            dto.getPrice() == null &&
            dto.getCategory() == null &&
            dto.getActive() == null) {
            throw new InvalidProductException("At least one field must be provided for update");
        }
        if (dto.getCategory() != null) {
            validateCategory(dto.getCategory());
        }
    }

    private void validateCategory(String category) {
        if (!validCategories.contains(category)) {
            throw new InvalidProductException("Invalid category. Allowed: " + validCategories);
        }
    }
}
