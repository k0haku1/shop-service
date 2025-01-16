package com.example.shop_service.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties (prefix = "rest")
@Getter
@Setter
@Configuration
public class RestConfiguration {

    private Currency currency;

    @Getter
    @Setter
    public static class Currency {
        private String host;
        private Methods methods;

        @Getter
        @Setter
        public static class Methods {
            private String ExchangeRates;
        }
    }

}
