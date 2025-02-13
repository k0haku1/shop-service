package com.example.shop_service.controller.dto;


import com.example.shop_service.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderInfo {

    private UUID id;
    private CustomerInfo customerInfo;
    private OrderStatus orderStatus;
    private String deliveryAddress;
    private Integer quantity;

}
