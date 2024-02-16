package com.arpc.sotnim.account.component_tests.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;

import java.util.Map;

import static com.arpc.sotnim.account.component_tests.endpoints.InvocationResult.wrap;

@TestComponent
@RequiredArgsConstructor
public class PaymentEndpoint {

    private final TestRestTemplate restTemplate;

    public InvocationResult<Map<String, Object>> transfer(Map<String, Object> transferRequest) {
        return wrap(() -> restTemplate.exchange(
                RequestEntity
                        .post("/api/v1/payments")
                        .body(transferRequest),
                new ParameterizedTypeReference<>() {})
        );

    }


}
