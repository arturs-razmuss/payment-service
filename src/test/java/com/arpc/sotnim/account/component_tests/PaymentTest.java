package com.arpc.sotnim.account.component_tests;

import com.arpc.sotnim.ComponentTest;
import com.arpc.sotnim.account.component_tests.endpoints.AccountEndpoint;
import com.arpc.sotnim.account.component_tests.endpoints.PaymentEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentTest extends ComponentTest {

    @Autowired
    PaymentEndpoint paymentSystem;

    String clientId = "59";
    String debitAccountId;
    String creditAccountId;

    @BeforeEach
    void setUp() {
        debitAccountId = accountSystem.createAccount(clientId, Map.of(
                        "name", "John Doe",
                        "currency", "USD")
                ).getResponseData().accountId();
        creditAccountId = accountSystem.createAccount(clientId, Map.of(
                "name", "Janis Doe",
                "currency", "EUR")
        ).getResponseData().accountId();
        exchangeServiceTestDouble.setRate("2");
    }

    @Test
    void shouldSuccessfullyCreatePaymentWhenSufficientFundsExist() {
        executePayment(debitAccountId, creditAccountId, "1000", "EUR");

        List<AccountEndpoint.AccountDto> accountDetailList = accountSystem.getAccounts(clientId).getResponseData();

        assertThat(accountDetailList)
                .extracting(AccountEndpoint.AccountDto::balance)
                .extracting(AccountEndpoint.MoneyAmountDto::amount)
                .containsExactlyInAnyOrder("9500", "11000");
    }

    @Test
    void shouldRecordTransactionWhenPaymentIsFinished() {
        executePayment(debitAccountId, creditAccountId,"50", "EUR");
        executePayment(creditAccountId, debitAccountId,"70", "USD");

        var accountDetailList = accountSystem.getAccountTransactions(clientId, debitAccountId).getResponseData();

        assertThat(accountDetailList).hasSize(2);
        assertThat(accountDetailList)
                .extracting(AccountEndpoint.AccountTransactionDto::amount)
                .containsExactly("70.00", "-25.00");
    }

    private void executePayment(String debitAccountId, String creditAccountId, String amount, String currency) {
        paymentSystem.transfer(Map.of(
                "sourceAccountId", debitAccountId,
                "targetAccountId", creditAccountId,
                "instructedAmount", Map.of(
                        "amount", amount,
                        "currency", currency)
        ));
    }

}
