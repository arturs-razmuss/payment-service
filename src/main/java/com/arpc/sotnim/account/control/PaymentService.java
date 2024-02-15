package com.arpc.sotnim.account.control;

import com.arpc.sotnim.account.boundary.dto.TransferRequest;
import com.arpc.sotnim.account.entity.AccountRepository;
import com.arpc.sotnim.account.entity.Payment;
import com.arpc.sotnim.account.entity.PaymentRepository;
import com.arpc.sotnim.account.entity.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.arpc.sotnim.core.boundary.ErrorCodes.ACCOUNT_NOT_FOUND;
import static com.arpc.sotnim.core.boundary.RequestProcessingException.withCode;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void transfer(TransferRequest request) {
        var debitAccount = accountRepository.findById(request.sourceAccountId())
                .orElseThrow(withCode(ACCOUNT_NOT_FOUND));
        var creditAccount = accountRepository.findById(request.targetAccountId())
                .orElseThrow(withCode(ACCOUNT_NOT_FOUND));
        var paymentRequest = new PaymentRequest(Money.of(request.instructedAmount().amount(), request.instructedAmount().currency()));

        var payment = Payment.initiate(debitAccount, creditAccount, paymentRequest);
        paymentRepository.save(payment);
    }
}
