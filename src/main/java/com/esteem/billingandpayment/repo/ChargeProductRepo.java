package com.esteem.billingandpayment.repo;

import java.util.List;

import com.esteem.billingandpayment.domain.Charge;
import com.esteem.billingandpayment.domain.ChargeProduct;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeProductRepo extends JpaRepository<ChargeProduct, Long> {

    List<ChargeProduct> findByChargeAndDeletedStatus(Charge c, boolean ds);

}
