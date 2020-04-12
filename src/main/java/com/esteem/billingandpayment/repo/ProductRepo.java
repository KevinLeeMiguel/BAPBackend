package com.esteem.billingandpayment.repo;

import com.esteem.billingandpayment.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

}