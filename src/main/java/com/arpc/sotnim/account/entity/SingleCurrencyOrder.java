package com.arpc.sotnim.account.entity;

import lombok.RequiredArgsConstructor;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;


@RequiredArgsConstructor
public class SingleCurrencyOrder implements PaymentOrder {

    private final MonetaryAmount creditAmount;

    @Override
    public MonetaryAmount getCreditAmount() {
        return creditAmount;
    }

    @Override
    public MonetaryAmount getDebitAmount() {
        return creditAmount;
    }

    @Override
    public BigDecimal getExchangeRate() {
        return BigDecimal.ONE;
    }
}
