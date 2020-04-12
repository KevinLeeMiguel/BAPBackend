package com.esteem.billingandpayment.repo;

import com.esteem.billingandpayment.domain.Location;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<Location, Long> {

}