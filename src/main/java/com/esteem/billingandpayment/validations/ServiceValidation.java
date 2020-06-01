package com.esteem.billingandpayment.validations;

import java.util.Map;

import com.esteem.billingandpayment.domain.ServiceE;

import org.springframework.stereotype.Component;

@Component
public class ServiceValidation extends GeneralValidations {

    public ServiceE validate(Map<String,String> req){
        ServiceE service = new ServiceE();
        service.setName(validateName(req));
        service.setDescription(validateDescription(req));
        return service;
    }

    public String validateName(Map<String,String> req){
        return isValidString("name", req.get("name"), 3);
    }

    public String validateDescription(Map<String,String> req){
        return isValidOptionalString("description", req.get("description"), 3);
    }
}
