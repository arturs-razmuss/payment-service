package com.arpc.sotnim.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Transfer {

    @Id
    @GeneratedValue
    private Long transaction_id;

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

    public void addBalanceChange(AccountBalanceChange change) {
        balanceChanges.add(change);
        change.setTransfer(this);
    }

    public static Transfer initiate(Account debitAccount, Account creditAccount, TransferRequest request) {
        var transfer = Transfer.builder()
                .requestData(mapToRequestData(request))
                .build();

        var debitChange = AccountBalanceChange.builder()
                .account(debitAccount)
                .transfer(transfer)
                .amount(request.instructedAmount().amount())
                .build();

        var creditChange = AccountBalanceChange.builder()
                .account(creditAccount)
                .transfer(transfer)
                .amount(request.instructedAmount().amount())
                .build();

        transfer.addBalanceChange(debitChange);
        transfer.addBalanceChange(creditChange);

        return transfer;
    }

    private static RequestData mapToRequestData(TransferRequest request) {
        return RequestData.builder()
                .sourceAccountId(request.sourceAccountId())
                .targetAccountId(request.targetAccountId())
                .instructedAmount(request.instructedAmount().amount().toPlainString())
                .instructedCurrency(request.instructedAmount().currency())
                .build();
    }

    @Embeddable
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestData {
        private Long sourceAccountId;
        private Long targetAccountId;
        private String instructedAmount;
        private String instructedCurrency;


    }
}
