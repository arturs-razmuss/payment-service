package com.arpc.sotnim.client.boundary.dto;

public record CreateClientRequest (
    String name,
    String email
) {
}