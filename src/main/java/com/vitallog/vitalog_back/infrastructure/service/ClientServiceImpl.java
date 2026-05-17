package com.vitallog.vitalog_back.infrastructure.service;

import com.vitallog.vitalog_back.application.dto.client.ClientRequest;
import com.vitallog.vitalog_back.application.dto.client.ClientResponse;
import com.vitallog.vitalog_back.application.port.ClientServicePort;
import com.vitallog.vitalog_back.domain.entity.Client;
import com.vitallog.vitalog_back.domain.entity.User;
import com.vitallog.vitalog_back.domain.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientServicePort {

    private final ClientRepository clientRepository;

    @Override
    public List<ClientResponse> findAll(User user) {
        return clientRepository.findAllByUserOrderByCreatedAtDesc(user).stream()
                .map(ClientResponse::from)
                .toList();
    }

    @Override
    public ClientResponse create(ClientRequest request, User user) {
        Client client = Client.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .cpf(request.cpf())
                .cep(request.cep())
                .street(request.street())
                .city(request.city())
                .state(request.state())
                .user(user)
                .build();
        return ClientResponse.from(clientRepository.save(client));
    }

    @Override
    public ClientResponse update(UUID id, ClientRequest request, User user) {
        Client client = clientRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        client.setName(request.name());
        client.setEmail(request.email());
        client.setPhone(request.phone());
        client.setCpf(request.cpf());
        client.setCep(request.cep());
        client.setStreet(request.street());
        client.setCity(request.city());
        client.setState(request.state());
        return ClientResponse.from(clientRepository.save(client));
    }

    @Override
    public void delete(UUID id, User user) {
        Client client = clientRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        clientRepository.delete(client);
    }
}
