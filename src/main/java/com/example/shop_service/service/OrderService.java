package com.example.shop_service.service;

import com.example.shop_service.service.dto.OrderRequest;
import com.example.shop_service.service.dto.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    UUID createOrder(Long customerId, OrderRequest orderRequest);

    UUID addProductToOrder(Long customerId, UUID orderId, List<OrderRequest.CompressedProduct> products);

    OrderResponse findOrderById (Long customerId, UUID orderId);

    void deleteOrder (Long customerId, UUID orderId);

}
