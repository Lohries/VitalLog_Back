package com.vitallog.vitalog_back.domain.repository;

import com.vitallog.vitalog_back.domain.entity.ProductSale;
import com.vitallog.vitalog_back.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductSaleRepository extends JpaRepository<ProductSale, UUID> {
    List<ProductSale> findAllByUserOrderByCreatedAtDesc(User user);
}
