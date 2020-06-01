package com.esteem.billingandpayment.validations;

import java.util.Map;

import com.esteem.billingandpayment.domain.Product;

import org.springframework.stereotype.Component;

@Component
public class ProductValidation extends GeneralValidations {

    public Product validate(Map<String, String> req) {
        Product p = new Product();
        p.setName(validateName(req));
        p.setDescription(validateDescription(req));
        return p;
    }

    public String validateName(Map<String, String> req) {
        return isValidString("name", req.get("name"), 3);
    }

    public String validateDescription(Map<String, String> req) {
        return isValidOptionalString("description", req.get("description"), 3);
    }
}
