package com.arpc.sotnim.currency.control;

import com.arpc.sotnim.ComponentTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyServiceTest extends ComponentTest {
    @Autowired
    CurrencyService currencyService;

    @Test
    void getExchangeRate() {
        var exchangeRate = currencyService.getExchangeRate("USD", "EUR");
        System.out.println(exchangeRate);
        assertThat(exchangeRate.getFactor()).isNotNull();
    }
}