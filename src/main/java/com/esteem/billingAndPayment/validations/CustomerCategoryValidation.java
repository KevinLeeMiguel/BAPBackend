package com.esteem.billingandpayment.validations;

import java.util.Map;

import com.esteem.billingandpayment.domain.CustomerCategory;

import org.springframework.stereotype.Component;

@Component
public class CustomerCategoryValidation extends GeneralValidations {

    public CustomerCategory validate(Map<String, String> req) {
        String name = validateName(req);
        String description = validateDescription(req);
        CustomerCategory c = new CustomerCategory();
        c.setName(name);
        c.setDescription(description);
        return c;
    }

    public String validateName(Map<String, String> req) {
        return isValidString("name", req.get("name"), 3);
    }

    public String validateDescription(Map<String, String> req) {
        return isValidString("description", req.get("description"), 3);
    }
}