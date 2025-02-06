package com.example.shop_service.service.dto;

import com.example.shop_service.persistence.projection.CompressedProductForOrderProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private UUID orderId;
    private List<CompressedProductForOrderProjection> products;
    private BigDecimal totalPrice;

}
