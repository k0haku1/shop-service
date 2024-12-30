package com.example.shop_service.converter;

import com.example.shop_service.controller.dto.ControllerUpdateDTO;
import com.example.shop_service.service.dto.ProductUpdateDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ControllerUpdateDtoToProductUpdateDto implements Converter<ControllerUpdateDTO, ProductUpdateDTO> {

    @Override
    public ProductUpdateDTO convert(ControllerUpdateDTO source) {
        return ProductUpdateDTO.builder()
                .name(source.getName())
                .price(source.getPrice())
                .description(source.getDescription())
                .article(source.getArticle())
                .quantity(source.getQuantity())
                .build();
    }
}
