package com.example.shop_service.service;

import com.example.shop_service.controller.dto.OrderInfo;
import com.example.shop_service.enumeration.OrderStatus;
import com.example.shop_service.service.dto.OrderRequest;
import com.example.shop_service.service.dto.OrderResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {

    UUID createOrder(Long customerId, OrderRequest orderRequest);

    UUID addProductToOrder(Long customerId, UUID orderId, List<OrderRequest.CompressedProduct> products);

    OrderResponse findOrderById (Long customerId, UUID orderId);

    Map<UUID, List<OrderInfo>> getProductInOrderInfo (List <UUID> productId);

    void deleteOrder (Long customerId, UUID orderId);

    UUID changeOrderStatus (UUID orderId, OrderStatus orderStatus, Long customerId);

}
