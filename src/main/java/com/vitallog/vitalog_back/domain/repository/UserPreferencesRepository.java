package com.vitallog.vitalog_back.domain.repository;

import com.vitallog.vitalog_back.domain.entity.User;
import com.vitallog.vitalog_back.domain.entity.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, UUID> {
    Optional<UserPreferences> findByUser(User user);
}
