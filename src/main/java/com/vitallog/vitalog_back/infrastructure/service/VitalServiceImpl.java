package com.vitallog.vitalog_back.infrastructure.service;

import com.vitallog.vitalog_back.application.dto.service.ServiceItemRequest;
import com.vitallog.vitalog_back.application.dto.service.ServiceRequest;
import com.vitallog.vitalog_back.application.dto.service.ServiceResponse;
import com.vitallog.vitalog_back.application.port.VitalServicePort;
import com.vitallog.vitalog_back.domain.entity.Product;
import com.vitallog.vitalog_back.domain.entity.ServiceItem;
import com.vitallog.vitalog_back.domain.entity.User;
import com.vitallog.vitalog_back.domain.entity.VitalService;
import com.vitallog.vitalog_back.domain.repository.ProductRepository;
import com.vitallog.vitalog_back.domain.repository.VitalServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VitalServiceImpl implements VitalServicePort {

    private final VitalServiceRepository serviceRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ServiceResponse> findAll(User user) {
        return serviceRepository.findAllByUser(user).stream()
                .map(ServiceResponse::from)
                .toList();
    }

    @Override
    public ServiceResponse create(ServiceRequest request, User user) {
        VitalService service = VitalService.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .user(user)
                .build();

        buildItems(request.items(), service, user);

        return ServiceResponse.from(serviceRepository.save(service));
    }

    @Override
    public ServiceResponse update(UUID id, ServiceRequest request, User user) {
        VitalService service = serviceRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        service.setName(request.name());
        service.setDescription(request.description());
        service.setPrice(request.price());
        service.getItems().clear();
        buildItems(request.items(), service, user);

        return ServiceResponse.from(serviceRepository.save(service));
    }

    @Override
    public void delete(UUID id, User user) {
        VitalService service = serviceRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
        serviceRepository.delete(service);
    }

    private void buildItems(List<ServiceItemRequest> itemRequests, VitalService service, User user) {
        if (itemRequests == null) return;
        itemRequests.forEach(itemReq -> {
            Product product = productRepository.findByIdAndUser(itemReq.productId(), user)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found: " + itemReq.productId()));
            ServiceItem item = ServiceItem.builder()
                    .product(product)
                    .quantity(itemReq.quantity())
                    .service(service)
                    .build();
            service.getItems().add(item);
        });
    }
}
