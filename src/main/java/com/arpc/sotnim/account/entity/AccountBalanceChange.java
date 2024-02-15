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

    public static AccountBalanceChange ofDebit(Account account, BigDecimal amount) {
        return new AccountBalanceChange(null, account, account.getVersion(), null, amount.negate());
    }

    public static AccountBalanceChange ofCredit(Account account, BigDecimal amount) {
        return new AccountBalanceChange(null, account, account.getVersion(), null, amount);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long accountBalanceChangeId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private int accountVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Transfer transfer;

    private BigDecimal amount;

}
