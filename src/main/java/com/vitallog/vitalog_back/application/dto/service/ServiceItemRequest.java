package com.vitallog.vitalog_back.application.dto.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ServiceItemRequest(
        @NotNull UUID productId,
        @Min(1) int quantity
) {}
