package com.example.shop_service.service.impl;

import com.example.shop_service.properties.RestConfiguration;
import com.example.shop_service.properties.WebClientConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.shop_service.service.CurrencyService;
import com.example.shop_service.service.dto.ExchangeRatesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.util.retry.Retry;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final RestConfiguration restConfiguration;
    private final WebClientConfig webClientConfig;

    @Override
    @Cacheable(value = "currency")
    public ExchangeRatesDto getExchangeRates() {

        WebClient webClient = webClientConfig.getCurrencyClient();

        return webClient.get()
                .uri(restConfiguration.getCurrency().getMethods().getExchangeRates())
                .retrieve()
                .bodyToMono(ExchangeRatesDto.class)
                .retryWhen(Retry.backoff(2, Duration.ofMillis(1000))
                    .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                            new RuntimeException("API call failed after retries", retrySignal.failure())
                    )
                )
                .block();
    }
}
