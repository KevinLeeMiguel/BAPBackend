package com.esteem.billingandpayment.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import com.esteem.billingandpayment.domain.Account;
import com.esteem.billingandpayment.domain.Customer;
import com.esteem.billingandpayment.domain.CustomerCategory;
import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.repo.AccountRepo;
import com.esteem.billingandpayment.repo.CustomerCategoryRepo;
import com.esteem.billingandpayment.repo.CustomerRepo;
import com.esteem.billingandpayment.validations.CustomerValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerValidation validation;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CustomerCategoryRepo categoryRepo;
    @Autowired
    private AccountRepo accountRepo;

    private Random random = new Random();

    private static final String CATEGORY_NOT_FOUND_STRING = "Invalid customer category!";
    private static final String CUSTOMER_NOT_FOUND_STRING = "Customer not found!";

    @Transactional
    public Customer createCustomer(Map<String, String> req, String doneBy) {

        Optional<CustomerCategory> category = categoryRepo.findByUuidAndDeletedStatus(req.get("customerCategoryUuid"),
                false);
        if (category.isPresent()) {
            Customer c = validation.validate(req);
            c.setCategory(category.get());
            c.setDoneBy(doneBy);
            customerRepo.save(c);
            Account a = new Account();
            a.setAccountNumber(generateAccountNumber() + "");
            a.setCustomer(c);
            a.setDoneBy(doneBy);
            accountRepo.save(a);
            return c;
        } else {
            throw new ObjectNotFoundException(CATEGORY_NOT_FOUND_STRING);
        }
    }

    public Customer updateCustomer(String uuid, Map<String, String> req, String doneBy) {
        Optional<Customer> customerO = customerRepo.findByUuidAndDeletedStatus(uuid, false);
        if (customerO.isPresent()) {
            Customer c = validation.validate(req);
            Customer customer = customerO.get();
            customer.setName(c.getName());
            customer.setEmail(c.getEmail());
            customer.setPhone1(c.getPhone1());
            customer.setPhone2(c.getPhone2());
            customer.setStatus(c.getStatus());
            customer.setLastUpdatedAt(new Date());
            customer.setLastUpdatedBy(doneBy);
            return customerRepo.save(customer);
        } else {
            throw new ObjectNotFoundException(CUSTOMER_NOT_FOUND_STRING);
        }
    }

    public Customer changeCategory(Map<String, String> req, String doneBy) {
        Optional<Customer> customerO = customerRepo.findByUuidAndDeletedStatus(req.get("uuid"), false);

        if (customerO.isPresent()) {
            Optional<CustomerCategory> category = categoryRepo
                    .findByUuidAndDeletedStatus(req.get("customerCategoryUuid"), false);
            if (category.isPresent()) {
                Customer c = customerO.get();
                c.setCategory(category.get());
                c.setLastUpdatedAt(new Date());
                c.setLastUpdatedBy(doneBy);
                return customerRepo.save(c);
            } else {
                throw new ObjectNotFoundException(CATEGORY_NOT_FOUND_STRING);
            }
        } else {
            throw new ObjectNotFoundException(CUSTOMER_NOT_FOUND_STRING);
        }
    }

    public void deleteCustomer(String uuid, String doneBy) {
        Optional<Customer> customerO = customerRepo.findByUuidAndDeletedStatus(uuid, false);
        if (customerO.isPresent()) {
            Customer c = customerO.get();
            c.setDeletedStatus(true);
            c.setLastUpdatedAt(new Date());
            c.setLastUpdatedBy(doneBy);
            customerRepo.save(c);
        } else {
            throw new ObjectNotFoundException(CUSTOMER_NOT_FOUND_STRING);
        }
    }

    public Customer findByUuid(String uuid) {
        Optional<Customer> customerO = customerRepo.findByUuidAndDeletedStatus(uuid, false);
        if (customerO.isPresent()) {
            return customerO.get();
        } else {
            throw new CustomValidationException(CUSTOMER_NOT_FOUND_STRING);
        }
    }

    public List<Customer> findAll() {
        return customerRepo.findByDeletedStatus(false);
    }

    public int generateAccountNumber() {
        return random.nextInt(100000);
    }

}