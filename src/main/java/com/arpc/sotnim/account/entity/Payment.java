package com.arpc.sotnim.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
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

    private void addBalanceChange(AccountBalanceChange change) {
        balanceChanges.add(change);
        change.setPayment(this);
    }

    public Payment(Account debitAccount, Account creditAccount, PaymentRequest request) {
        requestData = mapToRequestData(request);
        this.addBalanceChange(debitAccount.debit(this));
        this.addBalanceChange(creditAccount.credit(this));
        //TODO: Ensure balance change sum is 0
    }

    private static RequestData mapToRequestData(PaymentRequest request) {
        return RequestData.builder()
                .sourceAccountId(request.sourceAccountId())
                .targetAccountId(request.targetAccountId())
                .targetAmount(request.amount())
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
        private Long sourceAccountId;
        private Long targetAccountId;
        private MonetaryAmount targetAmount;
    }
}
