package com.esteem.billingandpayment.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account extends Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private Double balance = 0.0;
    @Enumerated(EnumType.STRING)
    private PaymentTime paymentTime;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private Long mainReferenceId;
    @JsonIgnore
    @OneToOne
    private Customer customer;

    public Long getId() {
        return id;
    }

    public Long getMainReferenceId() {
        return mainReferenceId;
    }

    public void setMainReferenceId(Long mainReferenceId) {
        this.mainReferenceId = mainReferenceId;
    }

    public PaymentTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(PaymentTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

}