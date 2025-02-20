package com.example.shop_service.kafka.event;

import com.example.shop_service.enumeration.Event;
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
public class DeleteOrderEvent implements KafkaEvent {

    private Long customerId;
    private UUID orderId;

    @Override
    public Event getEvent() {
        return Event.DELETE_ORDER;
    }
}
