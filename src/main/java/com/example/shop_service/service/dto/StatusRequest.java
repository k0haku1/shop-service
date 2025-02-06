package com.example.shop_service.service.dto;


import com.example.shop_service.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusRequest {

    private OrderStatus status;

}
