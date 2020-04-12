package com.esteem.billingandpayment.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Location extends Metadata {
    @Id
    private long id;
    private String code;
    private String name;

    @ManyToOne
    private Location Parent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getParent() {
        return Parent;
    }

    public void setParent(Location parent) {
        Parent = parent;
    }

}