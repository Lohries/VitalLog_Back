package com.vitallog.vitalog_back.infrastructure.service;

import com.vitallog.vitalog_back.application.dto.analytics.AnalyticsResponse;
import com.vitallog.vitalog_back.application.dto.company.CompanyAnalyticsResponse;
import com.vitallog.vitalog_back.application.port.AnalyticsServicePort;
import com.vitallog.vitalog_back.application.port.CompanyAnalyticsPort;
import com.vitallog.vitalog_back.domain.entity.CompanyMember;
import com.vitallog.vitalog_back.domain.entity.User;
import com.vitallog.vitalog_back.domain.repository.CompanyMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyAnalyticsServiceImpl implements CompanyAnalyticsPort {

    private final CompanyMemberRepository memberRepository;
    private final AnalyticsServicePort analyticsService;

    @Override
    public CompanyAnalyticsResponse getCompanyAnalytics(UUID companyId, User user) {
        CompanyMember membership = memberRepository.findByCompanyIdAndUser(companyId, user)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada ou sem acesso"));

        List<CompanyMember> members = memberRepository.findAllByCompany(membership.getCompany());

        List<CompanyAnalyticsResponse.MemberPerformanceResponse> performances = members.stream().map(m -> {
            User u = m.getUser();
            AnalyticsResponse analytics = analyticsService.getAnalytics(u);
            return new CompanyAnalyticsResponse.MemberPerformanceResponse(
                    u.getName(), u.getEmail(),
                    analytics.totalClients(),
                    analytics.totalRevenue(),
                    analytics.totalItems()
            );
        }).toList();

        long totalClients = performances.stream().mapToLong(CompanyAnalyticsResponse.MemberPerformanceResponse::clients).sum();
        BigDecimal totalRevenue = performances.stream()
                .map(CompanyAnalyticsResponse.MemberPerformanceResponse::revenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long totalItems = performances.stream().mapToLong(CompanyAnalyticsResponse.MemberPerformanceResponse::items).sum();

        return new CompanyAnalyticsResponse(members.size(), totalClients, totalRevenue, totalItems, performances);
    }
}
