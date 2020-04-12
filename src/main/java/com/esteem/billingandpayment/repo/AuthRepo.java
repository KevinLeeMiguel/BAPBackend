package com.esteem.billingandpayment.repo;

import com.esteem.billingandpayment.domain.SystemUser;

public interface AuthRepo {

    SystemUser signUp(SystemUser user); 
    Boolean login(String username, String password);
    Boolean resetPassword(SystemUser user,String oldPassword,String newPassword);
   
}