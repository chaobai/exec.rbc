package org.ruthie.exec.rbc.service;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.ruthie.exec.rbc.model.FruitType;

public class FruitTypeServiceTest {
    
    private FruitTypeService toTest = new FruitTypeService();

    @Test
    public void testUpdateFruitType() {
        String name = "peach";
        FruitType result = toTest.getFruitType(name);
        assertEquals(name, result.getName());
        assertNotEquals(15.5, result.getUnitPrice(), 1e-10);
        assertNotEquals(true, result.isPayByWeight());
        
        toTest.updateFruitType(new FruitType("peach", 15.5, true));
        
        result = toTest.getFruitType(name);
        assertEquals(name, result.getName());
        assertEquals(15.5, result.getUnitPrice(), 1e-10);
        assertEquals(true, result.isPayByWeight());
    }

    @Test
    public void testGetAllFruitTypes() {
        Collection<FruitType> result = toTest.getAllFruitTypes();
        assertNotNull(result);
        assertThat(result, hasSize(5));
    }

    @Test
    public void testGetFruitType() {
        String name = "peach";
        FruitType result = toTest.getFruitType(name);
        assertEquals(name, result.getName());
        assertThat(result.getUnitPrice(), greaterThan(0.0));
        
        toTest.updateFruitType(new FruitType("peach", 15.5, true));
        
        name = "orange";
        result = toTest.getFruitType(name);
        assertEquals(name, result.getName());
        assertThat(result.getUnitPrice(), greaterThan(0.0));
        
        name = "banana";
        result = toTest.getFruitType(name);
        assertEquals(name, result.getName());
        assertThat(result.getUnitPrice(), greaterThan(0.0));
    }
    
    @Test(expected=ResourceNotFoundException.class)
    public void testGetWrongFruitTypeFails() {
        String name = "exotic fruit";
        FruitType result = toTest.getFruitType(name);
        assertEquals(name, result.getName());
        assertThat(result.getUnitPrice(), greaterThan(0.0));
    }
}