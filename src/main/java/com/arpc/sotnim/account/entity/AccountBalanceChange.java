package com.arpc.sotnim.account.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Immutable;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Immutable
public class AccountBalanceChange {

    public AccountBalanceChange(Long accountId, int accountVersion, BigDecimal amount) {
        this.accountId = accountId;
        this.accountVersion = accountVersion;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long accountBalanceChangeId;

    private Long accountId;

    private int accountVersion;


    @ManyToOne(fetch = FetchType.LAZY)
    @Setter(AccessLevel.PACKAGE)
    @JoinColumn(name = "payment_id")
    @ToString.Exclude
    private Payment payment;

    private BigDecimal amount;

}
