package com.arpc.sotnim.account.boundary;

public record CreateAccountRequest(
        String name,
        String currency
) {
}
