package com.vitallog.vitalog_back.application.dto.sale;

import com.vitallog.vitalog_back.domain.entity.ProductSale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductSaleResponse(
        UUID id,
        UUID productId,
        String productName,
        int quantity,
        String client,
        BigDecimal pricePerUnit,
        BigDecimal total,
        String status,
        LocalDateTime createdAt
) {
    public static ProductSaleResponse from(ProductSale sale) {
        return new ProductSaleResponse(
                sale.getId(),
                sale.getProduct() != null ? sale.getProduct().getId() : null,
                sale.getProduct() != null ? sale.getProduct().getName() : null,
                sale.getQuantity(),
                sale.getClient(),
                sale.getPricePerUnit(),
                sale.getTotal(),
                sale.getStatus(),
                sale.getCreatedAt()
        );
    }
}
