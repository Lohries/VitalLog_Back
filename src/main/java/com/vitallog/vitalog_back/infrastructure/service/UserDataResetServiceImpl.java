package com.vitallog.vitalog_back.infrastructure.service;

import com.vitallog.vitalog_back.application.port.UserDataResetPort;
import com.vitallog.vitalog_back.domain.entity.User;
import com.vitallog.vitalog_back.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDataResetServiceImpl implements UserDataResetPort {

    private final NegotiationRepository negotiationRepository;
    private final ServiceSaleRepository serviceSaleRepository;
    private final ProductSaleRepository productSaleRepository;
    private final VitalServiceRepository vitalServiceRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public void resetAllData(User user) {
        negotiationRepository.deleteAll(negotiationRepository.findAllByUserOrderByCreatedAtDesc(user));
        serviceSaleRepository.deleteAll(serviceSaleRepository.findAllByUserOrderByCreatedAtDesc(user));
        productSaleRepository.deleteAll(productSaleRepository.findAllByUserOrderByCreatedAtDesc(user));
        vitalServiceRepository.deleteAll(vitalServiceRepository.findAllByUser(user));
        productRepository.deleteAll(productRepository.findAllByUser(user));
        clientRepository.deleteAll(clientRepository.findAllByUserOrderByCreatedAtDesc(user));
    }
}
