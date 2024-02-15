package com.arpc.sotnim.account.control;

import com.arpc.sotnim.account.boundary.dto.CreateAccountRequest;
import com.arpc.sotnim.account.entity.Account;
import com.arpc.sotnim.account.entity.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    @Value("${sotnim.account.initial-balance:0}")
    private final BigDecimal newAccountInitialBalance;

    private final AccountRepository accountRepository;

    @Transactional
    public Account createAccount(Long clientId, CreateAccountRequest request) {
        var account = Account.builder()
            .clientId(clientId)
            .name(request.name())
            .balance(Money.of(newAccountInitialBalance, request.currency()))
            .build();

        return accountRepository.save(account);
    }

    public List<Account> getAccounts(Long clientId) {
        return accountRepository.findByClientId(clientId);
    }
}
