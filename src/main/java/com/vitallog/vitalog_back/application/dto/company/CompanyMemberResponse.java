package com.vitallog.vitalog_back.application.dto.company;

import com.vitallog.vitalog_back.domain.entity.CompanyMember;

import java.time.LocalDateTime;
import java.util.UUID;

public record CompanyMemberResponse(
        UUID userId,
        String name,
        String email,
        String role,
        LocalDateTime joinedAt
) {
    public static CompanyMemberResponse from(CompanyMember m) {
        return new CompanyMemberResponse(
                m.getUser().getId(),
                m.getUser().getName(),
                m.getUser().getEmail(),
                m.getRole().name(),
                m.getJoinedAt()
        );
    }
}
