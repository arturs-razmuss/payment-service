package com.arpc.sotnim.account.entity;

import org.javamoney.moneta.Money;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {

    Account sourceAccount;
    Account targetAccount;
    @BeforeEach
    void setUp() {
        sourceAccount = Account.builder()
                .accountId(1L)
                .clientId(1L)
                .balance(Money.of(10, "USD"))
                .build();
        targetAccount = Account.builder()
                .accountId(2L)
                .clientId(2L)
                .balance(Money.of(9999.99, "USD"))
                .build();
    }
    @ParameterizedTest
    @CsvSource({
            "0.01, 9.99, 10000",
            "5, 5, 10004.99",
            "10, 0, 10009.99"})
    void shouldUpdateAccountBalanceWhenCurrenciesMatch(
            float transferAmount,
            float endSourceBalance,
            float endTargetBalance
        ) {
        Payment.initiate(
                sourceAccount,
                targetAccount,
                new PaymentRequest(Money.of(transferAmount, "USD"))
        );

        assertThat(sourceAccount.getBalance()).isEqualTo(Money.of(endSourceBalance, "USD"));
        assertThat(targetAccount.getBalance()).isEqualTo(Money.of(endTargetBalance, "USD"));
    }

    @Test
    void shouldCreateAccountBalanceChangeWhenPaymentIsSuccessful() {
        var payment = Payment.initiate(
                sourceAccount,
                targetAccount,
                new PaymentRequest(Money.of(5, "USD"))
        );

        assertThat(payment.getBalanceChanges()).hasSize(2);
        var debitBalanceChange = getBalanceChangeForAccount(payment, sourceAccount.getAccountId()).orElseThrow();
        var creditBalanceChange = getBalanceChangeForAccount(payment, targetAccount.getAccountId()).orElseThrow();
        assertThat(debitBalanceChange.getAmount()).isEqualByComparingTo(new BigDecimal("-5"));
        assertThat(creditBalanceChange.getAmount()).isEqualByComparingTo(new BigDecimal("5"));


    }

    @NotNull
    private Optional<AccountBalanceChange> getBalanceChangeForAccount(Payment payment, Long accountId) {
        return payment.getBalanceChanges().stream()
                .filter(change -> accountId.equals(change.getAccountId()))
                .findFirst();
    }

}