package com.example.shop_service.exception;

import java.util.UUID;

public class ProductNotFound extends RuntimeException {
    public ProductNotFound(UUID id) {
        super("Product with id " + id + " not found");
    }
}
