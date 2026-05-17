package com.vitallog.vitalog_back.domain.repository;

import com.vitallog.vitalog_back.domain.entity.Company;
import com.vitallog.vitalog_back.domain.entity.CompanyInvite;
import com.vitallog.vitalog_back.domain.enums.InviteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyInviteRepository extends JpaRepository<CompanyInvite, UUID> {
    List<CompanyInvite> findAllByInvitedEmailAndStatus(String email, InviteStatus status);
    List<CompanyInvite> findAllByCompanyAndStatus(Company company, InviteStatus status);
    boolean existsByCompanyAndInvitedEmailAndStatus(Company company, String email, InviteStatus status);
    Optional<CompanyInvite> findByIdAndInvitedEmail(UUID id, String email);
}
