package com.esteem.billingandpayment.validations;

import java.time.LocalDate;
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
        s.setStartDate(validateStartDate(req));
        s.setChargeId(validateChargeId(req));

        return s;
    }

    public String validateDescription(Map<String, String> req) {
        String description = req.get("description");
        return isValidString("description", description, 3);
    }

    public double validateAmount(Map<String, String> req) {
        return isValidDouble("amount", req.get("amount"));
    }

    public LocalDate validateStartDate(Map<String, String> req) {
        return isValidDate("startDate", req.get("startDate"));
    }

    public LocalDate validateEndDate(Map<String, String> req) {
        return isValidDate("endDate", req.get("endDate"));
    }

    public Long validateChargeId(Map<String, String> req) {
        return isValidLong("chargeId", req.get("chargeId"));
    }
}
