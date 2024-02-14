package com.arpc.sotnim.account.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long>{
    List<Account> findByClientId(Long clientId);
}
