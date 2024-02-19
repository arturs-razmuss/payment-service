package com.arpc.sotnim.exchange.control;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.CurrencyConversionException;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import java.util.Optional;

import static com.arpc.sotnim.currency.ExchangeRateHelper.getExchangeRate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {

    private static final String USD_CODE = "USD";
    private static final String EUR_CODE = "EUR";
    private static final CurrencyUnit USD_CURRENCY = Monetary.getCurrency("USD");
    private static final CurrencyUnit EUR_CURRENCY = Monetary.getCurrency("EUR");

    @InjectMocks
    ExchangeService exchangeService;

    @Mock
    ExchangeRateProvider provider;

    @Test
    public void shouldRetrieveExchangeRateBetweenValidCurrencyUnits() {
        var exchangeRate = getExchangeRate(USD_CODE, EUR_CODE, "2");
        when(provider.getExchangeRate(USD_CURRENCY, EUR_CURRENCY)).thenReturn(exchangeRate);

        // Act
        Optional<ExchangeRate> result = exchangeService.getExchangeRate(USD_CODE, EUR_CODE);

        // Assert
        assertThat(result).isPresent().contains(exchangeRate);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenExchangeRateNotFoundForCurrencyUnits() {
        when(provider.getExchangeRate(USD_CURRENCY, EUR_CURRENCY)).thenThrow(CurrencyConversionException.class);

        // Act
        Optional<ExchangeRate> result = exchangeService.getExchangeRate(USD_CODE, EUR_CODE);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnEmptyOptionalWhenSourceCurrencyCodeIsInvalid() {
        var exchangeService = new ExchangeService(MonetaryConversions.getExchangeRateProvider("ECB", "IMF"));

        // Act
        Optional<ExchangeRate> result = exchangeService.getExchangeRate("INVALID", "2");

        // Assert
        assertThat(result).isEmpty();
    }



}