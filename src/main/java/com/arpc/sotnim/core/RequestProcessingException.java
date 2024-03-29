package com.arpc.sotnim.core;

import lombok.Getter;

@Getter
public class RequestProcessingException extends RuntimeException {

    private final ErrorCodes errorCode;

    public RequestProcessingException(ErrorCodes errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public static java.util.function.Supplier<RequestProcessingException> withCode(ErrorCodes code) {
        return () -> new RequestProcessingException(code);
    }

}
