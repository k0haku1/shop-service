package com.example.shop_service.kafka;

import com.example.shop_service.controller.dto.EventSource;
import com.example.shop_service.service.handler.EventHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    @Autowired
    @Qualifier(value = "handler")
    private Set<EventHandler<EventSource>> eventHandlers;

    @KafkaListener(topics = "test_topic", groupId = "group1", containerFactory = "kafkaListenerContainerFactoryByteArray")
    public void listen(byte[] message) {
        System.out.println(new String(message));
        final ObjectMapper objectMapper = new ObjectMapper();

        try{
            final KafkaEvent kafkaEvent = objectMapper.readValue(message, KafkaEvent.class);

            eventHandlers.stream()
                    .filter(eventSourceEventHandler -> eventSourceEventHandler.canHandle(kafkaEvent))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Handler for eventsource not found"))
                    .handleEvent(kafkaEvent);

        } catch (JsonProcessingException e) {
        log.error("Couldn't parse message: {}; exception: ", message, e);
    } catch (IOException e) {
            throw new RuntimeException(e);
        }
}

}
