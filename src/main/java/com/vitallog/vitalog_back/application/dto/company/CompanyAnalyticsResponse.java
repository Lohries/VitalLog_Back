package com.vitallog.vitalog_back.application.dto.company;

import java.math.BigDecimal;
import java.util.List;

public record CompanyAnalyticsResponse(
        int memberCount,
        long totalClients,
        BigDecimal totalRevenue,
        long totalItems,
        List<MemberPerformanceResponse> performances
) {
    public record MemberPerformanceResponse(
            String name,
            String email,
            long clients,
            BigDecimal revenue,
            long items
    ) {}
}
