package com.ecommerce.catalog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String status;     // SUCCESS / ERROR / FAILURE
    private int code;          // HTTP Status code
    private String message;    // Friendly message
    private T data;            // Actual response body
    private LocalDateTime timestamp;
}
