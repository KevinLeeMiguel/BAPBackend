package com.esteem.billingandpayment.repo;

import java.util.List;
import java.util.Optional;

import com.esteem.billingandpayment.domain.CustomerCategory;
import com.esteem.billingandpayment.domain.CustomerType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCategoryRepo extends JpaRepository<CustomerCategory, Long> {
        Optional<CustomerCategory> findByUuidAndDeletedStatus(String uuid, boolean b);

        Optional<CustomerCategory> findByNameAndTypeAndDeletedStatus(String name, CustomerType customerType, boolean b);

        List<CustomerCategory> findByTypeUuidAndDeletedStatus(String uuid, Boolean b);

        List<CustomerCategory> findByDeletedStatus(Boolean b);
}