package com.example.shop_service.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Value
@Builder
@AllArgsConstructor

public class ProductDTO {

    UUID id;

    String name;

    String description;

    BigDecimal price;

    Integer quantity;

    String article;

    String category;

    LocalDate createdAt;

}
