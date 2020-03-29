package com.esteem.billingAndPayment.service;

import java.util.List;

import com.esteem.billingAndPayment.domain.SystemUser;
import com.esteem.billingAndPayment.domain.SystemUser_Role;
import com.esteem.billingAndPayment.repo.SystemUser_RoleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

  @Autowired
  private SystemUser_RoleRepo userRoleRepo;

  public List<SystemUser_Role> getByUser(SystemUser user) {
    return userRoleRepo.findByUserAndDeletedStatus(user, false);
  }

  public SystemUser_Role createUserRole(SystemUser_Role role) {
    return userRoleRepo.save(role);
  }
}