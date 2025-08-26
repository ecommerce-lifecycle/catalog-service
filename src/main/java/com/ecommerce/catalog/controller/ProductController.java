package com.ecommerce.catalog.controller;

import com.ecommerce.catalog.entity.Product;
import com.ecommerce.catalog.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable UUID id) {
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable UUID id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @PatchMapping("/{id}/deactivate")
    public void deactivate(@PathVariable UUID id) {
        productService.deactivateProduct(id);
    }
}
