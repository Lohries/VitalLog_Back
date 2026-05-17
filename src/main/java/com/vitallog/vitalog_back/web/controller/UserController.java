package com.vitallog.vitalog_back.web.controller;

import com.vitallog.vitalog_back.application.dto.user.ChangePasswordRequest;
import com.vitallog.vitalog_back.application.dto.user.UserPreferencesRequest;
import com.vitallog.vitalog_back.application.dto.user.UserProfileRequest;
import com.vitallog.vitalog_back.application.dto.user.UserProfileResponse;
import com.vitallog.vitalog_back.application.port.UserDataResetPort;
import com.vitallog.vitalog_back.application.port.UserServicePort;
import com.vitallog.vitalog_back.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServicePort userService;
    private final UserDataResetPort userDataResetService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getProfile(user));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @Valid @RequestBody UserProfileRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.updateProfile(request, user));
    }

    @PutMapping("/preferences")
    public ResponseEntity<UserProfileResponse> updatePreferences(
            @RequestBody UserPreferencesRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.updatePreferences(request, user));
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal User user) {
        userService.changePassword(request, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reset")
    public ResponseEntity<Void> resetAllData(@AuthenticationPrincipal User user) {
        userDataResetService.resetAllData(user);
        return ResponseEntity.noContent().build();
    }
}
