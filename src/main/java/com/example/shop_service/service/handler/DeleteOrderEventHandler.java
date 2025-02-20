package com.example.shop_service.service.handler;

import com.example.shop_service.controller.dto.EventSource;
import com.example.shop_service.enumeration.Event;
import com.example.shop_service.kafka.event.DeleteOrderEvent;
import com.example.shop_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteOrderEventHandler implements EventHandler<DeleteOrderEvent> {

    private final OrderService orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        return Event.DELETE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public void handleEvent(DeleteOrderEvent eventSource) {
        orderService.deleteOrder(eventSource.getCustomerId(), eventSource.getOrderId());
    }

}
