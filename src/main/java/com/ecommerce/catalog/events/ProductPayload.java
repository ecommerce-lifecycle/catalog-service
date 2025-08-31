package com.ecommerce.catalog.events;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductPayload {
    private UUID productId;
    private String name;
    private BigDecimal price;
    private String category;
    private boolean active;
}
