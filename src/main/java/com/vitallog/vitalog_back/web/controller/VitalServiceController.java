package com.vitallog.vitalog_back.web.controller;

import com.vitallog.vitalog_back.application.dto.service.ServiceRequest;
import com.vitallog.vitalog_back.application.dto.service.ServiceResponse;
import com.vitallog.vitalog_back.application.port.VitalServicePort;
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
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class VitalServiceController {

    private final VitalServicePort vitalServicePort;

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> findAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(vitalServicePort.findAll(user));
    }

    @PostMapping
    public ResponseEntity<ServiceResponse> create(
            @Valid @RequestBody ServiceRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vitalServicePort.create(request, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ServiceRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(vitalServicePort.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        vitalServicePort.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
