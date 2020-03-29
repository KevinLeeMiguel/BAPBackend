package com.esteem.billingAndPayment.repo;

import com.esteem.billingAndPayment.domain.Service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Service, Long> {

}