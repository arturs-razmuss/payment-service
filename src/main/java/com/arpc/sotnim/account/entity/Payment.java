package com.arpc.sotnim.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
@Immutable
public class Payment {

    @Id
    @GeneratedValue
    private Long paymentId;

    private BigDecimal exchangeRate;

    @OneToMany(
            mappedBy = "payment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AccountBalanceChange> balanceChanges = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public static Payment initiate(Account debitAccount, Account creditAccount, PaymentOrder request) {
        var payment = new Payment();
        payment.exchangeRate = request.getExchangeRate();
        payment.addBalanceChange(debitAccount.debit(request));
        payment.addBalanceChange(creditAccount.credit(request));
        return payment;
    }

    public List<AccountBalanceChange> getBalanceChanges() {
        return List.copyOf(balanceChanges);
    }

    private void addBalanceChange(AccountBalanceChange change) {
        balanceChanges.add(change);
        change.setPayment(this);
    }
}
