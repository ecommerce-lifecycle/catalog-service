package com.ecommerce.catalog.controller;

import com.ecommerce.catalog.dto.ApiResponse;
import com.ecommerce.catalog.dto.ProductCreateRequestDto;
import com.ecommerce.catalog.dto.ProductDto;
import com.ecommerce.catalog.dto.ProductMapper;
import com.ecommerce.catalog.dto.ProductUpdateRequestDto;
import com.ecommerce.catalog.entity.Product;
import com.ecommerce.catalog.service.ProductService;
import com.ecommerce.catalog.util.DateTimeProvider;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> create(@Valid @RequestBody ProductCreateRequestDto productDto) {
    	Product created = productService.createProduct(productDto);
    	ResponseEntity<ApiResponse<ProductDto>> prod =  ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ProductDto>builder()
                        .status("SUCCESS")
                        .code(HttpStatus.CREATED.value())
                        .message("Product created successfully")
                        .data(ProductMapper.toDto(created))
                        .timestamp(DateTimeProvider.now())
                        .build()
        );
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
                        .timestamp(DateTimeProvider.now())
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
                        .timestamp(DateTimeProvider.now())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProductUpdateRequestDto productDto) {

        Product updated = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(
                ApiResponse.<ProductDto>builder()
                        .status("SUCCESS")
                        .code(HttpStatus.OK.value())
                        .message("Product updated successfully")
                        .data(ProductMapper.toDto(updated))
                        .timestamp(DateTimeProvider.now())
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
                        .timestamp(DateTimeProvider.now())
                        .build()
        );
    }
}

