package com.example.shop_service.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private String deliveryAddress;
    private List<CompressedProduct> products;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompressedProduct {
        private UUID id;
        private int quantity;
        private BigDecimal price;
    }

}
