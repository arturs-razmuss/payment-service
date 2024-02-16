package com.arpc.sotnim.currency;

import org.javamoney.moneta.convert.ExchangeRateBuilder;
import org.javamoney.moneta.spi.DefaultNumberValue;

import javax.money.Monetary;
import javax.money.convert.ExchangeRate;
import javax.money.convert.RateType;
import java.math.BigDecimal;

public class ExchangeRateHelper {
    public static ExchangeRate getExchangeRate(String base, String term, BigDecimal factor) {
        return new ExchangeRateBuilder("mock", RateType.REALTIME)
                .setBase(Monetary.getCurrency(base))
                .setFactor(new DefaultNumberValue(factor))
                .setTerm(Monetary.getCurrency(term))
                .build();
    }
}
