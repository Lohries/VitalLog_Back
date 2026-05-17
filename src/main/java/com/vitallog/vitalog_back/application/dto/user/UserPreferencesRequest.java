package com.vitallog.vitalog_back.application.dto.user;

public record UserPreferencesRequest(
        Boolean notificationsEnabled,
        String theme,
        String currency
) {}
