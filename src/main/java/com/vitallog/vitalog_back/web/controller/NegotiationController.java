package com.vitallog.vitalog_back.web.controller;

import com.vitallog.vitalog_back.application.dto.negotiation.NegotiationRequest;
import com.vitallog.vitalog_back.application.dto.negotiation.NegotiationResponse;
import com.vitallog.vitalog_back.application.port.NegotiationServicePort;
import com.vitallog.vitalog_back.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/negotiations")
@RequiredArgsConstructor
public class NegotiationController {

    private final NegotiationServicePort negotiationService;

    @GetMapping
    public ResponseEntity<List<NegotiationResponse>> findAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(negotiationService.findAll(user));
    }

    @PostMapping
    public ResponseEntity<NegotiationResponse> create(
            @Valid @RequestBody NegotiationRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(negotiationService.create(request, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NegotiationResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody NegotiationRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(negotiationService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        negotiationService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
