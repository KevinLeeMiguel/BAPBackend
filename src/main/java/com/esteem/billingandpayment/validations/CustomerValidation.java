package com.esteem.billingandpayment.validations;

import java.util.Map;

import com.esteem.billingandpayment.domain.Customer;
import com.esteem.billingandpayment.domain.CustomerStatus;
import com.esteem.billingandpayment.exceptions.CustomValidationException;

import org.springframework.stereotype.Component;

@Component
public class CustomerValidation extends GeneralValidations {

    public Customer validate(Map<String, String> req) {

        Customer customer = new Customer();
        customer.setName(validateName(req.get("name")));
        customer.setEmail(validateEmail(req.get("email")));
        customer.setPhone1(validatePhone1(req.get("phone1")));
        customer.setPhone2(validatePhone2(req.get("phone2")));
        customer.setStatus(validateCustomerStatus(req.get("customerStatus")));
        return customer;
    }

    public String validateName(String name) {
        return isValidString("name", name, 3);
    }

    public String validateEmail(String email) {
        return isValidEmail("email", email);
    }

    public String validatePhone1(String phone) {
        return isValidPhone("phone1", phone);
    }

    public String validatePhone2(String phone) {
        return isValidPhone("phone2", phone);
    }

    public CustomerStatus validateCustomerStatus(String status) {
        String st = isValidString("customerStatus", status, 6);
        try {
            return CustomerStatus.valueOf(st);
        } catch (Exception e) {
            throw new CustomValidationException("Invalid customer Status!");
        }
    }

}