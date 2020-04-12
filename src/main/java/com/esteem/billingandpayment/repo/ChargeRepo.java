package com.esteem.billingAndPayment.repo;

import com.esteem.billingAndPayment.domain.Charge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRepo extends JpaRepository<Charge, Long> {

}