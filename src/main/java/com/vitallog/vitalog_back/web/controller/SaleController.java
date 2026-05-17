package com.vitallog.vitalog_back.web.controller;

import com.vitallog.vitalog_back.application.dto.sale.*;
import com.vitallog.vitalog_back.application.port.SaleServicePort;
import com.vitallog.vitalog_back.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleServicePort saleService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductSaleResponse>> findAllProductSales(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(saleService.findAllProductSales(user));
    }

    @PostMapping("/products")
    public ResponseEntity<ProductSaleResponse> createProductSale(
            @Valid @RequestBody ProductSaleRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.createProductSale(request, user));
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceSaleResponse>> findAllServiceSales(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(saleService.findAllServiceSales(user));
    }

    @PostMapping("/services")
    public ResponseEntity<ServiceSaleResponse> createServiceSale(
            @Valid @RequestBody ServiceSaleRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.createServiceSale(request, user));
    }
}
