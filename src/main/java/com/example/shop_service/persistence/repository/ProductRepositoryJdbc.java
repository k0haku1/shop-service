package com.example.shop_service.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryJdbc {

    private final JdbcTemplate jdbcTemplate;

    public int updatePrices(BigDecimal percentage) {
        String sql = "UPDATE product SET price = price * ?";
        return jdbcTemplate.update(sql, BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)));
    }

}