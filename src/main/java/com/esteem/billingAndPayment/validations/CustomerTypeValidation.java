package com.esteem.billingandpayment.validations;

import java.util.Map;

import com.esteem.billingandpayment.domain.CustomerType;

import org.springframework.stereotype.Component;

@Component
public class CustomerTypeValidation extends GeneralValidations {

    public CustomerType validate(Map<String, String> req) {
        String name = validateName(req);
        String description = validateDescription(req);
        CustomerType ct = new CustomerType();
        ct.setName(name);
        ct.setDescription(description);
        return ct;
    }

    public String validateName(Map<String, String> req) {
        String name = req.get("name");
        return isValidString("name", name, 3);
    }

    public String validateDescription(Map<String, String> req) {
        String description = req.get("description");
        return isValidString("name", description, 3);
    }
}