package com.ecommerce.catalog.controller;

import com.ecommerce.catalog.api.ApiResponse;
import com.ecommerce.catalog.dto.ProductDto;
import com.ecommerce.catalog.mapper.ProductMapper;
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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> create(@Valid @RequestBody ProductDto productDto) {
    	log.info("Incoming productDto: {}", productDto);
    	Product created = productService.createProduct(ProductMapper.toEntity(productDto));
    	ResponseEntity<ApiResponse<ProductDto>> prod =  ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ProductDto>builder()
                        .status("SUCCESS")
                        .code(HttpStatus.CREATED.value())
                        .message("Product created successfully")
                        .data(ProductMapper.toDto(created))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    	log.info("Incoming prod: {}", prod);
    	return prod;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAll() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(
                ApiResponse.<List<ProductDto>>builder()
                        .status("SUCCESS")
                        .code(HttpStatus.OK.value())
                        .message("Products fetched successfully")
                        .data(products.stream()
                                .map(ProductMapper::toDto)
                                .toList())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getById(@PathVariable UUID id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(
                ApiResponse.<ProductDto>builder()
                        .status("SUCCESS")
                        .code(HttpStatus.OK.value())
                        .message("Product fetched successfully")
                        .data(ProductMapper.toDto(product))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProductDto productDto) {

        Product updated = productService.updateProduct(id, ProductMapper.toEntity(productDto));
        return ResponseEntity.ok(
                ApiResponse.<ProductDto>builder()
                        .status("SUCCESS")
                        .code(HttpStatus.OK.value())
                        .message("Product updated successfully")
                        .data(ProductMapper.toDto(updated))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<ProductDto>> deactivate(@PathVariable UUID id) {
        Product deactivated = productService.deactivateProduct(id);
        return ResponseEntity.ok(
                ApiResponse.<ProductDto>builder()
                        .status("SUCCESS")
                        .code(HttpStatus.OK.value())
                        .message("Product deactivated successfully")
                        .data(ProductMapper.toDto(deactivated))
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}

