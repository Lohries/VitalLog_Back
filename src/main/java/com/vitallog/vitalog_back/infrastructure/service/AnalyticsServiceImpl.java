package com.vitallog.vitalog_back.infrastructure.service;

import com.vitallog.vitalog_back.application.dto.analytics.AnalyticsResponse;
import com.vitallog.vitalog_back.application.port.AnalyticsServicePort;
import com.vitallog.vitalog_back.domain.entity.User;
import com.vitallog.vitalog_back.domain.entity.UserPreferences;
import com.vitallog.vitalog_back.domain.repository.ClientRepository;
import com.vitallog.vitalog_back.domain.repository.ProductRepository;
import com.vitallog.vitalog_back.domain.repository.ProductSaleRepository;
import com.vitallog.vitalog_back.domain.repository.ServiceSaleRepository;
import com.vitallog.vitalog_back.domain.repository.UserPreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsServicePort {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final ProductSaleRepository productSaleRepository;
    private final ServiceSaleRepository serviceSaleRepository;
    private final UserPreferencesRepository preferencesRepository;

    @Override
    public AnalyticsResponse getAnalytics(User user) {
        var productSales = productSaleRepository.findAllByUserOrderByCreatedAtDesc(user);
        var serviceSales = serviceSaleRepository.findAllByUserOrderByCreatedAtDesc(user);
        var products = productRepository.findAllByUser(user);

        long totalClients = clientRepository.countByUser(user);

        BigDecimal totalRevenue = Stream.concat(
                productSales.stream().map(s -> s.getTotal()),
                serviceSales.stream().map(s -> s.getTotal())
        ).reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalItems = products.stream().mapToLong(p -> p.getQuantity()).sum();
        long totalProductTypes = products.size();

        UserPreferences prefs = preferencesRepository.findByUser(user).orElse(null);
        String currency = prefs != null ? prefs.getCurrency() : "R$";

        return new AnalyticsResponse(
                totalClients,
                totalRevenue,
                totalItems,
                totalProductTypes,
                currency
        );
    }
}
