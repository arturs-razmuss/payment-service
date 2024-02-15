package com.arpc.sotnim.account.boundary.dto;

import com.arpc.sotnim.account.entity.Account;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountBalanceDtoTest {

    @Test
    void shouldReturnAccountBalanceDtoWhenFromIsCalledWithValidAccount() {
        var now = Instant.now();
        MonetaryAmount balance = Money.of(100, "USD");
        Account account = Account.builder()
                .accountId(1L)
                .name("Test Account")
                .balance(balance)
                .clientId(9L)
                .createdAt(now)
                .build();

        AccountBalanceDto accountBalanceDto = AccountBalanceDto.from(account);

        assertThat(accountBalanceDto).isNotNull();
        assertThat(accountBalanceDto.accountId()).isEqualTo(1L);
        assertThat(accountBalanceDto.balance().amount()).isEqualByComparingTo(balance.getNumber().numberValueExact(BigDecimal.class));
        assertThat(accountBalanceDto.balance().currency()).isEqualTo(balance.getCurrency().getCurrencyCode());
        assertThat(accountBalanceDto.clientId()).isEqualTo(9L);
        assertThat(accountBalanceDto.createdAt()).isEqualTo(now);
    }

    @Test
    void shouldReturnAccountBalanceDtoWithNullBalanceWhenFromIsCalledWithAccountHavingNullBalance() {
        Account account = Account.builder()
                .balance(null)
                .build();

        AccountBalanceDto accountBalanceDto = AccountBalanceDto.from(account);

        assertThat(accountBalanceDto.balance()).isNull();
    }

    @Test
    void shouldThrowNullPointerExceptionWhenFromIsCalledWithNullAccount() {
        assertThatThrownBy(() -> AccountBalanceDto.from(null))
                .isInstanceOf(NullPointerException.class);
    }
}