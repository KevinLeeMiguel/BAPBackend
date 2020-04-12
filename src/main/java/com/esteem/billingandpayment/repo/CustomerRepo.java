package com.esteem.billingandpayment.repo;

import java.util.Optional;

import com.esteem.billingandpayment.domain.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUuidAndDeletedStatus(String uuid, Boolean ds);
}