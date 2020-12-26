package com.esteem.billingandpayment.repo;

import java.util.List;

import com.esteem.billingandpayment.domain.Charge;
import com.esteem.billingandpayment.domain.ChargeServiceE;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ChargeServiceERepo
 */
public interface ChargeServiceERepo extends JpaRepository<ChargeServiceE, Long> {

    List<ChargeServiceE> findByChargeAndDeletedStatus(Charge c, boolean ds);

}