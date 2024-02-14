package com.arpc.sotnim.client.control;

import com.arpc.sotnim.client.boundary.dto.CreateClientRequest;
import com.arpc.sotnim.client.entity.Client;
import com.arpc.sotnim.client.entity.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client createClient(CreateClientRequest createClientRequest) {

        return clientRepository.save(
            Client.builder()
                .name(createClientRequest.name())
                .email(createClientRequest.email())
                .build()
        );
    }
}