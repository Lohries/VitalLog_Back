package com.vitallog.vitalog_back.application.port;

import com.vitallog.vitalog_back.application.dto.company.*;
import com.vitallog.vitalog_back.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface CompanyServicePort {
    CompanySummaryResponse createCompany(CompanyRequest request, User user);
    List<CompanySummaryResponse> getMyCompanies(User user);
    CompanyDetailResponse getCompanyDetail(UUID companyId, User user);
    InviteResponse inviteMember(UUID companyId, InviteRequest request, User user);
    void cancelInvite(UUID companyId, UUID inviteId, User user);
    List<InviteResponse> getMyPendingInvites(User user);
    void acceptInvite(UUID inviteId, User user);
    void rejectInvite(UUID inviteId, User user);
    void removeMember(UUID companyId, UUID targetUserId, User user);
}
