package com.esteem.billingAndPayment.repo;

import java.util.List;

import com.esteem.billingAndPayment.domain.SystemUser;
import com.esteem.billingAndPayment.domain.SystemUser_Role;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SystemUser_RoleRepo extends JpaRepository<SystemUser_Role, Long> {

    List<SystemUser_Role> findByUserAndDeletedStatus(SystemUser user,boolean ds);
    
}