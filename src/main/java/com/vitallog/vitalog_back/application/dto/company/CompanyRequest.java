package com.vitallog.vitalog_back.application.dto.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CompanyRequest(
        @NotBlank @Size(max = 100) String name,
        @Size(max = 300) String description
) {}
