package com.esteem.billingandpayment.service;

import java.util.Optional;

import com.esteem.billingandpayment.domain.SystemUser;
import com.esteem.billingandpayment.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService implements UserDetailsService {
    @Autowired
    private UserRepo uService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SystemUser> optionalUser = uService.findByUsernameAndEmailVerifiedAndDeletedStatus(username, true,
                false);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException("Incorrect credentials");
        }
    }

}