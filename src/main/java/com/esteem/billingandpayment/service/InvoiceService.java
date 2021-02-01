package com.esteem.billingandpayment.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.esteem.billingandpayment.controllers.InvoiceController.InvoiceReq;
import com.esteem.billingandpayment.domain.Account;
import com.esteem.billingandpayment.domain.AccountType;
import com.esteem.billingandpayment.domain.Charge;
import com.esteem.billingandpayment.domain.Customer;
import com.esteem.billingandpayment.domain.Invoice;
import com.esteem.billingandpayment.domain.InvoiceCharge;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.repo.AccountRepo;
import com.esteem.billingandpayment.repo.ChargeProductRepo;
import com.esteem.billingandpayment.repo.ChargeRepo;
import com.esteem.billingandpayment.repo.ChargeServiceERepo;
import com.esteem.billingandpayment.repo.CustomerRepo;
import com.esteem.billingandpayment.repo.InvoiceChargeRepo;
import com.esteem.billingandpayment.repo.InvoiceRepo;
import com.esteem.billingandpayment.validations.InvoiceValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private InvoiceChargeRepo invoiceChargeRepo;

    @Autowired
    private ChargeRepo chargeRepo;

    @Autowired
    private InvoiceValidation validation;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ChargeServiceERepo chargeServiceRepo;

    @Autowired
    private ChargeProductRepo chargeProductRepo;

    @Transactional
    public Map<String, Object> createInvoice(InvoiceReq req, String doneBy) {
        Optional<Customer> ocustomer = customerRepo.findByUuidAndDeletedStatus(req.getCustomerUuid(), false);
        if (ocustomer.isPresent()) {
            Customer customer = ocustomer.get();
            Account a;
            if (customer.getAccount().getAccountType().equals(AccountType.MAIN)) {
                a = customer.getAccount();
            } else {
                Optional<Account> acc = accountRepo.findByIdAndDeletedStatus(customer.getAccount().getMainReferenceId(),
                        false);
                if (acc.isPresent()) {
                    a = acc.get();
                } else {
                    throw new ObjectNotFoundException("Main Account not found!");
                }

            }
            Invoice i = validation.validate(req.getInvoice());
            i.setCustomer(customer);
            i.setAccount(a);
            i.setDoneBy(doneBy);
            i.setLastUpdatedBy(doneBy);
            invoiceRepo.save(i);
            List<Map<String, Object>> charges = new ArrayList<>();
            for (String uuid : req.getCharges()) {
                Optional<Charge> charge = chargeRepo.findByUuidAndDeletedStatusAndInvoiced(uuid, false, false);
                if (charge.isPresent()) {
                    Charge c = charge.get();
                    if (c.getAccount().equals(customer.getAccount())) {
                        InvoiceCharge ic = new InvoiceCharge();
                        ic.setInvoice(i);
                        ic.setCharge(c);
                        invoiceChargeRepo.save(ic);
                        c.setInvoiced(true);
                        chargeRepo.save(c);
                        Map<String, Object> chargeRes = new HashMap<>();
                        chargeRes.put("charge", c);
                        chargeRes.put("invoice_charge", ic);
                        chargeRes.put("charge_services", chargeServiceRepo.findByChargeAndDeletedStatus(c, false));
                        chargeRes.put("charge_products", chargeProductRepo.findByChargeAndDeletedStatus(c, false));
                        charges.add(chargeRes);
                    }
                }
            }
            Map<String, Object> res = new HashMap<>();
            res.put("invoice", i);
            res.put("charges", charges);
            return res;
        } else {
            throw new ObjectNotFoundException("Customer Not Found!");
        }
    }

    @Transactional
    public Map<String, Object> generateInvoiceByPeriod(String uuid, LocalDate startDate, LocalDate endDate,
            Map<String, String> invoice, String doneBy) {
        Optional<Customer> ocustomer = customerRepo.findByUuidAndDeletedStatus(uuid, false);

        if (ocustomer.isPresent()) {
            Customer customer = ocustomer.get();
            Account a;
            if (customer.getAccount().getAccountType().equals(AccountType.MAIN)) {
                a = customer.getAccount();
            } else {
                Optional<Account> acc = accountRepo.findByIdAndDeletedStatus(customer.getAccount().getMainReferenceId(),
                        false);
                if (acc.isPresent()) {
                    a = acc.get();
                } else {
                    throw new ObjectNotFoundException("Main Account not found!");
                }

            }
            Map<String, Object> res = new HashMap<>();
            Invoice inv = validation.validate(invoice);
            inv.setCustomer(ocustomer.get());
            inv.setAccount(a);
            inv.setDoneBy(doneBy);
            inv.setLastUpdatedBy(doneBy);
            invoiceRepo.save(inv);
            List<Charge> charges = chargeRepo.findAllInvoiceEligibleByPostDateRange(startDate, endDate);
            List<InvoiceCharge> invoiceCharges = new ArrayList<>();
            for (Charge charge : charges) {
                InvoiceCharge ivch = new InvoiceCharge();
                ivch.setCharge(charge);
                ivch.setInvoice(inv);
                invoiceChargeRepo.save(ivch);
                charge.setInvoiced(true);
                charge.setLastUpdatedBy(doneBy);
                chargeRepo.save(charge);
                invoiceCharges.add(ivch);
            }
            res.put("invoice", inv);
            res.put("invoice_charges", invoiceCharges);

            return res;
        }
        throw new ObjectNotFoundException("Customer Not Found!");
    }

    public Map<String,Object> getInvoiceDetail(String uuid){
        Optional<Invoice> inv = invoiceRepo.findByUuidAndDeletedStatus(uuid, false);
        if(inv.isPresent()){
            Map<String,Object> res = new HashMap<>();
            res.put("invoice", inv.get());
            res.put("invoice_charges", invoiceChargeRepo.findByInvoiceAndDeletedStatus(inv.get(), false));
            return res;
        }
        throw new ObjectNotFoundException("Invoice Not found!");

    }

}
