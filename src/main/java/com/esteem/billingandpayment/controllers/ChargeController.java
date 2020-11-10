package com.esteem.billingandpayment.controllers;

import java.util.List;
import java.util.Map;

import com.esteem.billingandpayment.domain.Unit;
import com.esteem.billingandpayment.exceptions.CustomValidationException;
import com.esteem.billingandpayment.exceptions.ObjectAlreadyExistException;
import com.esteem.billingandpayment.exceptions.ObjectNotFoundException;
import com.esteem.billingandpayment.service.ChargeService;
import com.esteem.billingandpayment.utils.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/charges")
public class ChargeController extends ResponseUtils {

    @Autowired
    private ChargeService chargeService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createCharge(@RequestBody ChargeRequest req, Authentication auth) {
        try {
            String doneBy = getDoneBy(auth);
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, chargeService.createCharge(req, doneBy)),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    @PutMapping(value = "/{uuid}")
    public ResponseEntity<Object> updateCharge(@PathVariable String uuid, @RequestBody ChargeRequest req,
            Authentication auth) {
        try {
            String doneBy = getDoneBy(auth);
            return new ResponseEntity<>(
                    makeResponse(200, SUCCESS_MESSAGE, chargeService.updateCharge(uuid, req, doneBy)), HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<Object> deleteCharge(@PathVariable String uuid, Authentication auth) {
        try {
            String doneBy = getDoneBy(auth);
            chargeService.deleteCharge(uuid, doneBy);
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, null), HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<Object> findByUuid(@PathVariable String uuid, Authentication auth) {
        try {
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, chargeService.findByUuid(uuid)),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> findAll(Authentication auth) {
        try {
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, chargeService.findAll()), HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/customers/{uuid}")
    public ResponseEntity<Object> findByCustomer(@PathVariable String uuid, Authentication auth) {
        try {
            return new ResponseEntity<>(makeResponse(200, SUCCESS_MESSAGE, chargeService.findByCustomer(uuid)),
                    HttpStatus.OK);
        } catch (ObjectNotFoundException | ObjectAlreadyExistException | CustomValidationException e) {
            return new ResponseEntity<>(makeResponse(400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(makeResponse(500, INTERNAL_ERROR_MESSAGE, null), HttpStatus.OK);
        }
    }

    public static class ChargeRequest {
        private String customerUuid;
        private Map<String, String> charge;
        private List<ProductReq> products;
        private List<ServiceReq> services;

        public Map<String, String> getCharge() {
            return charge;
        }

        public List<ServiceReq> getServices() {
            return services;
        }

        public void setServices(List<ServiceReq> services) {
            this.services = services;
        }

        public String getCustomerUuid() {
            return customerUuid;
        }

        public void setCustomerUuid(String customerUuid) {
            this.customerUuid = customerUuid;
        }

        public void setCharge(Map<String, String> charge) {
            this.charge = charge;
        }

        public List<ProductReq> getProducts() {
            return products;
        }

        public void setProducts(List<ProductReq> products) {
            this.products = products;
        }

    }

    public static class ProductReq {
        private String uuid;
        private int quantity;
        private double amount;

        public String getUuid() {
            return uuid;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

    }

    public static class ServiceReq {
        private String uuid;
        private double specialServiceQuantity;
        private Unit specialServiceUnit;
        private double amount;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public double getSpecialServiceQuantity() {
            return specialServiceQuantity;
        }

        public void setSpecialServiceQuantity(double specialServiceQuantity) {
            this.specialServiceQuantity = specialServiceQuantity;
        }

        public Unit getSpecialServiceUnit() {
            return specialServiceUnit;
        }

        public void setSpecialServiceUnit(Unit specialServiceUnit) {
            this.specialServiceUnit = specialServiceUnit;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

    }
}