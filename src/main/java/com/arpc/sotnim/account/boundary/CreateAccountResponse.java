package com.arpc.sotnim.account.boundary;

import java.time.Instant;

public record CreateAccountResponse(
        Long accountId,
        Instant createdAt
) {
}
