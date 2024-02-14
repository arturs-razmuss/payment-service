package com.arpc.sotnim.account.entity;

import io.hypersistence.utils.hibernate.type.money.MonetaryAmountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CompositeType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.money.MonetaryAmount;
import java.time.Instant;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}