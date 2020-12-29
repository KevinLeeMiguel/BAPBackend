package com.esteem.billingandpayment.validations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.esteem.billingandpayment.domain.Account;
import com.esteem.billingandpayment.domain.AccountType;
import com.esteem.billingandpayment.domain.Customer;
import com.esteem.billingandpayment.domain.CustomerStatus;
import com.esteem.billingandpayment.domain.PaymentTime;
import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.repo.AccountRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidation extends GeneralValidations {

    @Autowired
    private AccountRepo accountRepo;

    public Customer validate(Map<String, String> req) {
        Customer customer = new Customer();
        customer.setName(validateName(req.get("name")));
        customer.setEmail(validateEmail(req.get("email")));
        customer.setPhone1(validatePhone1(req.get("phone1")));
        customer.setPhone2(validatePhone2(req.get("phone2")));
        customer.setStatus(validateCustomerStatus(req.get("customerStatus")));
        customer.setAddress(isValidOptionalString("address", req.get("address"), 1));
        customer.setProvince(isValidOptionalString("province", req.get("province"), 1));
        customer.setDistrict(isValidOptionalString("district", req.get("district"), 1));
        customer.setSector(isValidOptionalString("sector", req.get("sector"), 1));
        customer.setCell(isValidOptionalString("cell", req.get("cell"), 1));
        customer.setVillage(isValidOptionalString("village", req.get("village"), 1));
        customer.setContactPerson(isValidOptionalString("contactPerson", req.get("contactPerson"), 1));
        customer.setContactPersonTitle(isValidOptionalString("contactPersonTitle", req.get("contactPersonTitle"), 1));
        customer.setEmail2(isValidOptionalString("Email2", req.get("email2"), 3));
        customer.setNote(isValidOptionalString("Note", req.get("note"), 3));
        return customer;
    }

    public String validateName(String name) {
        return isValidString("name", name, 3);
    }

    public String validateEmail(String email) {
        return email != null ? isValidEmail("email", email) : null;
    }

    public String validatePhone1(String phone) {
        return phone != null ? isValidPhone("phone1", phone) : null;
    }

    public String validatePhone2(String phone) {
        return phone != null ? isValidPhone("phone2", phone) : null;
    }

    public CustomerStatus validateCustomerStatus(String status) {
        String st = isValidString("customerStatus", status, 6);
        try {
            return CustomerStatus.valueOf(st);
        } catch (Exception e) {
            throw new CustomValidationException("Invalid customer Status!");
        }
    }

    @SuppressWarnings("Unchecked")
    public Account validateAccount(Object acc) {

        Map<String, String> account = new HashMap<>();
        Account a = new Account();
        try {
            account = (Map<String, String>) acc;
        } catch (Exception e) {
            throw new CustomValidationException("Invalid Account Structure!");
        }
        try {
            a.setPaymentTime(PaymentTime.valueOf(account.get("paymentTime")));
        } catch (Exception e) {
            throw new CustomValidationException("Invalid Payment Time!");
        }
        try {
            a.setAccountType(AccountType.valueOf(account.get("accountType")));
        } catch (Exception e) {
            throw new CustomValidationException("Invalid Account Type!");
        }
        if (account.get("accountType").equals("SUB")) {
            String ref = isValidString("mainCustomerUuid", account.get("mainCustomerUuid"), 1);
            Optional<Account> ac = accountRepo.findByCustomerUuidAndDeletedStatus(ref, false);
            if (ac.isPresent())
                a.setMainReferenceId(ac.get().getId());
            else
                throw new CustomValidationException("Main account does not exist!");
        }
        return a;
    }

}