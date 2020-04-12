package com.esteem.billingandpayment.service;

import java.util.List;

import com.esteem.billingandpayment.domain.SystemUser;
import com.esteem.billingandpayment.domain.SystemUserRole;
import com.esteem.billingandpayment.repo.SystemUserRoleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

  @Autowired
  private SystemUserRoleRepo userRoleRepo;

  public List<SystemUserRole> getByUser(SystemUser user) {
    return userRoleRepo.findByUserAndDeletedStatus(user, false);
  }

  public SystemUserRole createUserRole(SystemUserRole role) {
    return userRoleRepo.save(role);
  }
}