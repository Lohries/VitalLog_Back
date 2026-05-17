package com.vitallog.vitalog_back.application.dto.product;

import com.vitallog.vitalog_back.domain.entity.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        int quantity,
        BigDecimal price,
        String description,
        Boolean active
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getQuantity(),
                product.getPrice(),
                product.getDescription(),
                product.getActive()
        );
    }
}
