package com.esteem.billingandpayment.repo;

import java.util.List;
import java.util.Optional;

import com.esteem.billingandpayment.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<Product> findByNameAndDeletedStatus(String name, Boolean ds);

    Optional<Product> findByUuidAndDeletedStatus(String uuid, Boolean ds);

    List<Product> findByDeletedStatus(Boolean ds);
}