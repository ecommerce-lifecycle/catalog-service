package com.ecommerce.catalog.exception;

import com.ecommerce.catalog.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Product not found, etc.
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(NoSuchElementException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .status("ERROR")
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage() != null ? ex.getMessage() : "Resource not found")
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Generic fallback for unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .status("ERROR")
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred: " + ex.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> hanleProductNotFound(ProductNotFoundException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
        		.status("ERROR")
        		.code(HttpStatus.NOT_FOUND.value())
        		.message(ex.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
        
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiResponse<Object> response = ApiResponse.builder()
        		.status("ERROR")
        		.code(HttpStatus.BAD_REQUEST.value())
        		.message(errors)
        		.data(null)
        		.timestamp(LocalDateTime.now())
        		.build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidProduct(InvalidProductException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
        		.status("ERROR")
        		.code(HttpStatus.BAD_REQUEST.value())
        		.message(ex.getMessage())
        		.data(null)
        		.timestamp(LocalDateTime.now())
        		.build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    
}
