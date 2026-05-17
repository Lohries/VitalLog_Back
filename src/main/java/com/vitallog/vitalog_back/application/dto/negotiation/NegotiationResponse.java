package com.vitallog.vitalog_back.application.dto.negotiation;

import com.vitallog.vitalog_back.domain.entity.Negotiation;
import com.vitallog.vitalog_back.domain.enums.NegotiationStatus;
import com.vitallog.vitalog_back.domain.enums.Priority;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record NegotiationResponse(
        UUID id,
        String client,
        String description,
        BigDecimal estimatedValue,
        String attendance,
        String nf,
        String xml,
        NegotiationStatus status,
        Priority priority,
        UUID linkedServiceId,
        LocalDateTime createdAt,
        LocalDateTime invoicedAt,
        List<AttachmentResponse> attachments
) {
    public static NegotiationResponse from(Negotiation negotiation) {
        return new NegotiationResponse(
                negotiation.getId(),
                negotiation.getClient(),
                negotiation.getDescription(),
                negotiation.getEstimatedValue(),
                negotiation.getAttendance(),
                negotiation.getNf(),
                negotiation.getXml(),
                negotiation.getStatus(),
                negotiation.getPriority(),
                negotiation.getLinkedServiceId(),
                negotiation.getCreatedAt(),
                negotiation.getInvoicedAt(),
                negotiation.getAttachments().stream().map(AttachmentResponse::from).toList()
        );
    }
}
