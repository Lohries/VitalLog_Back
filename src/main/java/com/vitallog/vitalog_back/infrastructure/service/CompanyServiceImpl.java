package com.vitallog.vitalog_back.infrastructure.service;

import com.vitallog.vitalog_back.application.dto.company.*;
import com.vitallog.vitalog_back.application.port.CompanyServicePort;
import com.vitallog.vitalog_back.domain.entity.*;
import com.vitallog.vitalog_back.domain.enums.InviteStatus;
import com.vitallog.vitalog_back.domain.enums.MemberRole;
import com.vitallog.vitalog_back.domain.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyServicePort {

    private final CompanyRepository companyRepository;
    private final CompanyMemberRepository memberRepository;
    private final CompanyInviteRepository inviteRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CompanySummaryResponse createCompany(CompanyRequest request, User user) {
        Company company = Company.builder()
                .name(request.name())
                .description(request.description())
                .owner(user)
                .build();
        company = companyRepository.save(company);

        CompanyMember owner = CompanyMember.builder()
                .company(company)
                .user(user)
                .role(MemberRole.OWNER)
                .build();
        memberRepository.save(owner);

        return CompanySummaryResponse.from(owner, 1);
    }

    @Override
    public List<CompanySummaryResponse> getMyCompanies(User user) {
        return memberRepository.findAllByUser(user).stream()
                .map(m -> CompanySummaryResponse.from(m, memberRepository.countByCompany(m.getCompany())))
                .toList();
    }

    @Override
    public CompanyDetailResponse getCompanyDetail(UUID companyId, User user) {
        CompanyMember membership = memberRepository.findByCompanyIdAndUser(companyId, user)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada ou sem acesso"));

        List<CompanyMemberResponse> members = memberRepository.findAllByCompany(membership.getCompany())
                .stream().map(CompanyMemberResponse::from).toList();

        List<InviteResponse> pending = inviteRepository
                .findAllByCompanyAndStatus(membership.getCompany(), InviteStatus.PENDING)
                .stream().map(InviteResponse::from).toList();

        return CompanyDetailResponse.from(membership.getCompany(), membership.getRole().name(), members, pending);
    }

    @Override
    public InviteResponse inviteMember(UUID companyId, InviteRequest request, User user) {
        CompanyMember membership = memberRepository.findByCompanyIdAndUser(companyId, user)
                .orElseThrow(() -> new IllegalArgumentException("Você não é membro desta empresa"));

        User invited = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário com este e-mail encontrado no sistema"));

        if (memberRepository.existsByCompanyAndUser(membership.getCompany(), invited))
            throw new IllegalStateException("Este usuário já é membro da empresa");

        if (inviteRepository.existsByCompanyAndInvitedEmailAndStatus(
                membership.getCompany(), request.email(), InviteStatus.PENDING))
            throw new IllegalStateException("Já existe um convite pendente para este e-mail");

        CompanyInvite invite = CompanyInvite.builder()
                .company(membership.getCompany())
                .invitedEmail(request.email())
                .invitedBy(user)
                .status(InviteStatus.PENDING)
                .build();

        return InviteResponse.from(inviteRepository.save(invite));
    }

    @Override
    public void cancelInvite(UUID companyId, UUID inviteId, User user) {
        CompanyMember membership = memberRepository.findByCompanyIdAndUser(companyId, user)
                .orElseThrow(() -> new IllegalArgumentException("Você não é membro desta empresa"));
        if (membership.getRole() != MemberRole.OWNER)
            throw new IllegalArgumentException("Apenas o owner pode cancelar convites");

        CompanyInvite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new EntityNotFoundException("Convite não encontrado"));
        invite.setStatus(InviteStatus.REJECTED);
        inviteRepository.save(invite);
    }

    @Override
    public List<InviteResponse> getMyPendingInvites(User user) {
        return inviteRepository.findAllByInvitedEmailAndStatus(user.getEmail(), InviteStatus.PENDING)
                .stream().map(InviteResponse::from).toList();
    }

    @Override
    @Transactional
    public void acceptInvite(UUID inviteId, User user) {
        CompanyInvite invite = inviteRepository.findByIdAndInvitedEmail(inviteId, user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Convite não encontrado"));
        if (invite.getStatus() != InviteStatus.PENDING)
            throw new IllegalStateException("Este convite não está mais pendente");

        invite.setStatus(InviteStatus.ACCEPTED);
        inviteRepository.save(invite);

        CompanyMember member = CompanyMember.builder()
                .company(invite.getCompany())
                .user(user)
                .role(MemberRole.MEMBER)
                .build();
        memberRepository.save(member);
    }

    @Override
    public void rejectInvite(UUID inviteId, User user) {
        CompanyInvite invite = inviteRepository.findByIdAndInvitedEmail(inviteId, user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Convite não encontrado"));
        invite.setStatus(InviteStatus.REJECTED);
        inviteRepository.save(invite);
    }

    @Override
    @Transactional
    public void removeMember(UUID companyId, UUID targetUserId, User user) {
        CompanyMember membership = memberRepository.findByCompanyIdAndUser(companyId, user)
                .orElseThrow(() -> new IllegalArgumentException("Você não é membro desta empresa"));

        boolean isSelf = user.getId().equals(targetUserId);
        boolean isOwner = membership.getRole() == MemberRole.OWNER;

        if (!isSelf && !isOwner)
            throw new IllegalArgumentException("Apenas o owner pode remover outros membros");
        if (isOwner && isSelf)
            throw new IllegalArgumentException("O owner não pode sair da empresa sem transferir a propriedade");

        User target = userRepository.findById(targetUserId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        CompanyMember targetMembership = memberRepository.findByCompanyAndUser(membership.getCompany(), target)
                .orElseThrow(() -> new EntityNotFoundException("Membro não encontrado na empresa"));

        memberRepository.delete(targetMembership);
    }

}
