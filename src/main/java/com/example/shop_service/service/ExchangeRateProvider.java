package com.example.shop_service.service;


import com.example.shop_service.service.dto.ExchangeRatesDto;
import com.example.shop_service.service.impl.CurrencyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ExchangeRateProvider {

    private final CurrencyServiceImpl currencyServiceImpl;
    private final ObjectMapper objectMapper;

    private ExchangeRatesDto getExchangeRate() {

        try {
            return currencyServiceImpl.getExchangeRates();
        } catch (Exception e) {
            try {
                return getExchangeRateFromFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public BigDecimal convertPriceFromCurrency(BigDecimal price, String currency) {

        ExchangeRatesDto exchangeRates = getExchangeRate();

        BigDecimal convertRate = switch (currency) {
            case "EUR" -> exchangeRates.getEUR();
            case "USD" -> exchangeRates.getUSD();
            case  "CNY" -> exchangeRates.getCNY();
            case "RUB" -> BigDecimal.valueOf(1);

            default -> throw new IllegalArgumentException("Invalid currency: " + currency);
        };

        return price.divide(convertRate, 2, RoundingMode.DOWN);

    }

    public ExchangeRatesDto getExchangeRateFromFile() throws IOException {
        ClassPathResource resource = new ClassPathResource("currencies.json");
        return objectMapper.readValue(resource.getInputStream(), ExchangeRatesDto.class);
    }

}
