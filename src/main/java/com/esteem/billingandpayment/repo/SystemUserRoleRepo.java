package com.esteem.billingandpayment.repo;

import java.util.List;

import com.esteem.billingandpayment.domain.SystemUser;
import com.esteem.billingandpayment.domain.SystemUserRole;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SystemUserRoleRepo extends JpaRepository<SystemUserRole, Long> {

    List<SystemUserRole> findByUserAndDeletedStatus(SystemUser user,boolean ds);
    
}
