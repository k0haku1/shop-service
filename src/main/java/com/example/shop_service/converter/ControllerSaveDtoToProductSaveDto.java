package com.example.shop_service.converter;

import com.example.shop_service.controller.dto.ControllerSaveDTO;
import com.example.shop_service.service.dto.ProductSaveDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ControllerSaveDtoToProductSaveDto implements Converter<ControllerSaveDTO, ProductSaveDTO> {
    @Override
    public ProductSaveDTO convert(ControllerSaveDTO source) {
        return ProductSaveDTO.builder()
                .name(source.getName())
                .price(source.getPrice())
                .description(source.getDescription())
                .article(source.getArticle())
                .category(source.getCategory())
                .quantity(source.getQuantity())
                .build();
    }
}
