package com.arpc.sotnim.client.entity;

import com.arpc.sotnim.ComponentTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


class ClientRepositoryTest extends ComponentTest {

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void shouldSaveClientWhenRequested() {
        var client = Client.builder()
                .name("John Doe")
                .email("mail@mail.com").build();
        client = clientRepository.saveAndFlush(client);

        var maybeClient = clientRepository.findById(client.getClientId());

        assertThat(maybeClient).isPresent();
        var savedClient = maybeClient.get();
        assertThat(savedClient)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(client);
        assertThat(savedClient.getClientId()).isNotNull();
        assertThat(savedClient.getCreatedAt()).isNotNull();
        assertThat(savedClient.getUpdatedAt()).isNotNull();
    }
}