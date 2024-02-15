package com.arpc.sotnim.currency.control;

import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

@Service
public class CurrencyService {

    public ExchangeRate getExchangeRate(CurrencyUnit sourceCurrency, CurrencyUnit targetCurrency) {
        //moneta provides multiple remote data sources and local caching on file system satisfying resiliency requirements
        //alternative would be to implement our own providers with retries,circuit breaking logic with fallback to cache
        ExchangeRateProvider provider = MonetaryConversions.getExchangeRateProvider("ECB", "IMF");

        return provider.getExchangeRate(sourceCurrency, targetCurrency);
    }

    public ExchangeRate getExchangeRate(String sourceCurrency, String targetCurrency) {
        return getExchangeRate(Monetary.getCurrency(sourceCurrency), Monetary.getCurrency(targetCurrency));
    }
}
