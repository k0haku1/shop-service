package com.example.shop_service.service.account;


import com.example.shop_service.properties.RestConfiguration;
import com.example.shop_service.properties.WebClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class AccountNumberServiceImpl implements AccountNumberService {

    private final RestConfiguration restConfiguration;
    private final WebClientConfig webClientConfig;

    @Override
    public CompletableFuture<Map<String, String>> getAccountNumber(List<String> logins) {
        WebClient webClient = webClientConfig.getAccountNumberClient();
        return webClient.post()
                .uri(restConfiguration.getAccount().getMethods().getGetAccountNumber())
                .bodyValue(logins)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
                .toFuture();
    }
}
