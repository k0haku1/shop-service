package com.example.shop_service.service.handler;

import com.example.shop_service.controller.dto.EventSource;
import com.example.shop_service.enumeration.Event;
import com.example.shop_service.kafka.event.CreateOrderEvent;
import com.example.shop_service.service.OrderService;
import com.example.shop_service.service.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOrderEventHandler implements EventHandler<CreateOrderEvent> {

    private final OrderService orderService;


    @Override
    public boolean canHandle(EventSource eventSource) {
        return Event.CREATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public void handleEvent(CreateOrderEvent eventSource) {
        orderService.createOrder(eventSource.getCustomerId(), new OrderRequest(eventSource.getDeliveryAddress(), eventSource.getProducts()));
    }

}
