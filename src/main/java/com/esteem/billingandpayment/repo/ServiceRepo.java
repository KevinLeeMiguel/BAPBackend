package com.esteem.billingandpayment.repo;

import java.util.Optional;

import com.esteem.billingandpayment.domain.ServiceE;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<ServiceE, Long> {

    Optional<ServiceE> findByIdAndDeletedStatus(Long id, Boolean ds);
}