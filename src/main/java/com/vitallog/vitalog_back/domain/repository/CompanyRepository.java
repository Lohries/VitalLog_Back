package com.vitallog.vitalog_back.domain.repository;

import com.vitallog.vitalog_back.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {}
