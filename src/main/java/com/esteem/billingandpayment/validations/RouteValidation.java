package com.esteem.billingandpayment.validations;

import java.util.Map;

import com.esteem.billingandpayment.domain.Route;

import org.springframework.stereotype.Component;

@Component
public class RouteValidation extends GeneralValidations {
    
    public Route validate(Map<String,String> req){
        Route route = new Route();
        route.setName(isValidString("name", req.get("name"), 3));
        route.setDescription(isValidOptionalString("description", req.get("description"), 1));
        return route;
    }
}
