package com.vitallog.vitalog_back.application.dto.client;

import com.vitallog.vitalog_back.domain.entity.Client;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientResponse(
        UUID id,
        String name,
        String email,
        String phone,
        String cpf,
        String cep,
        String street,
        String city,
        String state,
        LocalDateTime createdAt
) {
    public static ClientResponse from(Client c) {
        return new ClientResponse(
                c.getId(),
                c.getName(),
                c.getEmail(),
                c.getPhone(),
                c.getCpf(),
                c.getCep(),
                c.getStreet(),
                c.getCity(),
                c.getState(),
                c.getCreatedAt()
        );
    }
}
