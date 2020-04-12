package com.esteem.billingAndPayment.repo;

import com.esteem.billingAndPayment.domain.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

}