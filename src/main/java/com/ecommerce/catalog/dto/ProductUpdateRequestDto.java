package com.ecommerce.catalog.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDto {
	
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only alphabets and spaces")
    private String name;

    private String description;

    @DecimalMin(value = "100.00", message = "Price must be at least 100")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid decimal number")
    private BigDecimal price;
    private String category;
    private Boolean active;
}
