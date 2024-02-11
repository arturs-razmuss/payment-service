package com.arpc.sotnim.client.boundary;

public record CreateClientRequest (
    String name,
    String email
) {
}