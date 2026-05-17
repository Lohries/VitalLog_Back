package com.vitallog.vitalog_back.application.dto.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ServiceRequest(
        @NotBlank String name,
        @Size(max = 500) String description,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        List<ServiceItemRequest> items
) {}
