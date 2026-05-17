package com.vitallog.vitalog_back.application.port;

import com.vitallog.vitalog_back.application.dto.analytics.AnalyticsResponse;
import com.vitallog.vitalog_back.domain.entity.User;

public interface AnalyticsServicePort {
    AnalyticsResponse getAnalytics(User user);
}
