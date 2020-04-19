package com.esteem.billingandpayment.repo;

import java.util.List;
import java.util.Optional;

import com.esteem.billingandpayment.domain.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUuidAndDeletedStatus(String uuid, Boolean ds);

    List<Subscription> findByDeletedStatus(Boolean ds);

    List<Subscription> findByCustomerUuidAndDeletedStatus(String uuid, Boolean ds);
}
