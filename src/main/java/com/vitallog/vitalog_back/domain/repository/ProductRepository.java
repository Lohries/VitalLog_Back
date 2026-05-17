package com.vitallog.vitalog_back.domain.repository;

import com.vitallog.vitalog_back.domain.entity.Product;
import com.vitallog.vitalog_back.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByUser(User user);
    Optional<Product> findByIdAndUser(UUID id, User user);
    long countByUser(User user);
}
