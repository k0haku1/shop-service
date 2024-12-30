package com.example.shop_service.controller.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@Value
public class ControllerSaveDTO {

    String name;

    String description;

    String article;

    BigDecimal price;

    Integer quantity;

    String category;

}
