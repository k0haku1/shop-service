package com.example.shop_service.service;

import com.example.shop_service.persistence.models.CustomerEntity;

public interface CustomerService {

    CustomerEntity getCustomerById(Long customerId);

}
