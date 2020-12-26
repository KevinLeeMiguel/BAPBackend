package com.esteem.billingandpayment.repo;

import java.util.Optional;

import com.esteem.billingandpayment.domain.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {

    Optional<Account> findByCustomerUuidAndDeletedStatus(String uuid, Boolean ds);
    Optional<Account> findByIdAndDeletedStatus(Long id, Boolean ds);

}