package com.arpc.sotnim.account.boundary.dto;

import java.time.Instant;

public record PaymentResponse(
        Long paymentId,
        Instant createdAt
) {
}