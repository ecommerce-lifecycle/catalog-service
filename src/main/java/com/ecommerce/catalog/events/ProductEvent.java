package com.ecommerce.catalog.events;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ProductEvent {
    private String eventId;     // UUID string
    private String eventType;   // PRODUCT_CREATED | PRODUCT_UPDATED | PRODUCT_DEACTIVATED
    private Instant occurredAt; // ISO-8601 instant
    private ProductPayload product;
    private String traceId;     // for tracing/correlation
}
