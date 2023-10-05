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
@DiscriminatorValue("1")
public class InhousePart extends Part{
    public int partId;

    public InhousePart() {
    }

    public InhousePart(String name, double price, int inv, int minInventory, int maxInventory, int partId) {
        super(name, price, inv, minInventory, maxInventory);
        //this.minInventory = minInventory;
        //this.maxInventory = maxInventory;
        //this.setMin(minInventory);
        //this.setMax(maxInventory);
        System.out.println("Min Inventory: " + this.minInventory);
        System.out.println("Max Inventory: " + this.maxInventory);
        this.setPartId(partId);
    }


    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }


}
