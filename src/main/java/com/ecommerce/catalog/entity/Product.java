package com.ecommerce.catalog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

	@NotBlank(message = "Name cannot be blank")
	@Size(max = 100, message = "Name must be less than 100 characters")
	private String name;

	@Size(max = 500, message = "Description must be less than 500 characters")
	private String description;

	@NotNull(message = "Price cannot be blank")
	@Positive(message = "Price must be greater than 0")
	private BigDecimal price;

	@NotBlank(message = "Category cannot be blank")
	private String category;

	@Builder.Default
	@Column(nullable = false, columnDefinition = "boolean default true")
	@JsonSetter(nulls = Nulls.SKIP)
	private Boolean active = true;


	private LocalDateTime createdAt = LocalDateTime.now();

	private LocalDateTime updatedAt = LocalDateTime.now();
}