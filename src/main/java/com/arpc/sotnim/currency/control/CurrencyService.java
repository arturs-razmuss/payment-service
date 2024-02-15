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
        ExchangeRateProvider provider = MonetaryConversions.getExchangeRateProvider();

        return provider.getExchangeRate(sourceCurrency, targetCurrency);
    }

    public ExchangeRate getExchangeRate(String sourceCurrency, String targetCurrency) {
        return getExchangeRate(Monetary.getCurrency(sourceCurrency), Monetary.getCurrency(targetCurrency));
    }
}
