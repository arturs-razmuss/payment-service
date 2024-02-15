package com.arpc.sotnim.exchange.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExchangeServiceIT {

    ExchangeService currencyService;

    @BeforeEach
    void setUp() {
        currencyService = new ExchangeService();
    }

    @Test
    void getExchangeRate() {
        var exchangeRate = currencyService.getExchangeRate("USD", "EUR");
        System.out.println(exchangeRate);
        assertThat(exchangeRate.getFactor()).isNotNull();
    }
}