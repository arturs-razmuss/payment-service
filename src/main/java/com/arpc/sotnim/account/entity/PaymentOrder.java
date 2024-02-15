package com.arpc.sotnim.account.entity;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

public interface PaymentOrder {
    MonetaryAmount getCreditAmount();

    MonetaryAmount getDebitAmount();

    BigDecimal getExchangeRate();
}

