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
@DiscriminatorValue("1")
public class InhousePart extends Part{
    int partId;

    @Min(value = 0, message = "Inventory must be greater than or equal to zero.")
    private Integer min;

    @Max(value = 1000, message = "'Maximum Inventory' value must be 1,000 or less.")
    // @Max(value = max, message = "Inventory must be less than or equal to the maximum")
    private Integer max;

    public InhousePart() {
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public Integer getMin() {
        return min;
        // return min != null ? min : 0;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
        // return max != null ? max : 1000;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
}
