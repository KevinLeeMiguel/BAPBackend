package com.esteem.billingandpayment.validations;

import java.util.Date;
import java.util.Map;

import com.esteem.billingandpayment.domain.Subscription;

import org.springframework.stereotype.Component;

@Component
public class SubscriptionValidation extends GeneralValidations {

    public Subscription validate(Map<String, String> req) {

        Subscription s = new Subscription();
        s.setAmount(validateAmount(req));
        s.setDescription(validateDescription(req));
        s.setEndDate(validateEndDate(req));
        s.setStarDate(validateStartDate(req));
        s.setServiceId(validateServiceId(req));

        return s;
    }

    public String validateDescription(Map<String, String> req) {
        String description = req.get("description");
        return isValidString("description", description, 3);
    }

    public double validateAmount(Map<String,String> req){
        return isValidDouble("amount", req.get("amount"));
    }

    public Date validateStartDate(Map<String, String> req) {
        Date startDate = isValidDate("startDate", req.get("startDate"));
        return startDate;
    }

    public Date validateEndDate(Map<String, String> req) {
        Date endDate = isValidDate("endDate", req.get("endDate"));
        return endDate;
    }

    public Long validateServiceId(Map<String, String> req) {
        return isValidLong("serviceId", req.get("serviceId"));
    }
}