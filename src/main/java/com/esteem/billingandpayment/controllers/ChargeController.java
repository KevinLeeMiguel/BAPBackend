package com.esteem.billingandpayment.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChargeController {

    public static class ChargeProductRequest {
        private String customerUuid;
        private Map<String, String> charge;
        private List<ProductReq> products;

        public Map<String, String> getCharge() {
            return charge;
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

        public String getUuid() {
            return uuid;
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
}