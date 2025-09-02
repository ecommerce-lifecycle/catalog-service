package com.ecommerce.catalog.exception;

import com.ecommerce.catalog.api.ApiResponse;

import org.hibernate.LazyInitializationException;
import org.springframework.dao.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 🔴 Utility method to build ApiResponse
    private ResponseEntity<ApiResponse<Object>> buildResponse(HttpStatus status, String message) {
        ApiResponse<Object> response = ApiResponse.builder()
                .status("ERROR")
                .code(status.value())
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, status);
    }
    
    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidProduct(InvalidProductException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", "ERROR");
        error.put("code", 400);
        error.put("message", ex.getMessage());
        error.put("timestamp", LocalDateTime.now());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", "ERROR");
        error.put("code", 500);
        error.put("message", "Unexpected error: " + ex.getMessage());
        error.put("timestamp", LocalDateTime.now());

        return ResponseEntity.internalServerError().body(error);
    }

    // -------------------- CREATE --------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildResponse(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Database constraint violation: " + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        String errors = ex.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining(", "));
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed: " + errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidJson(HttpMessageNotReadableException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Malformed JSON request: " + ex.getMessage());
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ApiResponse<Object>> handleTransactionSystem(TransactionSystemException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction failed: " + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<Object>> handleNullPointer(NullPointerException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Null value error: " + ex.getMessage());
    }

    // -------------------- GET ALL --------------------

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataAccess(DataAccessException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Database access error: " + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(LazyInitializationException.class)
    public ResponseEntity<ApiResponse<Object>> handleLazyInit(LazyInitializationException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Lazy loading failed: " + ex.getMessage());
    }

    // -------------------- GET BY ID --------------------

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid argument: " + ex.getMessage());
    }

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class, ProductNotFoundException.class})
    public ResponseEntity<ApiResponse<Object>> handleEntityNotFound(RuntimeException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage() != null ? ex.getMessage() : "Entity not found");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidDataAccess(InvalidDataAccessApiUsageException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid DB access: " + ex.getMessage());
    }

    // -------------------- UPDATE --------------------

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ApiResponse<Object>> handleOptimisticLock(OptimisticLockException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Concurrent update error: " + ex.getMessage());
    }

    // -------------------- DEACTIVATE --------------------

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalState(IllegalStateException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // -------------------- FALLBACK --------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage());
    }
}
