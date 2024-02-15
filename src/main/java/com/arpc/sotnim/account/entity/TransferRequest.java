package com.arpc.sotnim.account.entity;

import com.arpc.sotnim.account.boundary.dto.MoneyAmountDto;

public record TransferRequest(
        Long sourceAccountId,
        Long targetAccountId,
        MoneyAmountDto instructedAmount
) {
}

