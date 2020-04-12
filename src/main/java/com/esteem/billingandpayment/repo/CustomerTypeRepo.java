package com.esteem.billingandpayment.repo;

import java.util.List;
import java.util.Optional;

import com.esteem.billingandpayment.domain.CustomerType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTypeRepo extends JpaRepository<CustomerType, Long> {
    Optional<CustomerType> findByUuidAndDeletedStatus(String uuid, Boolean deletedStatus);

    Optional<CustomerType> findByNameAndDeletedStatus(String name, Boolean deletedStatus);

    List<CustomerType> findByDeletedStatus(Boolean deletedStatus);
}