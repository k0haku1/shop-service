package com.example.shop_service.persistence.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface CompressedProductForOrderProjection {
    UUID getId();
    String getName();
    int getQuantity();
    BigDecimal getPrice();
}