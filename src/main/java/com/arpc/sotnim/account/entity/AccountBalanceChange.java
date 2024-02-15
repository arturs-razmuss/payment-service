package com.arpc.sotnim.account.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
public class AccountBalanceChange {

    public AccountBalanceChange(Long accountId, int accountVersion, Payment payment, BigDecimal amount) {
        this.accountId = accountId;
        this.accountVersion = accountVersion;
        this.payment = payment;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long accountBalanceChangeId;

    private Long accountId;

    private int accountVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Payment payment;

    private BigDecimal amount;

}
