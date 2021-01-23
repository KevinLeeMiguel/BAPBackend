package com.esteem.billingandpayment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.esteem.billingandpayment.controllers.ChargeController.ChargeRequest;
import com.esteem.billingandpayment.controllers.ChargeController.ProductReq;
import com.esteem.billingandpayment.controllers.ChargeController.ServiceReq;
import com.esteem.billingandpayment.domain.Account;
import com.esteem.billingandpayment.domain.Charge;
import com.esteem.billingandpayment.domain.ChargeProduct;
import com.esteem.billingandpayment.domain.ChargeServiceE;
import com.esteem.billingandpayment.domain.Product;
import com.esteem.billingandpayment.domain.ServiceE;
import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.repo.AccountRepo;
import com.esteem.billingandpayment.repo.ChargeProductRepo;
import com.esteem.billingandpayment.repo.ChargeRepo;
import com.esteem.billingandpayment.repo.ChargeServiceERepo;
import com.esteem.billingandpayment.repo.ProductRepo;
import com.esteem.billingandpayment.repo.ServiceRepo;
import com.esteem.billingandpayment.validations.ChargeValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ChargeServiceERepo chargeServiceERepo;

    private static final String ACCOUNT_NOT_FOUND_STRING = "Account not found!";
    private static final String CHARGE_NOT_FOUND_STRING = "Charge not found!";

    @Transactional
    public Charge createCharge(ChargeRequest req, String doneBy) {
        Optional<Account> account = accountRepo.findByCustomerUuidAndDeletedStatus(req.getCustomerUuid(), false);
        if (account.isPresent()) {
            Charge c = validation.validate(req.getCharge());
            c.setAccount(account.get());
            c.setDoneBy(doneBy);
            chargeRepo.save(c);
            int count = 0;
            int totalAmount = 0;
            for (ProductReq productReq : req.getProducts()) {
                Optional<Product> p = productRepo.findByUuidAndDeletedStatus(productReq.getUuid(), false);
                if (p.isPresent()) {
                    ChargeProduct cp = new ChargeProduct();
                    cp.setCharge(c);
                    cp.setQuantity(productReq.getQuantity());
                    cp.setProduct(p.get());
                    cp.setUnitPrice(productReq.getUnitPrice());
                    cp.setAmount(productReq.getUnitPrice()*productReq.getQuantity());
                    cp.setDoneBy(doneBy);
                    chargeProductRepo.save(cp);
                    totalAmount += cp.getAmount();
                } else {
                    count++;
                }
            }
            for (ServiceReq serviceReq: req.getServices()){
                Optional<ServiceE> s = serviceRepo.findByUuidAndDeletedStatus(serviceReq.getUuid(), false);
                if(s.isPresent()){
                    ChargeServiceE cs = new ChargeServiceE();
                    cs.setCharge(c);
                    cs.setService(s.get());
                    cs.setDoneBy(doneBy);
                    cs.setSpecialServiceQuantity(serviceReq.getSpecialServiceQuantity());
                    cs.setSpecialServiceUnit(serviceReq.getSpecialServiceUnit());
                    cs.setUnitPrice(serviceReq.getUnitPrice());
                    cs.setAmount(serviceReq.getUnitPrice()*serviceReq.getSpecialServiceQuantity());
                    chargeServiceERepo.save(cs);
                    totalAmount += cs.getAmount();
                } else {
                    count++;
                }
            }
            Account ac = account.get();
            ac.setBalance(ac.getBalance() + totalAmount);
            accountRepo.save(ac);
            c.setAmount(totalAmount);
            c = chargeRepo.save(c);
            if (count > 0) {
                throw new CustomValidationException("Some of the products/services were not found!!");
            } else {
                return c;
            }
        } else {
            throw new ObjectNotFoundException(ACCOUNT_NOT_FOUND_STRING);
        }
    }

    public void cleanCharge(Charge c){
        List<ChargeProduct> chargeProducts = chargeProductRepo.findByChargeAndDeletedStatus(c, false);
        for(ChargeProduct cp : chargeProducts){
            cp.setDeletedStatus(false);
        }
        chargeProductRepo.saveAll(chargeProducts);
        List<ChargeServiceE> chargeServices = chargeServiceERepo.findByChargeAndDeletedStatus(c, false);
        for(ChargeServiceE cs: chargeServices){
            cs.setDeletedStatus(false);
        }
        chargeServiceERepo.saveAll(chargeServices);
    }

    @Transactional
    public Charge updateCharge(String uuid, ChargeRequest req, String doneBy) {
        Optional<Charge> oldcharge = chargeRepo.findByUuidAndDeletedStatusAndInvoiced(uuid, false, false);
        if (oldcharge.isPresent()) {
            Charge c = validation.validate(req.getCharge());
            c.setAccount(oldcharge.get().getAccount());
            c.setLastUpdatedBy(doneBy);
            chargeRepo.save(c);
            cleanCharge(c);
            int count = 0;
            int totalAmount = 0;
            for (ProductReq productReq : req.getProducts()) {
                Optional<Product> p = productRepo.findByUuidAndDeletedStatus(productReq.getUuid(), false);
                if (p.isPresent()) {
                    ChargeProduct cp = new ChargeProduct();
                    cp.setCharge(c);
                    cp.setQuantity(productReq.getQuantity());
                    cp.setProduct(p.get());
                    cp.setAmount(productReq.getAmount());
                    cp.setDoneBy(doneBy);
                    chargeProductRepo.save(cp);
                    totalAmount += productReq.getAmount();
                } else {
                    count++;
                }
            }
            for (ServiceReq serviceReq: req.getServices()){
                Optional<ServiceE> s = serviceRepo.findByUuidAndDeletedStatus(serviceReq.getUuid(), false);
                if(s.isPresent()){
                    ChargeServiceE cs = new ChargeServiceE();
                    cs.setCharge(c);
                    cs.setService(s.get());
                    cs.setDoneBy(doneBy);
                    cs.setAmount(serviceReq.getAmount());
                    cs.setSpecialServiceQuantity(serviceReq.getSpecialServiceQuantity());
                    cs.setSpecialServiceUnit(serviceReq.getSpecialServiceUnit());
                    chargeServiceERepo.save(cs);
                    totalAmount += serviceReq.getAmount();
                } else {
                    count++;
                }
            }
            Account ac = oldcharge.get().getAccount();
            ac.setBalance(ac.getBalance() - oldcharge.get().getAmount() + totalAmount);
            accountRepo.save(ac);
            c.setAmount(totalAmount);
            c = chargeRepo.save(c);
            if (count > 0) {
                throw new CustomValidationException("Some of the products/services were not found!!");
            } else {
                return c;
            }
        } else {
            throw new ObjectNotFoundException(CHARGE_NOT_FOUND_STRING);
        }
    }

    public void deleteCharge(String uuid, String doneBy){
        Optional<Charge> charge = chargeRepo.findByUuidAndDeletedStatus(uuid, false);
        if (charge.isPresent()){
            Charge c = charge.get();
            if(c.getInvoiced().equals(true)){
                throw new CustomValidationException("Can not delete a charge that was already invoiced");
            }else{
                c.setDeletedStatus(true);
                c.setLastUpdatedBy(doneBy);
                chargeRepo.save(c);
            }
        }  
    }

  
    public Map<String,Object> findByUuid(String uuid) {
        Optional<Charge> charge = chargeRepo.findByUuidAndDeletedStatus(uuid, false);
        if (charge.isPresent()) {
            Map<String,Object> res = new HashMap<>();
            res.put("charge", charge.get());
            res.put("serviceCharges",chargeServiceERepo.findByChargeAndDeletedStatus(charge.get(), false));
            res.put("productCharges",chargeProductRepo.findByChargeAndDeletedStatus(charge.get(), false));
            return res;
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