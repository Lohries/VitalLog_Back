package com.vitallog.vitalog_back.application.dto.sale;

import com.vitallog.vitalog_back.domain.entity.ServiceSale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ServiceSaleResponse(
        UUID id,
        UUID serviceId,
        String serviceName,
        int quantity,
        String client,
        BigDecimal pricePerUnit,
        BigDecimal total,
        LocalDateTime createdAt
) {
    public static ServiceSaleResponse from(ServiceSale sale) {
        return new ServiceSaleResponse(
                sale.getId(),
                sale.getService() != null ? sale.getService().getId() : null,
                sale.getService() != null ? sale.getService().getName() : null,
                sale.getQuantity(),
                sale.getClient(),
                sale.getPricePerUnit(),
                sale.getTotal(),
                sale.getCreatedAt()
        );
    }
}
