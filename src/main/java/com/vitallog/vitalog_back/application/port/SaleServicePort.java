package com.vitallog.vitalog_back.application.port;

import com.vitallog.vitalog_back.application.dto.sale.ProductSaleRequest;
import com.vitallog.vitalog_back.application.dto.sale.ProductSaleResponse;
import com.vitallog.vitalog_back.application.dto.sale.ServiceSaleRequest;
import com.vitallog.vitalog_back.application.dto.sale.ServiceSaleResponse;
import com.vitallog.vitalog_back.domain.entity.User;

import java.util.List;

public interface SaleServicePort {
    List<ProductSaleResponse> findAllProductSales(User user);
    ProductSaleResponse createProductSale(ProductSaleRequest request, User user);

    List<ServiceSaleResponse> findAllServiceSales(User user);
    ServiceSaleResponse createServiceSale(ServiceSaleRequest request, User user);
}
