package com.esteem.billingandpayment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ChargeServiceE extends Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    private Charge charge;
    @ManyToOne
    private ServiceE service;
    private double specialServiceQuantity;
    private Unit specialServiceUnit = Unit.NA;
    private double amount;
    private double unitPrice;

    public Long getId() {
        return id;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    public ServiceE getService() {
        return service;
    }

    public void setService(ServiceE service) {
        this.service = service;
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

    
}