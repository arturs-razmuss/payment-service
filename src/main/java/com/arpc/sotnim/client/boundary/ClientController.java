package com.arpc.sotnim.client.boundary;

import com.arpc.sotnim.client.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController {

    @PostMapping("/api/v1/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public Client createClient(@RequestBody CreateClientRequest createClientRequest) {
        return Client.builder()
                .name(createClientRequest.name())
                .email(createClientRequest.email())
                .build();
    }

}
