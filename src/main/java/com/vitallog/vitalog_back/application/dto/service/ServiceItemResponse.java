package com.vitallog.vitalog_back.application.dto.service;

import com.vitallog.vitalog_back.domain.entity.ServiceItem;

import java.util.UUID;

public record ServiceItemResponse(
        UUID id,
        UUID productId,
        String productName,
        int quantity
) {
    public static ServiceItemResponse from(ServiceItem item) {
        return new ServiceItemResponse(
                item.getId(),
                item.getProduct() != null ? item.getProduct().getId() : null,
                item.getProduct() != null ? item.getProduct().getName() : null,
                item.getQuantity()
        );
    }
}
