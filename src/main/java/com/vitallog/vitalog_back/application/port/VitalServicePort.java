package com.vitallog.vitalog_back.application.port;

import com.vitallog.vitalog_back.application.dto.service.ServiceRequest;
import com.vitallog.vitalog_back.application.dto.service.ServiceResponse;
import com.vitallog.vitalog_back.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface VitalServicePort {
    List<ServiceResponse> findAll(User user);
    ServiceResponse create(ServiceRequest request, User user);
    ServiceResponse update(UUID id, ServiceRequest request, User user);
    void delete(UUID id, User user);
}
