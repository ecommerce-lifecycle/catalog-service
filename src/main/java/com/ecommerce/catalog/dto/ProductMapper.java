package com.ecommerce.catalog.dto;

import com.ecommerce.catalog.entity.Product;
import com.ecommerce.catalog.util.DateTimeProvider;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        if (product == null) return null;

        return ProductDto.builder()
                .id(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .active(product.getActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static Product toEntity(ProductCreateRequestDto dto) {
        if (dto == null) return null;

        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .createdAt(DateTimeProvider.now())
                .updatedAt(DateTimeProvider.now())
                .build();
    }

    public static void updateEntity(Product product, ProductUpdateRequestDto dto) {
        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getCategory() != null) product.setCategory(dto.getCategory());
        if (dto.getActive() != null) product.setActive(dto.getActive());
        product.setUpdatedAt(DateTimeProvider.now());
    }
}

