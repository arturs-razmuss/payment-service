package com.arpc.sotnim.account.boundary.dto;

import com.arpc.sotnim.account.entity.Account;
import org.springframework.lang.NonNull;

import java.util.Optional;

public record AccountBalanceDto(
        Long accountId,
        MoneyAmountDto balance,
        Long clientId
) {
    public static AccountBalanceDto from(@NonNull Account account) {
        return new AccountBalanceDto(
                account.getAccountId(),
                Optional.ofNullable(account.getBalance()).map(MoneyAmountDto::from).orElse(null),
                account.getClientId()
        );
    }
}