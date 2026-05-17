package com.vitallog.vitalog_back.application.dto.auth;

public record AuthResponse(
        String token,
        String email,
        String name
) {}
