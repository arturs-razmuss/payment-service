package com.arpc.sotnim.account.entity;

import com.arpc.sotnim.core.RequestProcessingException;
import org.javamoney.moneta.Money;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Optional;

import static com.arpc.sotnim.core.ErrorCodes.BAD_CURRENCY;
import static com.arpc.sotnim.core.ErrorCodes.BALANCE_NOT_SUFFICIENT;
import static com.arpc.sotnim.currency.ExchangeRateHelper.getExchangeRate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
                new SingleCurrencyOrder(Money.of(transferAmount, "USD"))
        );

        assertThat(sourceAccount.getBalance()).isEqualTo(Money.of(endSourceBalance, "USD"));
        assertThat(targetAccount.getBalance()).isEqualTo(Money.of(endTargetBalance, "USD"));
    }

    @Test
    void shouldCreateAccountBalanceChangeWhenPaymentIsSuccessful() {
        var payment = Payment.initiate(
                sourceAccount,
                targetAccount,
                new SingleCurrencyOrder(Money.of(5, "USD"))
        );

        assertThat(payment.getBalanceChanges()).hasSize(2);
        var debitBalanceChange = getBalanceChangeForAccount(payment, sourceAccount.getAccountId()).orElseThrow();
        var creditBalanceChange = getBalanceChangeForAccount(payment, targetAccount.getAccountId()).orElseThrow();
        assertThat(debitBalanceChange.getAmount()).isEqualByComparingTo(new BigDecimal("-5"));
        assertThat(creditBalanceChange.getAmount()).isEqualByComparingTo(new BigDecimal("5"));
    }

    @Test
    void shouldPreventPaymentCreationWhenSourceBalanceFallsBelowZero() {
        assertThatThrownBy(() -> Payment.initiate(
                sourceAccount,
                targetAccount,
                new SingleCurrencyOrder(Money.of(11, "USD")))
        )
                .isExactlyInstanceOf(RequestProcessingException.class)
                .extracting(it -> ((RequestProcessingException) it).getErrorCode())
                .isEqualTo(BALANCE_NOT_SUFFICIENT);
    }

    @Test
    void shouldPreventPaymentCreationWhenTargetCurrencyIsDifferent() {
        var targetAccount = Account.builder()
                .accountId(2L)
                .balance(Money.of(9999.99, "EUR"))
                .build();

        assertThatThrownBy(() -> Payment.initiate(
                sourceAccount,
                targetAccount,
                new SingleCurrencyOrder(Money.of(5, "USD")))
        )
                .isExactlyInstanceOf(RequestProcessingException.class)
                .extracting(it -> ((RequestProcessingException) it).getErrorCode())
                .isEqualTo(BAD_CURRENCY);
    }

    @Test
    void shouldUpdateTargetAccountBalanceWhenCurrenciesDiffer() {
        var targetAccount = Account.builder()
                .accountId(2L)
                .balance(Money.of(0, "EUR"))
                .build();

        Payment.initiate(
                sourceAccount,
                targetAccount,
                new MultiCurrencyOrder(Money.of(50.29, "EUR"), getExchangeRate("USD", "EUR", "10"))
        );

        assertThat(sourceAccount.getBalance()).isEqualTo(Money.of(4.97, "USD"));
        assertThat(targetAccount.getBalance()).isEqualTo(Money.of(50.29, "EUR"));
    }

    @NotNull
    private Optional<AccountBalanceChange> getBalanceChangeForAccount(Payment payment, Long accountId) {
        return payment.getBalanceChanges().stream()
                .filter(change -> accountId.equals(change.getAccountId()))
                .findFirst();
    }

}