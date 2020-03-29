package com.esteem.billingAndPayment.repo;

import java.util.List;
import java.util.Optional;

import com.esteem.billingAndPayment.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
   Optional<Role> findByTitle(String title);
   List<Role> findByDeletedStatus(Boolean ds);
}