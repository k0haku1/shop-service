package com.example.shop_service.converter;

import com.example.shop_service.persistence.models.Product;
import com.example.shop_service.service.dto.ProductSaveDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SaveProductDtoToProductDTO implements Converter<ProductSaveDTO, Product> {
    @Override
    public Product convert(ProductSaveDTO source) {
        return Product.builder()
                .name(source.getName())
                .article(source.getArticle())
                .description(source.getDescription())
                .category(source.getCategory())
                .price(source.getPrice())
                .createdAt(LocalDateTime.now().toLocalDate())
                .build();
    }
}
