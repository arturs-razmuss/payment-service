package com.arpc.sotnim.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.money.MonetaryAmount;
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
    private Long transactionId;

    @Embedded
    private RequestData requestData;

    @OneToMany(
            mappedBy = "transfer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AccountBalanceChange> balanceChanges = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public static Payment initiate(Account debitAccount, Account creditAccount, PaymentRequest request) {
        var payment = new Payment();
        payment.requestData = mapToRequestData(request);
        payment.addBalanceChange(debitAccount.debit(payment));
        payment.addBalanceChange(creditAccount.credit(payment));
        return payment;
    }

    public List<AccountBalanceChange> getBalanceChanges() {
        return List.copyOf(balanceChanges);
    }

    private void addBalanceChange(AccountBalanceChange change) {
        balanceChanges.add(change);
        change.setPayment(this);
    }

    private static RequestData mapToRequestData(PaymentRequest request) {
        return RequestData.builder()
                .targetAmount(request.targetAmount())
                .build();
    }

    @Embeddable
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @ToString
    @EqualsAndHashCode
    public static class RequestData {
        private MonetaryAmount targetAmount;
    }
}
