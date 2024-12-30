package com.example.shop_service.converter;

import com.example.shop_service.persistence.models.Product;
import com.example.shop_service.service.dto.ProductDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoToProduct implements Converter<ProductDTO, Product> {

    @Override
    public Product convert(ProductDTO source) {
        return Product.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .price(source.getPrice())
                .article(source.getArticle())
                .quantity(source.getQuantity())
                .createdAt(source.getCreatedAt())
                .category(source.getCategory())
                .build();
    }
}