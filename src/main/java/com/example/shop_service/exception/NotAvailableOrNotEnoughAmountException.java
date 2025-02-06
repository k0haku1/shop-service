package com.example.shop_service.exception;

import java.util.UUID;

public class NotAvailableOrNotEnoughAmountException extends RuntimeException {

    private final UUID productId;

    public NotAvailableOrNotEnoughAmountException(UUID productId) {
        super("Product with ID " + productId + " is not available or there is not enough stock.");
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}