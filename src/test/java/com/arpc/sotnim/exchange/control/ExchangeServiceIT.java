package com.arpc.sotnim.exchange.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.convert.ExchangeRate;
import javax.money.convert.MonetaryConversions;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ExchangeServiceIT {

    ExchangeService exchangeService;

    @BeforeEach
    void setUp() {
        exchangeService = new ExchangeService(MonetaryConversions.getExchangeRateProvider("ECB", "IMF"));
    }

    @Test
    void shouldRetrieveExchangeRateExternallyWhenMultipleProvidersAreAvailable() {
        var exchangeRate = exchangeService.getExchangeRate("USD", "EUR");

        assertThat(exchangeRate)
                .isPresent()
                .get().extracting(ExchangeRate::getFactor)
                .isNotNull();
    }

    @Test
    public void shouldReturnEmptyOptionalWhenSourceCurrencyCodeIsInvalid() {
        // Act
        Optional<ExchangeRate> result = exchangeService.getExchangeRate("INVALID", "2");

        // Assert
        assertThat(result).isEmpty();
    }
}