package com.esteem.billingandpayment.repo;

import java.util.List;
import java.util.Optional;

import com.esteem.billingandpayment.domain.Charge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRepo extends JpaRepository<Charge, Long> {

    Optional<Charge> findByUuidAndDeletedStatus(String uuid, Boolean ds);

    List<Charge> findByDeletedStatus(Boolean ds);

    List<Charge> findByAccountCustomerUuidAndDeletedStatus(String uuid, Boolean ds);
}