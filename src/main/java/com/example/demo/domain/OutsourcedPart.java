package com.example.demo.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 *
 *
 *
 */
@Entity
@DiscriminatorValue("2")
public class OutsourcedPart extends Part{
String companyName;

    public OutsourcedPart() {
    }

    public OutsourcedPart(String name, double price, int inv, int minInventory, int maxInventory, String companyName) {
        super(name, price, inv, minInventory, maxInventory);
        //this.minInventory = minInventory;
        //this.maxInventory = maxInventory;
        this.setCompanyName(companyName);
        this.setMinInventory(minInventory);
        this.setMaxInventory(maxInventory);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
