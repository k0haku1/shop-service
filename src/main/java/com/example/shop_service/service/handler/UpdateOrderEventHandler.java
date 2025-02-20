package com.example.shop_service.service.handler;

import com.example.shop_service.controller.dto.EventSource;
import com.example.shop_service.enumeration.Event;
import com.example.shop_service.kafka.event.UpdateOrderEvent;
import com.example.shop_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateOrderEventHandler implements EventHandler <UpdateOrderEvent>{

    private final OrderService orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        return Event.UPDATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public void handleEvent(UpdateOrderEvent eventSource) {
        orderService.addProductToOrder(eventSource.getCustomerId(), eventSource.getOrderId(), eventSource.getProducts());
    }

}
