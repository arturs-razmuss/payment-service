package com.arpc.sotnim.account.entity;

import com.arpc.sotnim.core.ErrorCodes;
import com.arpc.sotnim.core.RequestProcessingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.lang.NonNull;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.ExchangeRate;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class MultiCurrencyOrder implements PaymentOrder {
    private final MonetaryAmount debitAmount;
    private final MonetaryAmount creditAmount;
    private final BigDecimal exchangeRate;

    public MultiCurrencyOrder(@NonNull MonetaryAmount creditAmount, @NonNull ExchangeRate exchangeRate) {
        if (creditAmount.isNegativeOrZero()) {
            throw new RequestProcessingException(ErrorCodes.INVALID_AMOUNT);
        }
        this.debitAmount = calculateDebitAmount(creditAmount, exchangeRate);
        this.creditAmount = creditAmount;
        this.exchangeRate = exchangeRate.getFactor().numberValue(BigDecimal.class);
    }

    private static Money calculateDebitAmount(@NonNull MonetaryAmount creditAmount, @NonNull ExchangeRate exchangeRate) {
        var debitNumber = creditAmount.divide(exchangeRate.getFactor())
                .with(Monetary.getDefaultRounding())
                .getNumber();
        return Money.of(debitNumber, exchangeRate.getBaseCurrency());
    }
}
