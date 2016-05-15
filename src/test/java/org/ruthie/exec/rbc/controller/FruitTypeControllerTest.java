package org.ruthie.exec.rbc.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.ruthie.exec.rbc.model.FruitType;
import org.ruthie.exec.rbc.service.FruitTypeService;

import com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class FruitTypeControllerTest {

    @Mock
    private FruitTypeService fruitTypeService;
    @InjectMocks
    private FruitTypeController toTest = new FruitTypeController();

    private ArgumentCaptor<String> fruitTypeNameCaptor = ArgumentCaptor.forClass(String.class);
    private ArgumentCaptor<FruitType> fruitTypeCaptor = ArgumentCaptor.forClass(FruitType.class);
    
    @Test
    public void testGetAllFruitTypes() {
        FruitType type1 = new FruitType("apple", 1.1, true);
        FruitType type2 = new FruitType("banana", 2.2, false);
        when(fruitTypeService.getAllFruitTypes()).thenReturn(Sets.newHashSet(type1, type2));
        Collection<FruitType> result = toTest.getAllFruitTypes();
        
        verify(fruitTypeService, times(1)).getAllFruitTypes();
        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertThat(result, containsInAnyOrder(type1, type2));
    }

    @Test
    public void updateFruitType() {
        FruitType typeWithNewPrice = new FruitType("apple", 2.1, false);
        toTest.updateFruitType(typeWithNewPrice);
        
        verify(fruitTypeService, times(1)).updateFruitType(fruitTypeCaptor.capture());
        assertEquals(fruitTypeCaptor.getValue(), typeWithNewPrice);
    }
    
    @Test
    public void getFruitType() {
        FruitType type = new FruitType("apple", 1.1, true);
        when(fruitTypeService.getFruitType(Mockito.anyString())).thenReturn(type);
        FruitType result = toTest.getFruitType(type.getName());
        
        verify(fruitTypeService, times(1)).getFruitType(fruitTypeNameCaptor.capture());
        assertEquals(fruitTypeNameCaptor.getValue(), type.getName());
        assertEquals(result, type);
    }
}
