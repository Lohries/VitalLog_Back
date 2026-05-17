package com.vitallog.vitalog_back.application.port;

import com.vitallog.vitalog_back.application.dto.user.ChangePasswordRequest;
import com.vitallog.vitalog_back.application.dto.user.UserPreferencesRequest;
import com.vitallog.vitalog_back.application.dto.user.UserProfileRequest;
import com.vitallog.vitalog_back.application.dto.user.UserProfileResponse;
import com.vitallog.vitalog_back.domain.entity.User;

public interface UserServicePort {
    UserProfileResponse getProfile(User user);
    UserProfileResponse updateProfile(UserProfileRequest request, User user);
    UserProfileResponse updatePreferences(UserPreferencesRequest request, User user);
    void changePassword(ChangePasswordRequest request, User user);
}
