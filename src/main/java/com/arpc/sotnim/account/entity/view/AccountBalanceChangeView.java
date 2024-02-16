package com.arpc.sotnim.account.entity.view;

import java.math.BigDecimal;
import java.time.Instant;

public interface AccountBalanceChangeView {
    BigDecimal getAmount();
    String getPaymentId();
    Instant getCreatedAt();
}
