package com.example.shop_service.controller.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductFilterDto {

    private String name;

    private BigDecimal price;

    private Integer quantity;

}
