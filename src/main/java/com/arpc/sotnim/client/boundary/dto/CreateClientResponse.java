package com.arpc.sotnim.client.boundary.dto;

import java.time.Instant;

public record CreateClientResponse(
    Long clientId,
    Instant createdAt
) {
}
