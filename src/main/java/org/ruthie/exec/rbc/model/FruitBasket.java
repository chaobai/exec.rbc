package org.ruthie.exec.rbc.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

/**
 * The class for fruit basket
 * @author chaobai
 *
 */
public class FruitBasket implements Serializable {

    private static final long serialVersionUID = -8913698995434026761L;
    
    private String id;
    private Map<String, FruitLineItem> fruits = Maps.newConcurrentMap();
    
    public FruitBasket() {
        super();
    }

    public FruitBasket(String id) {
        super();
        this.id = id;
    }
    
    public Collection<FruitLineItem> getFruits() {
        return fruits.values();
    }
    
    public void setFruits(List<FruitLineItem> fruitItems) {
        fruits.clear();
        this.addItems(fruitItems);
    }
    
    @JsonIgnore
    public Map<String, FruitLineItem> getFruitsMap() {
        return Maps.newHashMap(fruits);
    }

    public void addItem(FruitLineItem fruit) {
        FruitLineItem itemInBasket = fruits.get(fruit.getFruitTypeName());
        if (itemInBasket != null) {
            fruits.put(fruit.getFruitTypeName(), itemInBasket.merge(fruit));
        } else {
            fruits.put(fruit.getFruitTypeName(), fruit);
        }
        
    }
    
    public void removeItem(String fruitTypeName) {
        fruits.remove(fruitTypeName);
    }
    
    public void addItems(List<FruitLineItem> fruits) {
        for (FruitLineItem item : fruits) {
            this.addItem(item);
        }
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}