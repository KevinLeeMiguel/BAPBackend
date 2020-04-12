package com.esteem.billingandpayment.repo;

import java.util.List;
import java.util.Optional;

import com.esteem.billingandpayment.domain.ServiceE;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<ServiceE, Long> {

    Optional<ServiceE> findByIdAndDeletedStatus(Long id, Boolean ds);

    Optional<ServiceE> findByNameAndDeletedStatus(String name, Boolean ds);

    Optional<ServiceE> findByUuidAndDeletedStatus(String uuid, Boolean ds);

    List<ServiceE> findByDeletedStatus(Boolean ds);
}