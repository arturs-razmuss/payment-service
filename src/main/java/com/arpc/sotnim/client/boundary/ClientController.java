package com.arpc.sotnim.client.boundary;

import com.arpc.sotnim.client.control.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/api/v1/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateClientResponse createClient(@RequestBody CreateClientRequest createClientRequest) {
        var newClient = clientService.createClient(createClientRequest);

        return new CreateClientResponse(newClient.getClientId(), newClient.getCreatedAt());
    }

}
