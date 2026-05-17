package com.vitallog.vitalog_back.application.dto.company;

import com.vitallog.vitalog_back.domain.entity.Company;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CompanyDetailResponse(
        UUID id,
        String name,
        String description,
        String ownerName,
        String myRole,
        List<CompanyMemberResponse> members,
        List<InviteResponse> pendingInvites,
        LocalDateTime createdAt
) {
    public static CompanyDetailResponse from(Company c, String myRole,
                                             List<CompanyMemberResponse> members,
                                             List<InviteResponse> pendingInvites) {
        return new CompanyDetailResponse(
                c.getId(), c.getName(), c.getDescription(),
                c.getOwner().getName(), myRole, members, pendingInvites, c.getCreatedAt()
        );
    }
}
