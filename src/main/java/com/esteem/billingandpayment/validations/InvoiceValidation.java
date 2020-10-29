package com.esteem.billingandpayment.validations;

import java.util.Map;

import com.esteem.billingandpayment.domain.Invoice;

import org.springframework.stereotype.Component;

@Component
public class InvoiceValidation extends GeneralValidations {
    
    public Invoice validate(Map<String, String> req){
        Invoice invoice = new Invoice();
        invoice.setDate(isValidDate("date", req.get("date")));
        invoice.setDueDate(isValidDate("Due date", req.get("dueDate")));
        invoice.setComment(isValidOptionalString("comment", req.get("comment"),3));

        return invoice;
    }
}
