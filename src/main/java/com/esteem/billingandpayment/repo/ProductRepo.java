package com.esteem.billingAndPayment.repo;

import com.esteem.billingAndPayment.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

}