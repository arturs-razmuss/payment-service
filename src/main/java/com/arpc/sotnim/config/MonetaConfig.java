package com.arpc.sotnim.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

@Configuration
public class MonetaConfig {

    @Bean
    public ExchangeRateProvider exchangeRateProvider() {
        return MonetaryConversions.getExchangeRateProvider("ECB", "IMF");
    }

}
