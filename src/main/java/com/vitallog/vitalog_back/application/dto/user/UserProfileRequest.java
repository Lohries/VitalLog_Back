package com.vitallog.vitalog_back.application.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserProfileRequest(
        @NotBlank String name,
        String currency
) {}
