package com.vitallog.vitalog_back.web.controller;

import com.vitallog.vitalog_back.application.dto.product.ProductRequest;
import com.vitallog.vitalog_back.application.dto.product.ProductResponse;
import com.vitallog.vitalog_back.application.port.ProductServicePort;
import com.vitallog.vitalog_back.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServicePort productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(productService.findAll(user));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody ProductRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(productService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        productService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
