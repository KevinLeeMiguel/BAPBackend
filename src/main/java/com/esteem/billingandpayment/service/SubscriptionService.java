package com.esteem.billingandpayment.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.esteem.billingandpayment.domain.Customer;
import com.esteem.billingandpayment.domain.ServiceE;
import com.esteem.billingandpayment.domain.Subscription;
import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.repo.CustomerRepo;
import com.esteem.billingandpayment.repo.ServiceRepo;
import com.esteem.billingandpayment.repo.SubscriptionRepo;
import com.esteem.billingandpayment.validations.SubscriptionValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionValidation subscriptionValidation;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ServiceRepo serviceRepo;

    public Subscription create(String customerUuid, Map<String, String> req, String doneBy) {
        Optional<Customer> customer = customerRepo.findByUuidAndDeletedStatus(customerUuid, false);
        if (customer.isPresent()) {
            Subscription s = subscriptionValidation.validate(req);
            Optional<ServiceE> service = serviceRepo.findByIdAndDeletedStatus(s.getServiceId(), false);
            if (service.isPresent()) {
                s.setCustomer(customer.get());
                s.setDoneBy(doneBy);
                return subscriptionRepo.save(s);
            } else {
                throw new CustomValidationException("The service you are trying to subscribe to was not found!");
            }
        } else {
            throw new ObjectNotFoundException("The Customer wasn't found!");
        }
    }

    public Subscription findByUuid(String uuid) {
        Optional<Subscription> sub = subscriptionRepo.findByUuidAndDeletedStatus(uuid, false);
        if (sub.isPresent()) {
            return sub.get();
        } else {
            throw new ObjectNotFoundException("Subscription not found!");
        }
    }

    public List<Subscription> findAll() {
        return subscriptionRepo.findByDeletedStatus(false);
    }

    public List<Subscription> findByCustomer(String uuid) {
        return subscriptionRepo.findByCustomerUuidAndDeletedStatus(uuid, false);
    }
}
