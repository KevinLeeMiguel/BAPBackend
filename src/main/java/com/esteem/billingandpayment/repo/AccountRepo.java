package com.esteem.billingAndPayment.repo;

import com.esteem.billingAndPayment.domain.Account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {

}