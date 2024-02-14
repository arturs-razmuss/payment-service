package com.arpc.sotnim.client.boundary;

import com.arpc.sotnim.client.boundary.dto.CreateClientRequest;
import com.arpc.sotnim.client.control.ClientService;
import com.arpc.sotnim.client.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClientService clientService;

    @Test
    void shouldRespondCreatedWhenValidClientCreationRequest() throws Exception {
        var client = Client.builder()
                .clientId(998L)
                .createdAt(Instant.ofEpochSecond(978252))
                .build();

        when(clientService.createClient(any(CreateClientRequest.class))).thenReturn(client);

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "John Doe",
                                    "email": "john@doe.com"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.clientId").value(998))
                .andExpect(jsonPath("$.createdAt").value("1970-01-12T07:44:12Z"));
    }

}