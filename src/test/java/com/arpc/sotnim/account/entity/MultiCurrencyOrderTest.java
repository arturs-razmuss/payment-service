package com.arpc.sotnim.account.entity;

import com.arpc.sotnim.core.boundary.ErrorCodes;
import com.arpc.sotnim.core.boundary.RequestProcessingException;
import org.javamoney.moneta.Money;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static com.arpc.sotnim.currency.ExchangeRateHelper.getExchangeRate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MultiCurrencyOrderTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
                #DEBIT, CREDIT, RATE
                10,     10,     1
                10,     1,      0.1
                10,     0.1,    0.01
                10,     0.01,   0.001
                100.04, 12.35,  0.12345
                """)
    void shouldRoundToCentsWhenExchangeRateIsNotOne(String debitAmount, String creditAmount, String rate) {
        var exchangeRate = getExchangeRate("USD", "EUR", rate);

        var multiCurrencyOrder = new MultiCurrencyOrder(Money.of(new BigDecimal(creditAmount), "EUR"), exchangeRate);

        assertThat(multiCurrencyOrder.getDebitAmount()).isEqualTo(Money.of(new BigDecimal(debitAmount), "USD"));
        assertThat(multiCurrencyOrder.getCreditAmount()).isEqualTo(Money.of(new BigDecimal(creditAmount), "EUR"));
    }

    @ParameterizedTest
    @CsvSource({"-1", "0"})
    public void shouldThrowExceptionWithInvalidAmountWhenAmountIsNegativeOrZero(String amount) {
        // Arrange
        MonetaryAmount creditAmount = Money.of(new BigDecimal(amount), "EUR");
        var exchangeRate = getExchangeRate("USD", "EUR", "1");

        // Act & Assert
        assertThatThrownBy(() -> new MultiCurrencyOrder(creditAmount, exchangeRate))
                .isInstanceOf(RequestProcessingException.class)
                .extracting(it -> ((RequestProcessingException) it).getErrorCode())
                .isEqualTo(ErrorCodes.INVALID_AMOUNT);
    }




}