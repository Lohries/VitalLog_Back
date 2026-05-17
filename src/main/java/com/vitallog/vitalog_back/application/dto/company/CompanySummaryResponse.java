package com.vitallog.vitalog_back.application.dto.company;

import com.vitallog.vitalog_back.domain.entity.Company;
import com.vitallog.vitalog_back.domain.entity.CompanyMember;

import java.time.LocalDateTime;
import java.util.UUID;

public record CompanySummaryResponse(
        UUID id,
        String name,
        String description,
        String ownerName,
        String myRole,
        int memberCount,
        LocalDateTime createdAt
) {
    public static CompanySummaryResponse from(CompanyMember m, int memberCount) {
        Company c = m.getCompany();
        return new CompanySummaryResponse(
                c.getId(), c.getName(), c.getDescription(),
                c.getOwner().getName(), m.getRole().name(), memberCount, c.getCreatedAt()
        );
    }
}
