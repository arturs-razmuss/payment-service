package com.arpc.sotnim.account.entity;

import com.arpc.sotnim.core.boundary.ErrorCodes;
import com.arpc.sotnim.core.boundary.RequestProcessingException;
import io.hypersistence.utils.hibernate.type.money.MonetaryAmountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CompositeType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryException;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long accountId;

    private Long clientId;

    private String name;

    @AttributeOverride(name = "amount", column = @Column(name = "balance_amount"))
    @AttributeOverride(name = "currency", column = @Column(name = "balance_currency"))
    @CompositeType(MonetaryAmountType.class)
    private MonetaryAmount balance;

    @Version
    private int version = 0;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    AccountBalanceChange debit(PaymentOrder request) {
        var negatedAmount = request.getDebitAmount().negate();
        var newBalance = balance.add(negatedAmount);
        if (newBalance.isNegative()) {
            throw new RequestProcessingException(ErrorCodes.BALANCE_NOT_SUFFICIENT);
        }
        balance = newBalance;
        return new AccountBalanceChange(accountId, version, negatedAmount.getNumber().numberValue(BigDecimal.class));
    }

    AccountBalanceChange credit(PaymentOrder request) {
        var amount = request.getCreditAmount();
        try {
            balance = balance.add(amount);
        } catch (MonetaryException e) {
            throw new RequestProcessingException(ErrorCodes.BAD_CURRENCY);
        }
        return new AccountBalanceChange(accountId, version, amount.getNumber().numberValue(BigDecimal.class));
    }

    public CurrencyUnit getCurrency() {
        return balance.getCurrency();
    }

}