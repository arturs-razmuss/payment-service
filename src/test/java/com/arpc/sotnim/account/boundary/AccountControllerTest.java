package com.arpc.sotnim.account.boundary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRespondWithCreatedWhenValidAccountCreationRequestArrives() throws Exception {
        mockMvc.perform(post("/api/v1/clients/{clientId}/accounts", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Savings",
                                    "currency": "USD"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "code":"00000",
                            "message":"success",
                            "data":{
                                "accountId":1,
                                "createdAt":"2021-01-01T00:00:00Z"
                            }
                        }
                        """));

    }
}