package com.example.shop_service.controller;

import com.example.shop_service.provider.CustomerIdRequestBean;
import com.example.shop_service.service.dto.OrderRequest;
import com.example.shop_service.service.dto.OrderResponse;
import com.example.shop_service.service.dto.StatusRequest;
import com.example.shop_service.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class ProductOrderController {

    private final OrderServiceImpl orderService;
    private final CustomerIdRequestBean customerIdRequestBean;

    @PostMapping
    public ResponseEntity<UUID> createOrder(
            @RequestBody OrderRequest orderRequest) {

        UUID orderResponse = orderService.createOrder(customerIdRequestBean.getCustomerId(), orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);

    }

    @PatchMapping("/{orderId}/products")
    public ResponseEntity<UUID> addProductToOrder(
            @PathVariable UUID orderId,
            @RequestBody OrderRequest request) {

        return ResponseEntity.ok(orderService.addProductToOrder(customerIdRequestBean.getCustomerId(), orderId, request.getProducts()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.findOrderById(customerIdRequestBean.getCustomerId(), orderId));

    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(
            @PathVariable UUID orderId) {
        orderService.deleteOrder(customerIdRequestBean.getCustomerId(), orderId);
    }

    @PostMapping("/order/{orderId}/confirm")
    public ResponseEntity<OrderResponse> confirmOrder(
            @PathVariable UUID orderId
    ){
        //todo
        return null;
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<UUID> changeOrderStatus(
            @PathVariable UUID orderId,
            @RequestBody StatusRequest statusRequest
    ) {
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, statusRequest.getStatus(),customerIdRequestBean.getCustomerId()));
    }
}
