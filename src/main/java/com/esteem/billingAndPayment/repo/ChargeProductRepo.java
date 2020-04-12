package com.esteem.billingandpayment.repo;

import com.esteem.billingandpayment.domain.ChargeProduct;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeProductRepo extends JpaRepository<ChargeProduct, Long> {

}