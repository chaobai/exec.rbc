package org.ruthie.exec.rbc.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class FruitType {
    
    private String name;
    private double unitPrice = 0;
    private boolean isPayByWeight = true;
    
    public FruitType() {
        super();
    }
    
    public FruitType(String name) {
        super();
        this.name = name;
    }
    
    public FruitType(String name, double unitPrice, boolean isPayByWeight) {
        super();
        this.name = name;
        this.unitPrice = unitPrice;
        this.isPayByWeight = isPayByWeight;
    }
    
    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public boolean isPayByWeight() {
        return isPayByWeight;
    }
    public void setPayByWeight(boolean isPayByWeight) {
        this.isPayByWeight = isPayByWeight;
    }
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
