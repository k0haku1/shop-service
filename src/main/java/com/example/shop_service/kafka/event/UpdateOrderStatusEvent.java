package com.example.shop_service.kafka.event;

import com.example.shop_service.enumeration.Event;
import com.example.shop_service.enumeration.OrderStatus;
import com.example.shop_service.kafka.KafkaEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusEvent implements KafkaEvent {
    private Long customerId;
    private UUID orderId;
    private OrderStatus orderStatus;

    @Override
    public Event getEvent() {
        return Event.UPDATE_ORDER_STATUS;
    }
}
