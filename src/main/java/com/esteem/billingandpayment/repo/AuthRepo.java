package com.esteem.billingAndPayment.repo;

import com.esteem.billingAndPayment.domain.SystemUser;

public interface AuthRepo {

    SystemUser signUp(SystemUser user); 
    Boolean login(String username, String password);
    Boolean resetPassword(SystemUser user,String oldPassword,String newPassword);
   
}