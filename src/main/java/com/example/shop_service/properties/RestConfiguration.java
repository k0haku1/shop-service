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
    private Account account;
    private Crm crm;

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

    @Getter
    @Setter
    public static class Account {
        private String host;
        private Methods methods;

        @Getter
        @Setter
        public static class Methods {
            private String getAccountNumber;
        }
    }

    @Getter
    @Setter
    public static class Crm {
        private String host;
        private Methods methods;

        @Getter
        @Setter
        public static class Methods {
            private String getAccountInn;
        }
    }

}
