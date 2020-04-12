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
        String name = req.get("name");
        return isValidString("name", name, 3);
    }

    public String validateDescription(Map<String, String> req) {
        String description = req.get("description");
        return isValidString("name", description, 3);
    }
}