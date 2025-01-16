package com.example.shop_service.controller;

import com.example.shop_service.service.dto.ProductDTO;
import com.example.shop_service.service.dto.SearchFilterDto;
import com.example.shop_service.service.impl.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    @PostMapping("")
    public ResponseEntity<Page<ProductDTO>> searchProducts (@RequestBody List<SearchFilterDto<?>> data, Pageable pageable) {
        return ResponseEntity.ok(productSearchService.searchProduct(data, pageable));
    }

}
