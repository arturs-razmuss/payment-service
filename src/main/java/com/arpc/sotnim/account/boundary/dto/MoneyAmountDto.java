package com.arpc.sotnim.account.boundary.dto;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

public record MoneyAmountDto(
        BigDecimal amount,
        String currency
) {

    public static MoneyAmountDto from(@NonNull MonetaryAmount balance) {
        Assert.notNull(balance.getNumber(), "MonetaryAmount should always have both number and currency");
        Assert.notNull(balance.getCurrency(), "MonetaryAmount should always have both number and currency");
        return new MoneyAmountDto(
                balance.getNumber().numberValueExact(BigDecimal.class),
                balance.getCurrency().getCurrencyCode()
        );
    }
}