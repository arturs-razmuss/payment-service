package com.arpc.sotnim.account.boundary.dto;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyAmountDtoTest {

    @Test
    void shouldReturnMoneyAmountDtoWhenFromIsCalledWithValidMonetaryAmount() {
        MonetaryAmount balance = Money.of(100, "USD");

        var moneyAmountDto = MoneyAmountDto.from(balance);

        assertThat(moneyAmountDto).isNotNull();
        assertThat(moneyAmountDto.amount()).isEqualByComparingTo(new BigDecimal("100"));
        assertThat(moneyAmountDto.currency()).isEqualTo("USD");
    }

    @Test
    void shouldThrowNullPointerExceptionWhenFromIsCalledWithNullMonetaryAmount() {
        assertThatThrownBy(() -> MoneyAmountDto.from(null))
                .isInstanceOf(NullPointerException.class);
    }

}