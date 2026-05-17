package com.vitallog.vitalog_back.domain.repository;

import com.vitallog.vitalog_back.domain.entity.Company;
import com.vitallog.vitalog_back.domain.entity.CompanyMember;
import com.vitallog.vitalog_back.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyMemberRepository extends JpaRepository<CompanyMember, UUID> {
    List<CompanyMember> findAllByUser(User user);
    List<CompanyMember> findAllByCompany(Company company);
    Optional<CompanyMember> findByCompanyIdAndUser(UUID companyId, User user);
    Optional<CompanyMember> findByCompanyAndUser(Company company, User user);
    boolean existsByCompanyAndUser(Company company, User user);
    int countByCompany(Company company);
}
