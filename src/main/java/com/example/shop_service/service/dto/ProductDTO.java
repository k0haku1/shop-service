package com.example.shop_service.service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Value
@Builder(toBuilder = true)
@AllArgsConstructor
@Setter
@Getter
public class ProductDTO {

    UUID id;

    String name;

    String description;

    BigDecimal price;

    Integer quantity;

    String article;

    String currency;

    String category;

    LocalDate createdAt;

}
