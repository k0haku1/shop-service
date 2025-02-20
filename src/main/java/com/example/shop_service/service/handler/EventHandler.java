package com.example.shop_service.service.handler;

import com.example.shop_service.controller.dto.EventSource;

public interface EventHandler <T extends EventSource>{

    boolean canHandle(EventSource eventSource);

    void handleEvent(T eventSource);


}
