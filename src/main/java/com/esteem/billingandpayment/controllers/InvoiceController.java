package com.esteem.billingandpayment.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectAlreadyExistException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.service.InvoiceService;
import com.esteem.billingandpayment.utils.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices")
public class InvoiceController extends ResponseUtils {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("")
    public ResponseEntity<Object> createInvoice(@RequestBody InvoiceReq req, Authentication auth) {
        try {
            String doneBy = getDoneBy(auth);
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, invoiceService.createInvoice(req, doneBy)),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    @PostMapping("/customers/{uuid}/{startDate}/{endDate}")
    public ResponseEntity<Object> createInvoice(@PathVariable String uuid,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
            @RequestBody Map<String, String> invoice, Authentication auth) {
        try {
            String doneBy = getDoneBy(auth);
            return new ResponseEntity<>(
                    makeResponse(200, SUCCESS_MESSAGE,
                            invoiceService.generateInvoiceByPeriod(uuid, startDate, endDate, invoice, doneBy)),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Object> getInvoiceDetails(@PathVariable String uuid, Authentication auth) {
        try {
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, invoiceService.getInvoiceDetail(uuid)),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    public static class InvoiceReq {
        private String customerUuid = UUID.randomUUID().toString();
        private Map<String, String> invoice = new HashMap<>();
        private List<String> charges = new ArrayList<>();

        public Map<String, String> getInvoice() {
            return invoice;
        }

        public String getCustomerUuid() {
            return customerUuid;
        }

        public void setCustomerUuid(String customerUuid) {
            this.customerUuid = customerUuid;
        }

        public void setInvoice(Map<String, String> invoice) {
            this.invoice = invoice;
        }

        public List<String> getCharges() {
            return charges;
        }

        public void setCharges(List<String> charges) {
            this.charges = charges;
        }

    }

}
