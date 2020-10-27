package com.esteem.billingandpayment.repo;

import com.esteem.billingandpayment.domain.InvoiceCharge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceChargeRepo extends JpaRepository<InvoiceCharge,Long> {
    
}
