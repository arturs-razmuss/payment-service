package com.arpc.sotnim.account.boundary.dto;

import com.arpc.sotnim.account.entity.Account;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.Optional;

public record AccountBalanceDto(
        Long accountId,
        MoneyAmountDto balance,
        Long clientId,
        Instant createdAt
) {
    public static AccountBalanceDto from(@NonNull Account account) {
        return new AccountBalanceDto(
                account.getAccountId(),
                Optional.ofNullable(account.getBalance()).map(MoneyAmountDto::from).orElse(null),
                account.getClientId(),
                account.getCreatedAt()
        );
    }
}