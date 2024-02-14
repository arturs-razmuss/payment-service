package com.arpc.sotnim.account.boundary.dto;

public record CreateAccountRequest(
        String name,
        String currency
) {
}
