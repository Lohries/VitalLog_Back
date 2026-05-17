package com.vitallog.vitalog_back.application.port;

import com.vitallog.vitalog_back.application.dto.company.CompanyAnalyticsResponse;
import com.vitallog.vitalog_back.domain.entity.User;

import java.util.UUID;

public interface CompanyAnalyticsPort {
    CompanyAnalyticsResponse getCompanyAnalytics(UUID companyId, User user);
}
