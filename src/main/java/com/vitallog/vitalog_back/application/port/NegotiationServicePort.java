package com.vitallog.vitalog_back.application.port;

import com.vitallog.vitalog_back.application.dto.negotiation.NegotiationRequest;
import com.vitallog.vitalog_back.application.dto.negotiation.NegotiationResponse;
import com.vitallog.vitalog_back.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface NegotiationServicePort {
    List<NegotiationResponse> findAll(User user);
    NegotiationResponse create(NegotiationRequest request, User user);
    NegotiationResponse update(UUID id, NegotiationRequest request, User user);
    void delete(UUID id, User user);
}
