package com.ecommerce.catalog.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventProducer {

    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

    @Value("${app.topics.product-events}")
    private String productEventsTopic;

    public void productCreated(ProductPayload payload) {
        send("PRODUCT_CREATED", payload);
    }

    public void productUpdated(ProductPayload payload) {
        send("PRODUCT_UPDATED", payload);
    }

    public void productDeactivated(ProductPayload payload) {
        send("PRODUCT_DEACTIVATED", payload);
    }

    private void send(String type, ProductPayload payload) {
        String key = payload.getProductId().toString();

        ProductEvent event = ProductEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(type)
                .occurredAt(Instant.now())
                .traceId(getTraceId())
                .product(payload)
                .build();

        kafkaTemplate.send(productEventsTopic, key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish {} for productId={} on topic={}",
                                type, key, productEventsTopic, ex);
                    } else {
                        log.info("Published {} for productId={} to {}-p{}@{}",
                                type,
                                key,
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }

    private String getTraceId() {
        String mdc = MDC.get("traceId");
        return (mdc != null && !mdc.isBlank()) ? mdc : UUID.randomUUID().toString();
    }
}
