package com.esteem.billingandpayment.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.esteem.billingandpayment.controllers.ChargeController.ChargeProductRequest;
import com.esteem.billingandpayment.controllers.ChargeController.ProductReq;
import com.esteem.billingandpayment.domain.Account;
import com.esteem.billingandpayment.domain.Charge;
import com.esteem.billingandpayment.domain.ChargeProduct;
import com.esteem.billingandpayment.domain.ChargeType;
import com.esteem.billingandpayment.domain.Product;
import com.esteem.billingandpayment.domain.ServiceE;
import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.repo.AccountRepo;
import com.esteem.billingandpayment.repo.ChargeProductRepo;
import com.esteem.billingandpayment.repo.ChargeRepo;
import com.esteem.billingandpayment.repo.ProductRepo;
import com.esteem.billingandpayment.repo.ServiceRepo;
import com.esteem.billingandpayment.validations.ChargeValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {

    @Autowired
    private ChargeValidation validation;
    @Autowired
    private ChargeRepo chargeRepo;
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ServiceRepo serviceRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ChargeProductRepo chargeProductRepo;

    private static final String SERVICE_NOT_FOUND_STRING = "Service not found!";
    private static final String ACCOUNT_NOT_FOUND_STRING = "Account not found!";
    private static final String CHARGE_NOT_FOUND_STRING = "Charge not found!";

    public Charge createSpecialServiceCharge(String uuid, Map<String, String> req, String doneBy) {
        Optional<Account> account = accountRepo.findByCustomerUuidAndDeletedStatus(uuid, false);
        if (account.isPresent()) {
            Optional<ServiceE> serviceO = serviceRepo.findByUuidAndDeletedStatus(req.get("serviceUuid"), false);
            if (serviceO.isPresent()) {
                Charge c = validation.validate(req);
                c.setChargeType(ChargeType.SPECIAL_SERVICE);
                c.setAccount(account.get());
                c.setDoneBy(doneBy);
                return chargeRepo.save(c);
            } else {
                throw new ObjectNotFoundException(SERVICE_NOT_FOUND_STRING);
            }
        } else {
            throw new ObjectNotFoundException(ACCOUNT_NOT_FOUND_STRING);
        }
    }

    public Charge createProductCharge(ChargeProductRequest req, String doneBy) {
        Optional<Account> account = accountRepo.findByCustomerUuidAndDeletedStatus(req.getCustomerUuid(), false);
        if (account.isPresent()) {
            Charge c = validation.validate(req.getCharge());
            c.setChargeType(ChargeType.SPECIAL_PRODUCT);
            c.setAccount(account.get());
            c.setDoneBy(doneBy);
            chargeRepo.save(c);
            int count = 0;
            for (ProductReq productReq : req.getProducts()) {
                Optional<Product> p = productRepo.findByUuidAndDeletedStatus(productReq.getUuid(), false);
                if (p.isPresent()) {
                    ChargeProduct cp = new ChargeProduct();
                    cp.setCharge(c);
                    cp.setQuantity(productReq.getQuantity());
                    cp.setProduct(p.get());
                    cp.setDoneBy(doneBy);
                    chargeProductRepo.save(cp);
                } else {
                    count++;
                }
            }
            if (count > 0) {
                throw new CustomValidationException("Some of the product were not found");
            } else {
                return c;
            }
        } else {
            throw new ObjectNotFoundException(ACCOUNT_NOT_FOUND_STRING);
        }
    }

    public Charge findByUuid(String uuid) {
        Optional<Charge> charge = chargeRepo.findByUuidAndDeletedStatus(uuid, false);
        if (charge.isPresent()) {
            return charge.get();
        } else {
            throw new ObjectNotFoundException(CHARGE_NOT_FOUND_STRING);
        }
    }

    public List<Charge> findAll() {
        return chargeRepo.findByDeletedStatus(false);
    }

    public List<Charge> findByCustomer(String customerUuid) {
        return chargeRepo.findByAccountCustomerUuidAndDeletedStatus(customerUuid, false);
    }

}