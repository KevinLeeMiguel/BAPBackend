package com.esteem.billingandpayment.service;

import java.util.List;
import java.util.Optional;

import com.esteem.billingandpayment.domain.SystemUser;
import com.esteem.billingandpayment.exceptions.InvalidOldPasswordException;
import com.esteem.billingandpayment.exceptions.ObjectAlreadyExistException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.repo.UserRepo;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepo uService;

    public SystemUser signUp(final SystemUser user) {
        final String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        final SystemUser us = uService.save(user);
        return us;
    }

    public Optional<SystemUser> findByUsernameAndVerifiedStatus(String username, boolean verifiedStatus) {
        return uService.findByUsernameAndEmailVerifiedAndDeletedStatus(username, verifiedStatus, false);
    }

    public SystemUser update(SystemUser nUser) {
        Optional<SystemUser> user = uService.findByUuidNotAndUsernameAndEmailVerifiedAndDeletedStatus(nUser.getUuid(),
                nUser.getUsername(), true, false);
        if (user.isPresent()) {
            throw new ObjectAlreadyExistException("This Username is already used");
        } else {
            return uService.save(nUser);
        }
    }

    public SystemUser findByUuid(String uuid) {
        Optional<SystemUser> user = uService.findByUuidAndDeletedStatus(uuid, false);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ObjectNotFoundException("User Not found");
        }
    }

    public Boolean login(final String username, final String password) {
        final Optional<SystemUser> user = uService.findByUsernameAndDeletedStatus(username, false);
        if (user.isPresent()) {
            return BCrypt.checkpw(password, user.get().getPassword());
        } else {
            return false;
        }
    }

    public Boolean resetPassword(final SystemUser user, final String oldPassword, final String newPassword) {
        if (BCrypt.checkpw(oldPassword, user.getPassword())) {
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            uService.save(user);
            return true;
        } else {
            throw new InvalidOldPasswordException("Invalid Old Password");
        }
    }

    public List<SystemUser> findAll() {
        return uService.findByDeletedStatus(false);
    }

    public SystemUser findByUsername(String username) {
        Optional<SystemUser> user = uService.findByUsernameAndDeletedStatus(username, false);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ObjectNotFoundException("User with this email not found");
        }
    }

    public SystemUser findByUsernameUnverified(String username) {
        Optional<SystemUser> user = uService.findByUsernameAndDefaultPasswordAndDeletedStatus(username, true, false);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ObjectNotFoundException("User with this email not found");
        }
    }

    public String getRandomPassWord() {
        String password = "";
        int max = 9;
        int min = 1;
        int range = max - min + 1;
        for (int i = 0; i < 6; i++) {
            int x = (int) (Math.random() * range) + min;
            password = password + x;
        }
        return password;
    }

}