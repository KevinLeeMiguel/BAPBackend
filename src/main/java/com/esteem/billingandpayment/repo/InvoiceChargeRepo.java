package com.esteem.billingandpayment.repo;

import java.util.List;

import com.esteem.billingandpayment.domain.Invoice;
import com.esteem.billingandpayment.domain.InvoiceCharge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceChargeRepo extends JpaRepository<InvoiceCharge,Long> {
    List<InvoiceCharge> findByInvoiceAndDeletedStatus(Invoice invoice, Boolean deletedStatus);
}
