package com.example.shop_service.service.impl;

import com.example.shop_service.persistence.models.CustomerEntity;
import com.example.shop_service.persistence.repository.CustomerRepository;
import com.example.shop_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public CustomerEntity getCustomerById(Long customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

}
