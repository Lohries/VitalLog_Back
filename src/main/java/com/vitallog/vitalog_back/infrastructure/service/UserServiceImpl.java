package com.vitallog.vitalog_back.infrastructure.service;

import com.vitallog.vitalog_back.application.dto.user.ChangePasswordRequest;
import com.vitallog.vitalog_back.application.dto.user.UserPreferencesRequest;
import com.vitallog.vitalog_back.application.dto.user.UserProfileRequest;
import com.vitallog.vitalog_back.application.dto.user.UserProfileResponse;
import com.vitallog.vitalog_back.application.port.UserServicePort;
import com.vitallog.vitalog_back.domain.entity.User;
import com.vitallog.vitalog_back.domain.entity.UserPreferences;
import com.vitallog.vitalog_back.domain.repository.UserPreferencesRepository;
import com.vitallog.vitalog_back.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServicePort {

    private final UserRepository userRepository;
    private final UserPreferencesRepository preferencesRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfileResponse getProfile(User user) {
        UserPreferences prefs = preferencesRepository.findByUser(user).orElse(null);
        return UserProfileResponse.from(user, prefs);
    }

    @Override
    public UserProfileResponse updateProfile(UserProfileRequest request, User user) {
        user.setName(request.name());
        userRepository.save(user);

        UserPreferences prefs = preferencesRepository.findByUser(user).orElse(null);
        if (request.currency() != null) {
            if (prefs == null) {
                prefs = UserPreferences.builder().user(user).currency(request.currency()).build();
            } else {
                prefs.setCurrency(request.currency());
            }
            preferencesRepository.save(prefs);
        }

        return UserProfileResponse.from(user, prefs);
    }

    @Override
    public UserProfileResponse updatePreferences(UserPreferencesRequest request, User user) {
        UserPreferences prefs = preferencesRepository.findByUser(user)
                .orElseGet(() -> UserPreferences.builder().user(user).build());

        if (request.currency() != null) prefs.setCurrency(request.currency());
        if (request.theme() != null) prefs.setTheme(request.theme());
        if (request.notificationsEnabled() != null) prefs.setNotificationsEnabled(request.notificationsEnabled());

        preferencesRepository.save(prefs);
        return UserProfileResponse.from(user, prefs);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, User user) {
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

}
