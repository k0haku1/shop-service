package com.example.shop_service.controller;


import com.example.shop_service.controller.dto.OrderInfo;
import com.example.shop_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/info")
@RequiredArgsConstructor
public class ProductInOrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity <Map<UUID, List <OrderInfo>>> getProductInOrder(@RequestBody List<UUID> productId) {
        return ResponseEntity.ok(orderService.getProductInOrderInfo(productId));
    }

}
