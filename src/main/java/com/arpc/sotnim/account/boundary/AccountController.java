package com.arpc.sotnim.account.boundary;

import com.arpc.sotnim.core.boundary.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
public class AccountController {

    @PostMapping("/api/v1/clients/{clientId}/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse<CreateAccountResponse> createAccount(@PathVariable("clientId") Long clientId,
                                                             @RequestBody CreateAccountRequest request) {
        CreateAccountResponse response = new CreateAccountResponse(clientId, Instant.parse("2021-01-01T00:00:00Z"));
        return RestResponse.success(response);
    }
}