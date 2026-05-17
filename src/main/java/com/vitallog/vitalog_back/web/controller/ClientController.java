package com.vitallog.vitalog_back.web.controller;

import com.vitallog.vitalog_back.application.dto.client.ClientRequest;
import com.vitallog.vitalog_back.application.dto.client.ClientResponse;
import com.vitallog.vitalog_back.application.port.ClientServicePort;
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
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientServicePort clientService;

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(clientService.findAll(user));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(
            @Valid @RequestBody ClientRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(request, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ClientRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(clientService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        clientService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
