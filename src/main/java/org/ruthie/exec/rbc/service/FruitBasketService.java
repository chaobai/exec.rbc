package org.ruthie.exec.rbc.service;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.ruthie.exec.rbc.model.FruitBasket;
import org.ruthie.exec.rbc.model.FruitLineItem;
import org.ruthie.exec.rbc.model.FruitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

@Service
public class FruitBasketService {
    private Map<String, FruitBasket> baskets = Maps.newConcurrentMap();
    @Autowired
    private FruitTypeService fruitTypeService;
    
    public double getBasketTotalPrice(String id) {
        FruitBasket basket = this.getBasket(id);
        Map<String, FruitLineItem> fruitsMap = basket.getFruitsMap();
        double total = 0;
        for (Entry<String, FruitLineItem> entry : fruitsMap.entrySet()) {
            //always get the new fruit type data for calculation
            FruitType type = fruitTypeService.getFruitType(entry.getKey());
            //if fruits not recognized in basket, ignore
            if (type == null) {
                continue;
            }
            //calculate according to if it is to pay by weight
            FruitLineItem fruitItem = entry.getValue();
            if (type.isPayByWeight()) {
                total += type.getUnitPrice() * fruitItem.getWeight();
            } else {
                total += type.getUnitPrice() * fruitItem.getQuantity();
            }
        }
        return total;
    }

    public FruitBasket createBasket(FruitBasket basket) {
        if (basket != null && basket.getId() != null) {
            throw new IllegalArgumentException("Creating basket failed. Id must be null.");
        }
        
        FruitBasket newBasket = new FruitBasket(UUID.randomUUID().toString());
        baskets.put(newBasket.getId(), newBasket);
        
        return newBasket;
    }
    
    public void discardBasket(String id) {
        FruitBasket exist = this.getBasket(id);
        baskets.remove(exist.getId());
    }
    
    public FruitBasket getBasket(String id) {
        FruitBasket basket = baskets.get(id);
        if (basket == null) {
            throw new ResourceNotFoundException(FruitBasket.class, id);
        }
        
        return basket;
    }
    
    public Collection<FruitBasket> getAllBaskets() {
        return baskets.values();
    }

    public void addItemToBasket(String id, FruitLineItem item) {
        FruitBasket basket = this.getBasket(id);
        FruitType type = fruitTypeService.getFruitType(item.getFruitTypeName());
        if (type == null) {
            throw new ResourceNotFoundException(FruitType.class, item.getFruitTypeName());
        }
        basket.addItem(item);
    }

    public FruitBasket removeItemFromBasket(String id, String type) {
        FruitBasket basket = this.getBasket(id);
        basket.removeItem(type);
        return basket;
    }
}
