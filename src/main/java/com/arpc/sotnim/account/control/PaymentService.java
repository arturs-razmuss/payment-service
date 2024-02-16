package com.arpc.sotnim.account.control;

import com.arpc.sotnim.account.boundary.dto.MoneyAmountDto;
import com.arpc.sotnim.account.boundary.dto.PaymentRequest;
import com.arpc.sotnim.account.entity.*;
import com.arpc.sotnim.core.boundary.ErrorCodes;
import com.arpc.sotnim.core.boundary.RequestProcessingException;
import com.arpc.sotnim.exchange.control.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.money.MonetaryAmount;
import javax.money.convert.ExchangeRate;

import static com.arpc.sotnim.core.boundary.ErrorCodes.ACCOUNT_NOT_FOUND;
import static com.arpc.sotnim.core.boundary.ErrorCodes.SERVER_ERROR;
import static com.arpc.sotnim.core.boundary.RequestProcessingException.withCode;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final ExchangeService exchangeService;

    @Transactional
    public Payment transfer(PaymentRequest request) {
        Account debitAccount = accountRepository.findById(request.sourceAccountId())
                .orElseThrow(withCode(ACCOUNT_NOT_FOUND));
        Account creditAccount = accountRepository.findById(request.targetAccountId())
                .orElseThrow(withCode(ACCOUNT_NOT_FOUND));

        PaymentOrder paymentOrder = createPaymentOrder(debitAccount, creditAccount, request.instructedAmount());

        var payment = Payment.initiate(debitAccount, creditAccount, paymentOrder);
        return paymentRepository.save(payment);
    }

    private PaymentOrder createPaymentOrder(Account debitAccount, Account creditAccount, MoneyAmountDto instructedAmount) {
        MonetaryAmount creditAmount = toMonetaryAmount(instructedAmount);

        if (!creditAccount.getCurrency().equals(creditAmount.getCurrency())) {
            throw new RequestProcessingException(ErrorCodes.BAD_CURRENCY);
        }

        boolean isSingleCurrencyTransfer = debitAccount.getCurrency().equals(creditAccount.getCurrency());
        if (isSingleCurrencyTransfer) {
            return new SingleCurrencyOrder(creditAmount);
        } else {
            ExchangeRate exchangeRate = exchangeService.getExchangeRate(debitAccount.getCurrency(), creditAccount.getCurrency())
                    .orElseThrow(withCode(SERVER_ERROR));
            return new MultiCurrencyOrder(creditAmount, exchangeRate);
        }
    }

    @NonNull
    private static MonetaryAmount toMonetaryAmount(@NonNull MoneyAmountDto moneyAmountDto) {
        return Money.of(moneyAmountDto.amount(), moneyAmountDto.currency());
    }
}
