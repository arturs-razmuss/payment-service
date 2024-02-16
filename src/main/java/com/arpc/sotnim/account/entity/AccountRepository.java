package com.arpc.sotnim.account.entity;

import com.arpc.sotnim.account.entity.view.AccountBalanceChangeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long>{
    List<Account> findByClientId(Long clientId);

    @Query(value="""
            SELECT
                c.amount,
                p.payment_id as paymentId,
                p.created_at as createdAt
            FROM account_balance_change c
            JOIN payment p on p.payment_id = c.payment_id
            JOIN account a on a.account_id = c.account_id
            WHERE
                c.account_id = :accountId
                AND a.client_id = :clientId
            ORDER BY p.created_at DESC
            LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<AccountBalanceChangeView> findTransactions(Long clientId, Long accountId, Integer offset, Integer limit);
}
