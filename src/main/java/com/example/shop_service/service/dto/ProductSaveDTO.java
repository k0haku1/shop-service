package com.example.shop_service.service.dto;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSaveDTO {

    private String name;

    private String description;

    private String article;

    private BigDecimal price;

    private Integer quantity;

    private String category;

}
