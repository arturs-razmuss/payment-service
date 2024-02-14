package com.arpc.sotnim.account.boundary.dto;

import java.time.Instant;

public record CreateAccountResponse(
        Long accountId,
        Instant createdAt
) {
}
