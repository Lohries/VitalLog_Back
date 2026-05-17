package com.vitallog.vitalog_back.domain.repository;

import com.vitallog.vitalog_back.domain.entity.User;
import com.vitallog.vitalog_back.domain.entity.VitalService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VitalServiceRepository extends JpaRepository<VitalService, UUID> {
    List<VitalService> findAllByUser(User user);
    Optional<VitalService> findByIdAndUser(UUID id, User user);
}
