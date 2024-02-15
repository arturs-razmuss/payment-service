package com.arpc.sotnim.account.entity;

import javax.money.MonetaryAmount;

public record PaymentRequest(
        Long sourceAccountId,
        Long targetAccountId,
        MonetaryAmount amount
) {
}

