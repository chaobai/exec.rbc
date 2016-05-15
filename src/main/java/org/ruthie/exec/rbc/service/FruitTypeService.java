package org.ruthie.exec.rbc.service;

import java.util.Collection;
import java.util.Map;

import org.ruthie.exec.rbc.model.FruitType;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

@Service
public class FruitTypeService {
    
    private Map<String, FruitType> fruitTypes = Maps.newConcurrentMap();
    {
        fruitTypes.put("banana", new FruitType("banana", 1, true));
        fruitTypes.put("orange", new FruitType("orange", 2, true));
        fruitTypes.put("apple", new FruitType("apple", 3, false));
        fruitTypes.put("lemon", new FruitType("lemon", 4, false));
        fruitTypes.put("peach", new FruitType("peach", 5, false));
    }

    public void updateFruitType(FruitType type) {
        FruitType existentType = this.getFruitType(type.getName());
        existentType.setPayByWeight(type.isPayByWeight());
        existentType.setUnitPrice(type.getUnitPrice());
    }

    public Collection<FruitType> getAllFruitTypes() {
        return fruitTypes.values();
    }

    public FruitType getFruitType(String name) {
        FruitType type = fruitTypes.get(name);
        if (type == null) {
            throw new ResourceNotFoundException(FruitType.class, name);
        }
        return type;
    }
}
