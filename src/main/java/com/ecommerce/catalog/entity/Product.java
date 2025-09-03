package com.ecommerce.catalog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ecommerce.catalog.util.DateTimeProvider;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue
    private UUID productId;

    private String name;
    private String description;
    private BigDecimal price;
    private String category;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "boolean default true")
    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean active = true;

    private LocalDateTime createdAt = DateTimeProvider.now();
    private LocalDateTime updatedAt = DateTimeProvider.now();
}
