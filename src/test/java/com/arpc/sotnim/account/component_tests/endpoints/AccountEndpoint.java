package com.arpc.sotnim.account.component_tests.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.arpc.sotnim.account.component_tests.endpoints.InvocationResult.wrap;

@Component
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

}
