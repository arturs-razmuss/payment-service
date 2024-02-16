package com.arpc.sotnim.account.entity;

import com.arpc.sotnim.core.boundary.ErrorCodes;
import com.arpc.sotnim.core.boundary.RequestProcessingException;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

public class SingleCurrencyOrder implements PaymentOrder {

    private final MonetaryAmount creditAmount;

    public SingleCurrencyOrder(MonetaryAmount creditAmount) {
        if (creditAmount.isNegativeOrZero()) {
            throw new RequestProcessingException(ErrorCodes.INVALID_AMOUNT);
        }
        this.creditAmount = creditAmount;
    }

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
