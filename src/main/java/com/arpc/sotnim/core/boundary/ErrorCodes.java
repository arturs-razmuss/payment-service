package com.arpc.sotnim.core.boundary;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCodes {
    BAD_CURRENCY("00001", "Currency is not supported"),
    BALANCE_NOT_SUFFICIENT("00002", "Balance is not sufficient"),
    ACCOUNT_NOT_FOUND("00003", "Account not found");

    private final String code;
    private final String message;

    ErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

}