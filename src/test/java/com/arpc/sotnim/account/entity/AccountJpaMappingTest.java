package com.arpc.sotnim.account.entity;

import com.arpc.sotnim.ComponentTest;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class AccountJpaMappingTest extends ComponentTest {

    @Autowired
    AccountRepository accountRepository;

    Account savedAccount;

    @BeforeEach
    public void setUp() {
        var toSave = Account.builder()
                .clientId(7L)
                .name("Checking")
                .balance(Money.of(new BigDecimal("100"), "USD"))
                .build();

        savedAccount = accountRepository.saveAndFlush(toSave);
    }

    @Test
    public void shouldPopulateAccountIdWhenPersisted() {
        assertThat(savedAccount.getAccountId()).isNotNull();
    }

    @Test
    public void shouldHaveMatchingCreatedAtAndUpdatedAtWhenJustPersisted() {
        assertThat(savedAccount.getCreatedAt()).isNotNull();
        assertThat(savedAccount.getUpdatedAt()).isEqualTo(savedAccount.getCreatedAt());
    }

    @Test
    public void shouldOnlyChangeUpdatedAtWhenModificationIsMade() {
        savedAccount.getBalance().add(Money.of(new BigDecimal("10"), "USD"));
        savedAccount = accountRepository.saveAndFlush(savedAccount);

        assertThat(savedAccount.getUpdatedAt()).isAfter(savedAccount.getCreatedAt());
    }

}