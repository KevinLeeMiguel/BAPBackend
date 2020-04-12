package com.esteem.billingAndPayment.repo;

import java.util.List;
import java.util.Optional;

import com.esteem.billingAndPayment.domain.SystemUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findByUsernameAndDeletedStatus(String username, Boolean status);

    Optional<SystemUser> findByUsernameAndEmailVerifiedAndDeletedStatus(String username, Boolean emailVerified,
            Boolean ds);

    Optional<SystemUser> findByUuidAndDeletedStatus(String uuid, Boolean status);

    Optional<SystemUser> findByReferenceIdAndDeletedStatusAndReferenceName(long refId, Boolean status, String refName);

    Optional<SystemUser> findByUsernameAndDefaultPasswordAndDeletedStatus(String username, boolean dp, boolean ds);

    List<SystemUser> findByDeletedStatus(boolean ds);

    Optional<SystemUser> findByUuidAndDeletedStatus(String uuid, boolean ds);

    Optional<SystemUser> findByUuidNotAndUsernameAndDeletedStatus(String uuid, String username, boolean ds);

    Optional<SystemUser> findByUuidNotAndUsernameAndEmailVerifiedAndDeletedStatus(String uuid, String username,
            boolean ev, boolean ds);

    Long countByDeletedStatus(Boolean ds);
}