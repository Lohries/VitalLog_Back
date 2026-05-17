package com.vitallog.vitalog_back.web.controller;

import com.vitallog.vitalog_back.application.dto.analytics.AnalyticsResponse;
import com.vitallog.vitalog_back.application.port.AnalyticsServicePort;
import com.vitallog.vitalog_back.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsServicePort analyticsService;

    @GetMapping
    public ResponseEntity<AnalyticsResponse> getAnalytics(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(analyticsService.getAnalytics(user));
    }
}
