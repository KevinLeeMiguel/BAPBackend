package com.esteem.billingAndPayment.repo;

import com.esteem.billingAndPayment.domain.CustomerType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTypeRepo extends JpaRepository<CustomerType, Long> {

}