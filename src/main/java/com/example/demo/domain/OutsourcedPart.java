package com.example.demo.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

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

    @Min(value = 1, message = "Inventory must be greater than or equal to the minimum")
    private Integer min;

    @Max(value = 1000, message = "Inventory must be less than or equal to the maximum")
    private Integer max;

    public OutsourcedPart() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getMin() {
        return min != null ? min : 0;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max != null ? max : 1000;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
}
