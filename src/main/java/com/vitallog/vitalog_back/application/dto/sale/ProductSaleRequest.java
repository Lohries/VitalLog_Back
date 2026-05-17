package com.vitallog.vitalog_back.application.dto.sale;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProductSaleRequest(
        @NotNull UUID productId,
        @Min(1) int quantity,
        String client
) {}
