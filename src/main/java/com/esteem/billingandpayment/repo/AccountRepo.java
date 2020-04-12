package com.esteem.billingandpayment.repo;

import com.esteem.billingandpayment.domain.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {

}