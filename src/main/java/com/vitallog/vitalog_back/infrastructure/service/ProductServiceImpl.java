package com.vitallog.vitalog_back.infrastructure.service;

import com.vitallog.vitalog_back.application.dto.product.ProductRequest;
import com.vitallog.vitalog_back.application.dto.product.ProductResponse;
import com.vitallog.vitalog_back.application.port.ProductServicePort;
import com.vitallog.vitalog_back.domain.entity.Product;
import com.vitallog.vitalog_back.domain.entity.User;
import com.vitallog.vitalog_back.domain.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServicePort {

    private static final int MAX_PRODUCT_TYPES = 10;

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> findAll(User user) {
        return productRepository.findAllByUser(user).stream()
                .map(ProductResponse::from)
                .toList();
    }

    @Override
    public ProductResponse create(ProductRequest request, User user) {
        if (productRepository.countByUser(user) >= MAX_PRODUCT_TYPES) {
            throw new IllegalStateException("Maximum of " + MAX_PRODUCT_TYPES + " product types reached");
        }

        Product product = Product.builder()
                .name(request.name())
                .quantity(request.quantity())
                .price(request.price())
                .description(request.description())
                .user(user)
                .build();

        return ProductResponse.from(productRepository.save(product));
    }

    @Override
    public ProductResponse update(UUID id, ProductRequest request, User user) {
        Product product = productRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setName(request.name());
        product.setQuantity(request.quantity());
        product.setPrice(request.price());
        product.setDescription(request.description());

        return ProductResponse.from(productRepository.save(product));
    }

    @Override
    public void delete(UUID id, User user) {
        Product product = productRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.delete(product);
    }
}
