package com.vitallog.vitalog_back.application.port;

import com.vitallog.vitalog_back.application.dto.auth.AuthResponse;
import com.vitallog.vitalog_back.application.dto.auth.LoginRequest;
import com.vitallog.vitalog_back.application.dto.auth.SignUpRequest;

public interface AuthServicePort {
    AuthResponse login(LoginRequest request);
    AuthResponse signUp(SignUpRequest request);
}
