package com.example.shop_service.exception;

import java.util.UUID;

public class OrderCannotBeCancelledException  extends RuntimeException{

    public OrderCannotBeCancelledException(UUID orderId) {
        super("Order with ID " + orderId + " cannot be cancelled due to its status.");
    }
}
