package com.vitallog.vitalog_back.application.port;

import com.vitallog.vitalog_back.application.dto.product.ProductRequest;
import com.vitallog.vitalog_back.application.dto.product.ProductResponse;
import com.vitallog.vitalog_back.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface ProductServicePort {
    List<ProductResponse> findAll(User user);
    ProductResponse create(ProductRequest request, User user);
    ProductResponse update(UUID id, ProductRequest request, User user);
    void delete(UUID id, User user);
}
