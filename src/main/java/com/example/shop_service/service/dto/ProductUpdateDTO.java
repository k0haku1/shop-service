package com.example.shop_service.service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductUpdateDTO {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private String category;

    private String article;

}
