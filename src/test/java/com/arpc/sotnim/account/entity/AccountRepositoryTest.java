package com.arpc.sotnim.account.entity;

import com.arpc.sotnim.ComponentTest;
import com.arpc.sotnim.account.entity.view.AccountBalanceChangeView;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountRepositoryTest extends ComponentTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PaymentRepository paymentRepository;

    Account account1;
    Account account2;

    @BeforeEach
    void setUp() {
        account1 = accountRepository.save(createAccountWithBalance(500));
        account2 = accountRepository.save(createAccountWithBalance(1000));

        IntStream.range(0, 10)
                .forEach(i -> paymentRepository.saveAndFlush(
                                Payment.initiate(account1, account2,
                                        new SingleCurrencyOrder(Money.of(10, "USD")))
                        )
                );
    }

    @Test
    void shouldFindAllTransactionsWhenLimitIsOverResultSet() {
        var transactions = accountRepository.findTransactions(account1.getClientId(), account1.getAccountId(), 0, 15);

        assertThat(transactions)
                .hasSize(10)
                .allSatisfy(t -> {
                    assertThat(t.getAmount()).isEqualByComparingTo(new BigDecimal(-10));
                    assertThat(t.getPaymentId()).as("paymentId").isNotNull();
                    assertThat(t.getCreatedAt()).as("createdAt").isNotNull();
                });
    }

    @Test
    void shouldReturnSubsetWhenOffsetAndLimitReachesEndOfResults() {
        var transactions = accountRepository.findTransactions(account1.getClientId(), account1.getAccountId(), 8, 5);

        assertThat(transactions).hasSize(2);
    }

    @Test
    void shouldReturnSingleResultWhenLimitIsOne() {
        var transactions = accountRepository.findTransactions(account1.getClientId(), account1.getAccountId(), 0, 1);

        assertThat(transactions).hasSize(1);
    }

    @Test
    void shouldSortTransactionsByCreatedAt() {
        var transactions = accountRepository.findTransactions(account1.getClientId(), account1.getAccountId(), 0, 10);

        assertThat(transactions)
                .isSortedAccordingTo(Comparator.comparing(AccountBalanceChangeView::getCreatedAt).reversed());
    }

    private static Account createAccountWithBalance(float balance) {
        return Account.builder()
                .clientId(77L)
                .name("John Doe")
                .balance(Money.of(balance, "USD"))
                .build();
    }
}