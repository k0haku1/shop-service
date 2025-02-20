package com.example.shop_service.kafka.event;

import com.example.shop_service.enumeration.Event;
import com.example.shop_service.kafka.KafkaEvent;
import com.example.shop_service.service.dto.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderEvent implements KafkaEvent {

    private Long customerId;
    private String deliveryAddress;
    private List<OrderRequest.CompressedProduct> products;

    @Override
    public Event getEvent() {
        return Event.CREATE_ORDER;
    }
}
