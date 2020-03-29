package com.esteem.billingAndPayment.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Charge extends Metadata {

    @Id
    private Long id;
    private double amount;
    private Date deliveryDate;
    private Boolean delivered = false;
    private Boolean paid = false;
    private double specialServiceQuantity;
    private Unit specialServiceUnit;

    @ManyToOne
    private Service service;

    public Long getId() {
        return id;
    }

    public Unit getSpecialServiceUnit() {
        return specialServiceUnit;
    }

    public void setSpecialServiceUnit(Unit specialServiceUnit) {
        this.specialServiceUnit = specialServiceUnit;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public double getSpecialServiceQuantity() {
        return specialServiceQuantity;
    }

    public void setSpecialServiceQuantity(double specialServiceQuantity) {
        this.specialServiceQuantity = specialServiceQuantity;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}