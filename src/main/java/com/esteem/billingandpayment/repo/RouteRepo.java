package com.esteem.billingandpayment.repo;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.List;
import java.util.Optional;

import com.esteem.billingandpayment.domain.Route;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepo extends JpaRepository<Route,Long> {

    Optional<Route> findByUuidAndDeletedStatus(String uuid, boolean deletedStatus);
    Optional<Route> findByNameAndDeletedStatus(String name, boolean deletedStatus);
    List<Route> findByDeletedStatus(boolean deletedStatus);
}
