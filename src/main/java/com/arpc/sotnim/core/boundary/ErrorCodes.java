package com.arpc.sotnim.core.boundary;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCodes {
    BAD_CURRENCY("00001", "Currency is not supported"),
    BALANCE_NOT_SUFFICIENT("00002", "Balance is not sufficient"),
    ACCOUNT_NOT_FOUND("00003", "Account not found"),
    INVALID_AMOUNT("00004", "Invalid amount"),
    SERVER_ERROR("10000", "Unexpected server error");

    private final String code;
    private final String message;

    ErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean isRequestIssue() {
        return code.charAt(0) == '0';
    }

}