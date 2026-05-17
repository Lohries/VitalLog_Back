package com.vitallog.vitalog_back.application.dto.user;

import com.vitallog.vitalog_back.domain.entity.User;
import com.vitallog.vitalog_back.domain.entity.UserPreferences;

import java.util.UUID;

public record UserProfileResponse(
        UUID id,
        String name,
        String email,
        String currency,
        String theme,
        boolean notificationsEnabled
) {
    public static UserProfileResponse from(User user, UserPreferences prefs) {
        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                prefs != null ? prefs.getCurrency() : "R$",
                prefs != null ? prefs.getTheme() : "light",
                prefs == null || prefs.isNotificationsEnabled()
        );
    }
}
