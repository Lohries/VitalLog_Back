package com.vitallog.vitalog_back.application.dto.negotiation;

import com.vitallog.vitalog_back.domain.entity.NegotiationAttachment;

import java.util.UUID;

public record AttachmentResponse(
        UUID id,
        String name,
        Long size
) {
    public static AttachmentResponse from(NegotiationAttachment attachment) {
        return new AttachmentResponse(attachment.getId(), attachment.getName(), attachment.getSize());
    }
}
