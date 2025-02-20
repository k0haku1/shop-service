package com.example.shop_service.kafka;

import com.example.shop_service.controller.dto.EventSource;
import com.example.shop_service.kafka.event.CreateOrderEvent;
import com.example.shop_service.kafka.event.DeleteOrderEvent;
import com.example.shop_service.kafka.event.UpdateOrderEvent;
import com.example.shop_service.kafka.event.UpdateOrderStatusEvent;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateOrderEvent.class, name = "CREATE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderEvent.class, name = "UPDATE_ORDER"),
        @JsonSubTypes.Type(value = DeleteOrderEvent.class, name = "DELETE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderStatusEvent.class, name = "UPDATE_ORDER_STATUS"),
})
public interface KafkaEvent extends EventSource {
}
