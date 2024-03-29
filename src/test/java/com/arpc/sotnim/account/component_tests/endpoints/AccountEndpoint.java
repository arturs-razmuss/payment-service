package com.arpc.sotnim.account.component_tests.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;

import java.util.List;
import java.util.Map;

import static com.arpc.sotnim.account.component_tests.endpoints.InvocationResult.wrap;

@TestComponent
@RequiredArgsConstructor
public class AccountEndpoint {

    private final TestRestTemplate restTemplate;

    public InvocationResult<AccountCreated> createAccount(String clientId, Map<String, String> clientData) {
        return wrap(() -> restTemplate.exchange(
                RequestEntity
                        .post("/api/v1/clients/{clientId}/accounts", clientId)
                        .body(clientData),
                new ParameterizedTypeReference<>() {}));
    }

    public InvocationResult<List<AccountDto>> getAccounts(String clientId) {
        return wrap(() -> restTemplate.exchange(
                RequestEntity
                        .get("/api/v1/clients/{clientId}/accounts", clientId)
                        .build(),
                new ParameterizedTypeReference<>() {}));
    }

    public InvocationResult<List<AccountTransactionDto>> getAccountTransactions(String clientId, String accountId) {
        return wrap(() -> restTemplate.exchange(
                RequestEntity
                        .get("/api/v1/clients/{clientId}/accounts/{accountId}/transactions", clientId, accountId)
                        .build(),
                new ParameterizedTypeReference<>() {}));
    }

    public record AccountCreated(String accountId, String createdAt) {
    }

    public record AccountDto(
            String accountId,
            MoneyAmountDto balance,
            String clientId
    ) {
    }

    public record MoneyAmountDto(
            String amount,
            String currency
    ) {
    }

    public record AccountTransactionDto(
            String amount,
            String paymentId,
            String createdAt
    ) {
    }

}
