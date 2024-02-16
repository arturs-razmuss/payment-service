package com.arpc.sotnim.account.component_tests.endpoints;

import com.arpc.sotnim.core.boundary.dto.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;

import java.util.Optional;
import java.util.function.Supplier;

public record InvocationResult<T>(boolean success, ResponseEntity<RestResponse<T>> responseEntity,
                                  RestClientResponseException e) {
    static <T> InvocationResult<T> success(ResponseEntity<RestResponse<T>> result) {
        return new InvocationResult<>(true, result, null);
    }

    static <T> InvocationResult<T> error(RestClientResponseException e) {
        return new InvocationResult<>(true, null, e);
    }

    public static <T> InvocationResult<T> wrap(Supplier<ResponseEntity<RestResponse<T>>> invocation) {
        try {
            return success(invocation.get());
        } catch (RestClientResponseException e) {
            return error(e);
        }
    }

    public T getResponseData() {
        return Optional.ofNullable(responseEntity.getBody()).orElseThrow().getData();
    }
}
