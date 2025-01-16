package com.example.shop_service.service.impl;


import com.example.shop_service.persistence.models.Product;
import com.example.shop_service.persistence.repository.ProductRepository;
import com.example.shop_service.service.dto.ProductDTO;
import com.example.shop_service.service.dto.SearchFilterDto;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductRepository productRepository;
    private final ConversionService conversionService;

    public Page<ProductDTO> searchProduct(List<SearchFilterDto<?>> data, Pageable pageable) {

        final Specification<Product> specification = (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            for (SearchFilterDto<?> filter : data) {
                predicates.add(filter.buildPredicate(criteriaBuilder, root));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Product> products = productRepository.findAll(specification, pageable);
        return products.map(product -> conversionService.convert(product, ProductDTO.class));

    }

}
