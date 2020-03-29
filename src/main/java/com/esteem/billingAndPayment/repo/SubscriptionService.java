package com.esteem.billingAndPayment.repo;

import com.esteem.billingAndPayment.domain.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionService extends JpaRepository<Subscription, Long> {

}