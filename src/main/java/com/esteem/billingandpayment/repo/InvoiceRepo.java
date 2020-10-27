package com.esteem.billingandpayment.repo;

import com.esteem.billingandpayment.domain.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepo extends JpaRepository<Invoice,Long> {
    
}
