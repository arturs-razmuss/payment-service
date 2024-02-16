package com.arpc.sotnim.account.component_tests.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor

public class PaymentEndpoint {

    private final TestRestTemplate restTemplate;

    public void transfer(Map<String, Object> transferRequest) {
        restTemplate.postForEntity("/api/v1/payments", transferRequest, Void.class);
    }
}
