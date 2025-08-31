package com.ecommerce.catalog.mapper;

import java.time.LocalDateTime;

import com.ecommerce.catalog.dto.ProductDto;
import com.ecommerce.catalog.entity.Product;

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

    public static Product toEntity(ProductDto dto) {
        if (dto == null) return null;

        return Product.builder()
                .productId(dto.getId())     
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .createdAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
