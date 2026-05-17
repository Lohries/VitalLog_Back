package com.vitallog.vitalog_back.web.controller;

import com.vitallog.vitalog_back.application.dto.auth.AuthResponse;
import com.vitallog.vitalog_back.application.dto.auth.LoginRequest;
import com.vitallog.vitalog_back.application.dto.auth.SignUpRequest;
import com.vitallog.vitalog_back.application.port.AuthServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServicePort authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(request));
    }
}
