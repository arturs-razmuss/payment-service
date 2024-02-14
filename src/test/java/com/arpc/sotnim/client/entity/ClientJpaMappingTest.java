package com.arpc.sotnim.client.entity;

import com.arpc.sotnim.ComponentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ClientJpaMappingTest extends ComponentTest {

    @Autowired
    ClientRepository clientRepository;

    Client savedClient;

    @BeforeEach
    public void setUp() {
        var client = Client.builder()
                .name("John Doe")
                .email("mail@mail.com").build();
        savedClient = clientRepository.saveAndFlush(client);
    }

    @Test
    public void shouldPopulateAccountIdWhenPersisted() {
        assertThat(savedClient.getClientId()).isNotNull();
    }

    @Test
    public void shouldHaveMatchingCreatedAtAndUpdatedAtWhenNoModificationsAfterInitialSave() {
        assertThat(savedClient.getCreatedAt()).isNotNull();
        assertThat(savedClient.getUpdatedAt()).isEqualTo(savedClient.getCreatedAt());
    }

}