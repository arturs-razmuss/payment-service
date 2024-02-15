package com.arpc.sotnim.account.boundary.dto;

public record TransferRequest(
        Long sourceAccountId,
        Long targetAccountId,
        MoneyAmountDto instructedAmount
) {
}

