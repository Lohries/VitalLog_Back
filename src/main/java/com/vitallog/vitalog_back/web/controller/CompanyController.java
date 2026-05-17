package com.vitallog.vitalog_back.web.controller;

import com.vitallog.vitalog_back.application.dto.company.*;
import com.vitallog.vitalog_back.application.port.CompanyAnalyticsPort;
import com.vitallog.vitalog_back.application.port.CompanyServicePort;
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
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyServicePort companyService;
    private final CompanyAnalyticsPort companyAnalyticsService;

    /* ── Companies ── */

    @PostMapping("/api/companies")
    public ResponseEntity<CompanySummaryResponse> create(
            @Valid @RequestBody CompanyRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(request, user));
    }

    @GetMapping("/api/companies")
    public ResponseEntity<List<CompanySummaryResponse>> listMine(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(companyService.getMyCompanies(user));
    }

    @GetMapping("/api/companies/{id}")
    public ResponseEntity<CompanyDetailResponse> detail(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(companyService.getCompanyDetail(id, user));
    }

    @GetMapping("/api/companies/{id}/analytics")
    public ResponseEntity<CompanyAnalyticsResponse> analytics(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(companyAnalyticsService.getCompanyAnalytics(id, user));
    }

    /* ── Members ── */

    @DeleteMapping("/api/companies/{id}/members/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable UUID id,
            @PathVariable UUID userId,
            @AuthenticationPrincipal User user) {
        companyService.removeMember(id, userId, user);
        return ResponseEntity.noContent().build();
    }

    /* ── Invites (company-scoped) ── */

    @PostMapping("/api/companies/{id}/invites")
    public ResponseEntity<InviteResponse> invite(
            @PathVariable UUID id,
            @Valid @RequestBody InviteRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.inviteMember(id, request, user));
    }

    @DeleteMapping("/api/companies/{id}/invites/{inviteId}")
    public ResponseEntity<Void> cancelInvite(
            @PathVariable UUID id,
            @PathVariable UUID inviteId,
            @AuthenticationPrincipal User user) {
        companyService.cancelInvite(id, inviteId, user);
        return ResponseEntity.noContent().build();
    }

    /* ── Invites (user-scoped) ── */

    @GetMapping("/api/invites")
    public ResponseEntity<List<InviteResponse>> myInvites(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(companyService.getMyPendingInvites(user));
    }

    @PostMapping("/api/invites/{id}/accept")
    public ResponseEntity<Void> accept(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        companyService.acceptInvite(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/invites/{id}/reject")
    public ResponseEntity<Void> reject(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        companyService.rejectInvite(id, user);
        return ResponseEntity.noContent().build();
    }
}
