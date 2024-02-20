package com.arpc.sotnim.account.component_tests.endpoints;

import com.arpc.sotnim.core.dto.RestResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;

import java.util.Optional;
import java.util.function.Supplier;

public record InvocationResult<T>(boolean success, String code, String message, HttpStatusCode httpStatusCode,
                                  ResponseEntity<RestResponse<T>> responseEntity,
                                  RestClientResponseException e) {
    static <T> InvocationResult<T> success(ResponseEntity<RestResponse<T>> result) {
        var code = result.getBody().getCode();
        var message = result.getBody().getMessage();
        var httpStatus = result.getStatusCode();
        return new InvocationResult<>(result.getStatusCode().is2xxSuccessful(), code, message, httpStatus, result, null);
    }

    static <T> InvocationResult<T> error(RestClientResponseException e) {
        var restResponse = e.getResponseBodyAs(new ParameterizedTypeReference<java.util.Map<String, String>>() {});
        return new InvocationResult<>(true, restResponse.get("code"), restResponse.get("message"), e.getStatusCode(), null, e);
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
