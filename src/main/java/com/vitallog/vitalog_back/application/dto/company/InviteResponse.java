package com.vitallog.vitalog_back.application.dto.company;

import com.vitallog.vitalog_back.domain.entity.CompanyInvite;

import java.time.LocalDateTime;
import java.util.UUID;

public record InviteResponse(
        UUID id,
        UUID companyId,
        String companyName,
        String invitedByName,
        String invitedEmail,
        String status,
        LocalDateTime createdAt
) {
    public static InviteResponse from(CompanyInvite i) {
        return new InviteResponse(
                i.getId(),
                i.getCompany().getId(),
                i.getCompany().getName(),
                i.getInvitedBy().getName(),
                i.getInvitedEmail(),
                i.getStatus().name(),
                i.getCreatedAt()
        );
    }
}
