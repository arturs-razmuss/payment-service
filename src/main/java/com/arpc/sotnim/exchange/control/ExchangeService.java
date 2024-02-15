package com.arpc.sotnim.exchange.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.CurrencyConversionException;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import java.util.Optional;

@Service
@Slf4j
public class ExchangeService {

    public Optional<ExchangeRate> getExchangeRate(CurrencyUnit sourceCurrency, CurrencyUnit targetCurrency) {
        //moneta provides multiple remote data sources and local caching on file system satisfying resiliency requirements
        //alternative would be to implement our own providers with retries,circuit breaking logic with fallback to cache
        ExchangeRateProvider provider = MonetaryConversions.getExchangeRateProvider("ECB", "IMF");

        try {
            return Optional.of(provider.getExchangeRate(sourceCurrency, targetCurrency));
        } catch (CurrencyConversionException e) {
            log.warn("Exchange rate not found for {} to {}", sourceCurrency, targetCurrency);
            return Optional.empty();
        }
    }

    public Optional<ExchangeRate> getExchangeRate(String sourceCurrency, String targetCurrency) {
        return getExchangeRate(Monetary.getCurrency(sourceCurrency), Monetary.getCurrency(targetCurrency));
    }
}
