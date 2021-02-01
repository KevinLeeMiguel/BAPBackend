package com.esteem.billingandpayment.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.esteem.billingandpayment.domain.Charge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChargeRepo extends JpaRepository<Charge, Long> {

    Optional<Charge> findByUuidAndDeletedStatus(String uuid, Boolean ds);

    List<Charge> findByDeletedStatus(Boolean ds);

    List<Charge> findByAccountCustomerUuidAndDeletedStatus(String uuid, Boolean ds);

    Optional<Charge> findByIdAndDeletedStatus(Long chargeId, boolean b);

    Optional<Charge> findByUuidAndDeletedStatusAndInvoiced(String uuid, boolean b, boolean c);

    @Query("select c from Charge c where c.deletedStatus=0 AND (c.canceled=0 OR c.invoiced=0) And c.postDate>=?1 And c.postDate<=?2 ")
    List<Charge> findAllInvoiceEligibleByPostDateRange(LocalDate startDate, LocalDate endDate);
    // List<Charge> findAllByDeletedStatusAndPostDateIsBetween(Boolean
    // deletedStatus, LocalDate startDate, LocalDate endDate);
}