package com.ecommerce.catalog.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDto {

    @NotBlank(message = "Name is required")
    @Size(max = 60, message = "Name must be less than 60 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only alphabets and spaces")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "100.00", message = "Price must be at least 100")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid decimal number")
    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "Category is required")
    private String category;

    private Boolean active;
}

