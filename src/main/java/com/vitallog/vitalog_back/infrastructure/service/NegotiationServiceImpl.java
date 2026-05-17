package com.vitallog.vitalog_back.infrastructure.service;

import com.vitallog.vitalog_back.application.dto.negotiation.NegotiationRequest;
import com.vitallog.vitalog_back.application.dto.negotiation.NegotiationResponse;
import com.vitallog.vitalog_back.application.port.NegotiationServicePort;
import com.vitallog.vitalog_back.domain.entity.Negotiation;
import com.vitallog.vitalog_back.domain.entity.NegotiationAttachment;
import com.vitallog.vitalog_back.domain.entity.User;
import com.vitallog.vitalog_back.domain.enums.NegotiationStatus;
import com.vitallog.vitalog_back.domain.repository.NegotiationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NegotiationServiceImpl implements NegotiationServicePort {

    private final NegotiationRepository negotiationRepository;

    @Override
    public List<NegotiationResponse> findAll(User user) {
        return negotiationRepository.findAllByUserOrderByCreatedAtDesc(user).stream()
                .map(NegotiationResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public NegotiationResponse create(NegotiationRequest request, User user) {
        Negotiation negotiation = buildNegotiation(request, user);
        return NegotiationResponse.from(negotiationRepository.save(negotiation));
    }

    @Override
    @Transactional
    public NegotiationResponse update(UUID id, NegotiationRequest request, User user) {
        Negotiation negotiation = negotiationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Negotiation not found"));

        negotiation.setClient(request.client());
        negotiation.setDescription(request.description());
        negotiation.setEstimatedValue(request.estimatedValue());
        negotiation.setAttendance(request.attendance());
        negotiation.setNf(request.nf());
        negotiation.setXml(request.xml());
        negotiation.setLinkedServiceId(request.linkedServiceId());

        if (request.priority() != null) negotiation.setPriority(request.priority());

        if (request.status() != null) {
            negotiation.setStatus(request.status());
            if (request.status() == NegotiationStatus.FATURADO && negotiation.getInvoicedAt() == null) {
                negotiation.setInvoicedAt(LocalDateTime.now());
            }
        }

        negotiation.getAttachments().clear();
        buildAttachments(request, negotiation);

        return NegotiationResponse.from(negotiationRepository.save(negotiation));
    }

    @Override
    public void delete(UUID id, User user) {
        Negotiation negotiation = negotiationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Negotiation not found"));
        negotiationRepository.delete(negotiation);
    }

    private Negotiation buildNegotiation(NegotiationRequest request, User user) {
        Negotiation negotiation = Negotiation.builder()
                .client(request.client())
                .description(request.description())
                .estimatedValue(request.estimatedValue())
                .attendance(request.attendance())
                .nf(request.nf())
                .xml(request.xml())
                .linkedServiceId(request.linkedServiceId())
                .user(user)
                .build();

        if (request.status() != null) negotiation.setStatus(request.status());
        if (request.priority() != null) negotiation.setPriority(request.priority());

        buildAttachments(request, negotiation);
        return negotiation;
    }

    private void buildAttachments(NegotiationRequest request, Negotiation negotiation) {
        if (request.attachments() == null) return;
        request.attachments().forEach(att -> {
            NegotiationAttachment attachment = NegotiationAttachment.builder()
                    .name(att.name())
                    .size(att.size())
                    .negotiation(negotiation)
                    .build();
            negotiation.getAttachments().add(attachment);
        });
    }
}
