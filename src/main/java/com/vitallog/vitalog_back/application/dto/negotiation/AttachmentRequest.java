package com.vitallog.vitalog_back.application.dto.negotiation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AttachmentRequest(
        @NotBlank String name,
        @NotNull Long size
) {}
