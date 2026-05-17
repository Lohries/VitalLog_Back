package com.vitallog.vitalog_back.domain.repository;

import com.vitallog.vitalog_back.domain.entity.ServiceSale;
import com.vitallog.vitalog_back.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceSaleRepository extends JpaRepository<ServiceSale, UUID> {
    List<ServiceSale> findAllByUserOrderByCreatedAtDesc(User user);
}
