package com.arpc.sotnim.account.boundary.dto;

public record PaymentRequest(
        Long sourceAccountId,
        Long targetAccountId,
        MoneyAmountDto instructedAmount
) {
}

