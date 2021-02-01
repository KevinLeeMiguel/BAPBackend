package com.esteem.billingandpayment.repo;

import java.util.Optional;

import com.esteem.billingandpayment.domain.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepo extends JpaRepository<Invoice,Long> {
    Optional<Invoice> findByUuidAndDeletedStatus(String uuid, Boolean deletedStatus);
}
