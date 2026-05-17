package com.vitallog.vitalog_back.application.dto.service;

import com.vitallog.vitalog_back.domain.entity.VitalService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ServiceResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        List<ServiceItemResponse> items
) {
    public static ServiceResponse from(VitalService service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getItems().stream().map(ServiceItemResponse::from).toList()
        );
    }
}
