package com.example.shop_service.controller.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@Value
public class ControllerUpdateDTO {

    String name;

    String description;

    BigDecimal price;

    Integer quantity;

    String category;

    String article;

}
