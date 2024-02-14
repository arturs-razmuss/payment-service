package com.arpc.sotnim.account.component_tests.endpoints;

import com.arpc.sotnim.core.boundary.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class AccountEndpoint {

    private final TestRestTemplate restTemplate;

    public InvocationResult<AccountDouble> createAccount(Long clientId, Map<String, String> clientData) {
        var interim = restTemplate.exchange(
                RequestEntity
                        .post("/api/v1/clients/{clientId}/accounts", clientId)
                        .body(clientData),
                new ParameterizedTypeReference<String>() {});

        return wrap(() -> restTemplate.exchange(
                RequestEntity
                        .post("/api/v1/clients/{clientId}/accounts", clientId)
                        .body(clientData),
                new ParameterizedTypeReference<>() {}));
    }

    public <T> InvocationResult<T> wrap(Supplier<ResponseEntity<RestResponse<T>>> invocation) {
        try {
            return InvocationResult.success(invocation.get());
        } catch (RestClientResponseException e) {
            return InvocationResult.error(e);
        }
    }

    public record InvocationResult<T>(boolean success, ResponseEntity<RestResponse<T>> responseEntity, RestClientResponseException e) {
        static <T> InvocationResult<T> success(ResponseEntity<RestResponse<T>> result) {
            return new InvocationResult<>(true, result, null);
        }

        static <T> InvocationResult<T> error(RestClientResponseException e) {
            return new InvocationResult<>(true, null, e);
        }

        public T getBody() {
            return Optional.ofNullable(responseEntity.getBody()).orElseThrow().getData();
        }
    }

    public record AccountDouble(String accountId, String createdAt) {
    }

}
