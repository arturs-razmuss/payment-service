package com.arpc.sotnim.account.entity;

import com.arpc.sotnim.core.boundary.ErrorCodes;
import com.arpc.sotnim.core.boundary.RequestProcessingException;
import org.javamoney.moneta.Money;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SingleCurrencyOrderTest {

    @ParameterizedTest
    @CsvSource({"-1", "0"})
    public void shouldThrowExceptionWithInvalidAmountWhenAmountIsNegativeOrZero(String amount) {
        // Arrange
        MonetaryAmount creditAmount = Money.of(new BigDecimal(amount), "EUR");

        // Act & Assert
        assertThatThrownBy(() -> new SingleCurrencyOrder(creditAmount))
                .isInstanceOf(RequestProcessingException.class)
                .extracting(it -> ((RequestProcessingException) it).getErrorCode())
                .isEqualTo(ErrorCodes.INVALID_AMOUNT);
    }
}