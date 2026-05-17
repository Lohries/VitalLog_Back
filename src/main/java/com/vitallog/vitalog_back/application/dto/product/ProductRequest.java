package com.vitallog.vitalog_back.application.dto.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank @Size(max = 30) String name,
        @Min(0) int quantity,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @Size(max = 500) String description
) {}
