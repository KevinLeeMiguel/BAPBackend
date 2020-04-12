package com.esteem.billingandpayment.repo;

import com.esteem.billingandpayment.domain.Charge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRepo extends JpaRepository<Charge, Long> {

}