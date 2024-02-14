package com.arpc.sotnim.account.boundary;

import com.arpc.sotnim.account.boundary.dto.AccountBalanceDto;
import com.arpc.sotnim.account.boundary.dto.CreateAccountRequest;
import com.arpc.sotnim.account.boundary.dto.CreateAccountResponse;
import com.arpc.sotnim.account.control.AccountService;
import com.arpc.sotnim.core.boundary.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/api/v1/clients/{clientId}/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse<CreateAccountResponse> createAccount(@PathVariable("clientId") Long clientId,
                                                             @RequestBody CreateAccountRequest request) {
        var account = accountService.createAccount(clientId, request);
        return RestResponse.success(new CreateAccountResponse(account.getAccountId(), account.getCreatedAt()));
    }

    @GetMapping("/api/v1/clients/{clientId}/accounts")
    public RestResponse<List<AccountBalanceDto>> getAccounts(@PathVariable Long clientId) {
        var accountsBalances = accountService.getAccounts(clientId).stream()
                .map(AccountBalanceDto::from)
                .toList();

        return RestResponse.success(accountsBalances);
    }
}