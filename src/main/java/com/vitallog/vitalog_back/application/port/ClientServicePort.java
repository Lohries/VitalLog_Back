package com.vitallog.vitalog_back.application.port;

import com.vitallog.vitalog_back.application.dto.client.ClientRequest;
import com.vitallog.vitalog_back.application.dto.client.ClientResponse;
import com.vitallog.vitalog_back.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface ClientServicePort {
    List<ClientResponse> findAll(User user);
    ClientResponse create(ClientRequest request, User user);
    ClientResponse update(UUID id, ClientRequest request, User user);
    void delete(UUID id, User user);
}
