package com.ecommerce.catalog.controller;

import com.ecommerce.catalog.api.ApiResponse;
import com.ecommerce.catalog.entity.Product;
import com.ecommerce.catalog.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> create(@Valid @RequestBody Product product) {
        Product created = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<Product>builder()
                        .status("SUCCESS")
                        .code(HttpStatus.CREATED.value())
                        .message("Product created successfully")
                        .data(created)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAll() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(
                ApiResponse.<List<Product>>builder()
                        .status("SUCCESS")
                        .code(HttpStatus.OK.value())
                        .message("Products fetched successfully")
                        .data(products)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getById(@PathVariable UUID id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(
                ApiResponse.<Product>builder()
                        .status("SUCCESS")
                        .code(HttpStatus.OK.value())
                        .message("Product fetched successfully")
                        .data(product)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> update(@PathVariable UUID id, @Valid @RequestBody Product product) {
        Product updated = productService.updateProduct(id, product);
        return ResponseEntity.ok(
                ApiResponse.<Product>builder()
                        .status("SUCCESS")
                        .code(HttpStatus.OK.value())
                        .message("Product updated successfully")
                        .data(updated)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Product>> deactivate(@PathVariable UUID id) {
        Product deactivated = productService.deactivateProduct(id);
        return ResponseEntity.ok(
                ApiResponse.<Product>builder()
                        .status("SUCCESS")
                        .code(HttpStatus.OK.value())
                        .message("Product deactivated successfully")
                        .data(deactivated)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
