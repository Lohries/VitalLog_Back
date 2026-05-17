package com.vitallog.vitalog_back.infrastructure.service;

import com.vitallog.vitalog_back.application.dto.sale.*;
import com.vitallog.vitalog_back.application.port.SaleServicePort;
import com.vitallog.vitalog_back.domain.entity.*;
import com.vitallog.vitalog_back.domain.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleServicePort {

    private final ProductSaleRepository productSaleRepository;
    private final ServiceSaleRepository serviceSaleRepository;
    private final ProductRepository productRepository;
    private final VitalServiceRepository vitalServiceRepository;

    @Override
    public List<ProductSaleResponse> findAllProductSales(User user) {
        return productSaleRepository.findAllByUserOrderByCreatedAtDesc(user).stream()
                .map(ProductSaleResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public ProductSaleResponse createProductSale(ProductSaleRequest request, User user) {
        Product product = productRepository.findByIdAndUser(request.productId(), user)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (product.getQuantity() < request.quantity()) {
            throw new IllegalStateException("Insufficient stock for product: " + product.getName());
        }

        product.setQuantity(product.getQuantity() - request.quantity());
        productRepository.save(product);

        String client = (request.client() != null && !request.client().isBlank())
                ? request.client() : "Consumidor";

        BigDecimal pricePerUnit = product.getPrice();
        BigDecimal total = pricePerUnit.multiply(BigDecimal.valueOf(request.quantity()));

        ProductSale sale = ProductSale.builder()
                .product(product)
                .quantity(request.quantity())
                .client(client)
                .pricePerUnit(pricePerUnit)
                .total(total)
                .user(user)
                .build();

        return ProductSaleResponse.from(productSaleRepository.save(sale));
    }

    @Override
    public List<ServiceSaleResponse> findAllServiceSales(User user) {
        return serviceSaleRepository.findAllByUserOrderByCreatedAtDesc(user).stream()
                .map(ServiceSaleResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public ServiceSaleResponse createServiceSale(ServiceSaleRequest request, User user) {
        VitalService service = vitalServiceRepository.findByIdAndUser(request.serviceId(), user)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        for (ServiceItem item : service.getItems()) {
            int needed = item.getQuantity() * request.quantity();
            Product product = item.getProduct();

            if (product == null) continue;
            if (product.getQuantity() < needed) {
                throw new IllegalStateException("Insufficient stock for: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - needed);
            productRepository.save(product);
        }

        String client = (request.client() != null && !request.client().isBlank())
                ? request.client() : "Consumidor";

        BigDecimal pricePerUnit = service.getPrice();
        BigDecimal total = pricePerUnit.multiply(BigDecimal.valueOf(request.quantity()));

        ServiceSale sale = ServiceSale.builder()
                .service(service)
                .quantity(request.quantity())
                .client(client)
                .pricePerUnit(pricePerUnit)
                .total(total)
                .user(user)
                .build();

        return ServiceSaleResponse.from(serviceSaleRepository.save(sale));
    }
}
