package com.vitallog.vitalog_back.domain.repository;

import com.vitallog.vitalog_back.domain.entity.Negotiation;
import com.vitallog.vitalog_back.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NegotiationRepository extends JpaRepository<Negotiation, UUID> {
    List<Negotiation> findAllByUserOrderByCreatedAtDesc(User user);
    Optional<Negotiation> findByIdAndUser(UUID id, User user);
}
