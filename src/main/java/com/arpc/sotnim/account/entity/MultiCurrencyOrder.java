package com.arpc.sotnim.account.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.money.convert.ExchangeRate;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class MultiCurrencyOrder implements PaymentOrder {
    private final MonetaryAmount debitAmount;
    private final MonetaryAmount creditAmount;
    private final BigDecimal exchangeRate;

    public MultiCurrencyOrder(MonetaryAmount creditAmount, ExchangeRate exchangeRate) {
        this.debitAmount = calculateDebitAmount(creditAmount, exchangeRate);
        this.creditAmount = creditAmount;
        this.exchangeRate = exchangeRate.getFactor().numberValue(BigDecimal.class);
    }

    private static Money calculateDebitAmount(MonetaryAmount creditAmount, ExchangeRate exchangeRate) {
        var debitNumber = creditAmount.divide(exchangeRate.getFactor()).getNumber();
        return Money.of(debitNumber, exchangeRate.getBaseCurrency());
    }
}
