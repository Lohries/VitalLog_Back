package com.vitallog.vitalog_back.application.dto.negotiation;

import com.vitallog.vitalog_back.domain.enums.NegotiationStatus;
import com.vitallog.vitalog_back.domain.enums.Priority;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record NegotiationRequest(
        @NotBlank String client,
        String description,
        BigDecimal estimatedValue,
        String attendance,
        String nf,
        String xml,
        NegotiationStatus status,
        Priority priority,
        UUID linkedServiceId,
        List<AttachmentRequest> attachments
) {}
