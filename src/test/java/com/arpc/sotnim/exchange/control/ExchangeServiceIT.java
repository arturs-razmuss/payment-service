package com.arpc.sotnim.exchange.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.convert.ExchangeRate;

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

        assertThat(exchangeRate)
                .isPresent()
                .get().extracting(ExchangeRate::getFactor)
                .isNotNull();
    }
}