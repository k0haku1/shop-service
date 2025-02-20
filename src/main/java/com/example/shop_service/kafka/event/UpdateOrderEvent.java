package com.example.shop_service.kafka.event;

import com.example.shop_service.enumeration.Event;
import com.example.shop_service.kafka.KafkaEvent;
import com.example.shop_service.service.dto.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderEvent implements KafkaEvent {

    private Long customerId;
    private UUID orderId;
    private List<OrderRequest.CompressedProduct> products;

    @Override
    public Event getEvent() {
        return Event.UPDATE_ORDER;
    }
}
