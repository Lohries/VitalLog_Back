package com.vitallog.vitalog_back.domain.repository;

import com.vitallog.vitalog_back.domain.entity.Client;
import com.vitallog.vitalog_back.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    List<Client> findAllByUserOrderByCreatedAtDesc(User user);
    Optional<Client> findByIdAndUser(UUID id, User user);
    long countByUser(User user);
}
