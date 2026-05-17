package com.vitallog.vitalog_back.application.dto.analytics;

import java.math.BigDecimal;

public record AnalyticsResponse(
        long totalClients,
        BigDecimal totalRevenue,
        long totalItems,
        long totalProductTypes,
        String currencySymbol
) {}
