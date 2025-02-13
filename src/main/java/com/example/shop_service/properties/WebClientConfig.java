package com.example.shop_service.properties;

import com.google.common.cache.CacheBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final RestConfiguration restConfiguration;

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(){
            @Override
            protected @NonNull Cache createConcurrentMapCache(@NonNull String name) {
                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                .expireAfterWrite(1, TimeUnit.MINUTES)
                                .build().asMap(),
                        false
                );
            }
        };
    }

    @Bean
    public WebClient.Builder webClient () {
        return WebClient.builder();
    }

    public WebClient getWebClientForService (String baseUrl) {
        return webClient()
                .baseUrl(baseUrl)
                .build();
    }

    public WebClient getCurrencyClient (){
        return getWebClientForService(restConfiguration.getCurrency().getHost());
    }

    public WebClient getAccountNumberClient (){
        return getWebClientForService(restConfiguration.getAccount().getHost());
    }

    public WebClient getAccountInnClient (){
        return getWebClientForService(restConfiguration.getCrm().getHost());
    }

}
