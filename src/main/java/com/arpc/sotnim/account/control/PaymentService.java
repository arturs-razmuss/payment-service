package com.arpc.sotnim.account.control;

import com.arpc.sotnim.account.entity.AccountRepository;
import com.arpc.sotnim.account.entity.Transfer;
import com.arpc.sotnim.account.entity.TransferRepository;
import com.arpc.sotnim.account.entity.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.arpc.sotnim.core.boundary.ErrorCodes.ACCOUNT_NOT_FOUND;
import static com.arpc.sotnim.core.boundary.RequestProcessingException.withCode;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    public void transfer(TransferRequest request) {
        var debitAccount = accountRepository.findById(request.sourceAccountId()).orElseThrow(withCode(ACCOUNT_NOT_FOUND));
        var creditAccount = accountRepository.findById(request.targetAccountId()).orElseThrow(withCode(ACCOUNT_NOT_FOUND));

        var transfer = Transfer.initiate(debitAccount, creditAccount, request);
        transferRepository.save(transfer);
    }
}
