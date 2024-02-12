package com.arpc.sotnim.client.boundary;

import java.time.Instant;

public record CreateClientResponse(
    Long clientId,
    Instant createdAt
) {
}
