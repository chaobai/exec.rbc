package org.ruthie.exec.rbc.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class FruitLineItem implements Serializable {

    private static final long serialVersionUID = -284596348677024042L;
    
    private String fruitTypeName;
    private int quantity;
    private double weight;
    
    public FruitLineItem() {
        super();
    }
    
    public FruitLineItem(String fruitTypeName, int quantity, double weight) {
        super();
        this.fruitTypeName = fruitTypeName;
        this.quantity = quantity;
        this.weight = weight;
    }

    public String getFruitTypeName() {
        return fruitTypeName;
    }
    public void setFruitTypeName(String fruitTypeName) {
        this.fruitTypeName = fruitTypeName;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public FruitLineItem merge(FruitLineItem fruit) {
        if (!this.getFruitTypeName().equals(fruit.getFruitTypeName())) {
            throw new IllegalArgumentException("Cannot merge line items of different type.");
        }
        this.setQuantity(this.quantity + fruit.getQuantity());
        this.setWeight(this.weight + fruit.getWeight());
        return this;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
