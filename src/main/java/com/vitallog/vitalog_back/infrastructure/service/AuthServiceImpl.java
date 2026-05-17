package com.vitallog.vitalog_back.infrastructure.service;

import com.vitallog.vitalog_back.application.dto.auth.AuthResponse;
import com.vitallog.vitalog_back.application.dto.auth.LoginRequest;
import com.vitallog.vitalog_back.application.dto.auth.SignUpRequest;
import com.vitallog.vitalog_back.application.port.AuthServicePort;
import com.vitallog.vitalog_back.domain.entity.User;
import com.vitallog.vitalog_back.domain.entity.UserPreferences;
import com.vitallog.vitalog_back.domain.repository.UserPreferencesRepository;
import com.vitallog.vitalog_back.domain.repository.UserRepository;
import com.vitallog.vitalog_back.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthServicePort {

    private final UserRepository userRepository;
    private final UserPreferencesRepository preferencesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return new AuthResponse(jwtUtil.generateToken(user), user.getEmail(), user.getName());
    }

    @Override
    @Transactional
    public AuthResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .ativo(true)
                .build();

        userRepository.save(user);

        UserPreferences prefs = UserPreferences.builder()
                .user(user)
                .currency("R$")
                .theme("light")
                .notificationsEnabled(true)
                .build();
        preferencesRepository.save(prefs);

        return new AuthResponse(jwtUtil.generateToken(user), user.getEmail(), user.getName());
    }
}
