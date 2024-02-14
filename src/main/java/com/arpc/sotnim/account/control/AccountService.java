package com.arpc.sotnim.account.control;

import com.arpc.sotnim.account.boundary.dto.CreateAccountRequest;
import com.arpc.sotnim.account.entity.Account;
import com.arpc.sotnim.account.entity.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final BigDecimal NEW_ACCOUNT_INITIAL_BALANCE = BigDecimal.ZERO;

    private final AccountRepository accountRepository;

    @Transactional
    public Account createAccount(Long clientId, CreateAccountRequest request) {
        var account = Account.builder()
            .clientId(clientId)
            .name(request.name())
            .balance(Money.of(NEW_ACCOUNT_INITIAL_BALANCE, request.currency()))
            .build();

        return accountRepository.save(account);
    }
}
