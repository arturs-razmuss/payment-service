package com.arpc.sotnim.client.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Builder
@Jacksonized
@Getter
@ToString
@EqualsAndHashCode
public class Client {

    private Long clientId;
    private String name;
    private String email;
    private Instant createdAt;

}
