package com.example.shop_service.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExchangeRatesDto {

    @JsonProperty("USD")
    private BigDecimal USD;
    @JsonProperty("EUR")
    private BigDecimal EUR;
    @JsonProperty("CNY")
    private BigDecimal CNY;

}
