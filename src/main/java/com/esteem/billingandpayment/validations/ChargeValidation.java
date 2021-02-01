package com.esteem.billingandpayment.validations;

import java.util.Map;

import com.esteem.billingandpayment.domain.Charge;
import com.esteem.billingandpayment.domain.ChargeType;
import com.esteem.billingandpayment.exceptions.CustomValidationException;

import org.springframework.stereotype.Component;

@Component
public class ChargeValidation extends GeneralValidations {

    public Charge validate(Map<String, String> req) {

        Charge c = new Charge();
        c.setAmount(validateAmount(req.get("amount")));
        c.setDeliveryDate(isValidDate("delivery date", req.get("deliveryDate")));
        c.setChargeType(validateChargeType(req.get("chargeType")));
        c.setDescription(isValidOptionalString("description", req.get("description"), 1));
        c.setPostDate(isValidDate("postDate", req.get("postDate")));
        return c;
    }

    public Double validateAmount(String amount) {
        return isValidDouble("amount", amount);
    }

    public ChargeType validateChargeType(String type) {
        String t = isValidString("charge type", type, 7);
        try {
            return ChargeType.valueOf(t.toUpperCase());
        } catch (Exception e) {
            throw new CustomValidationException("Invalid Charge Type");
        }
    }
}