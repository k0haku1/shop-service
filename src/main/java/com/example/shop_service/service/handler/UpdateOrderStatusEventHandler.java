package com.example.shop_service.service.handler;

import com.example.shop_service.controller.dto.EventSource;
import com.example.shop_service.enumeration.Event;
import com.example.shop_service.kafka.event.UpdateOrderStatusEvent;
import com.example.shop_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateOrderStatusEventHandler implements EventHandler<UpdateOrderStatusEvent> {

    private final OrderService orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        return Event.UPDATE_ORDER_STATUS.equals(eventSource.getEvent());
    }

    @Override
    public void handleEvent(UpdateOrderStatusEvent eventSource) {
        orderService.changeOrderStatus(eventSource.getOrderId(),eventSource.getOrderStatus(),eventSource.getCustomerId());
    }

}
