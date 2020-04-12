package com.esteem.billingAndPayment.repo;

import com.esteem.billingAndPayment.domain.Location;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<Location, Long> {

}