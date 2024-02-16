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
        paymentSystem.transfer(Map.of(
                "sourceAccountId", debitAccountId,
                "targetAccountId", creditAccountId,
                "instructedAmount", Map.of(
                        "amount", "1000",
                        "currency", "EUR")
        ));

        List<AccountEndpoint.AccountDto> accountBalanceList = accountSystem.getAccounts(clientId).getResponseData();

        assertThat(accountBalanceList)
                .extracting(AccountEndpoint.AccountDto::balance)
                .extracting(AccountEndpoint.MoneyAmountDto::amount)
                .containsExactlyInAnyOrder("9500", "11000");
    }


}
