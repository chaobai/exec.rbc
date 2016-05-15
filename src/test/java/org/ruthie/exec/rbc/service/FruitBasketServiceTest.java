package org.ruthie.exec.rbc.service;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ruthie.exec.rbc.model.FruitBasket;
import org.ruthie.exec.rbc.model.FruitLineItem;
import org.ruthie.exec.rbc.model.FruitType;

@RunWith(MockitoJUnitRunner.class)
public class FruitBasketServiceTest {
    @Mock
    private FruitTypeService fruitTypeService;
    @InjectMocks
    private FruitBasketService toTest = new FruitBasketService();
    
    @Test(expected=ResourceNotFoundException.class)
    public void testGetBasketTotalPriceWithWrongId() {
        String id = "id_not_exist";
        toTest.getBasketTotalPrice(id);
    }
    
    @Test
    public void testGetBasketTotalPriceSucceeds() {
        FruitBasket fullBasket = toTest.createBasket(null);
        String id = fullBasket.getId();
        when(fruitTypeService.getFruitType("apple")).thenReturn(new FruitType("apple", 2.8, true));
        when(fruitTypeService.getFruitType("banana")).thenReturn(new FruitType("banana", 2.2, false));
        
        toTest.addItemToBasket(id, new FruitLineItem("apple", 1, 0.1));
        toTest.addItemToBasket(id, new FruitLineItem("banana", 2, 0.2));
        
        // apple: 2.8 * 0.1 + 2.2 * 2 = 0.28 + 4.4
        double result = toTest.getBasketTotalPrice(id);
        assertEquals(4.68, result, 1e-10);
    }

    @Test
    public void testCreateBasket() {
        FruitBasket basket = toTest.createBasket(null);
        assertNotNull(basket);
        assertNotNull(basket.getId());
        assertNotNull(basket.getFruits());
        assertEquals(basket.getFruits().size(), 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateBasketFailsWithExistentId() {
        toTest.createBasket(new FruitBasket("ramdon_id"));
    }

    @Test
    public void testDiscardBasket() {
        FruitBasket basket = toTest.createBasket(null);
        assertNotNull(basket);
        assertEquals(toTest.getAllBaskets().size(), 1);
        
        toTest.discardBasket(basket.getId());
        assertEquals(toTest.getAllBaskets().size(), 0);
    }

    @Test
    public void testGetBasket() {
        FruitBasket basket = toTest.createBasket(null);
        assertNotNull(basket);
        FruitBasket theBasket = toTest.getBasket(basket.getId());
        Assert.assertEquals(basket, theBasket);
    }

    @Test
    public void testGetAllBaskets() {
        FruitBasket basket1 = toTest.createBasket(null);
        FruitBasket basket2 = toTest.createBasket(null);
        FruitBasket basket3 = toTest.createBasket(null);
        
        Collection<FruitBasket> result = toTest.getAllBaskets();
        assertNotNull(result);
        assertThat(result, hasSize(3));
        assertThat(result, containsInAnyOrder(basket1, basket2, basket3));
    }

    @Test
    public void testAddItemToBasket() {
        FruitBasket fullBasket = toTest.createBasket(null);
        String id = fullBasket.getId();
        when(fruitTypeService.getFruitType("apple")).thenReturn(new FruitType("apple", 2.8, true));
        when(fruitTypeService.getFruitType("banana")).thenReturn(new FruitType("banana", 2.2, false));
        when(fruitTypeService.getFruitType("orange")).thenReturn(new FruitType("orange", 1.7, true));
        
        FruitLineItem item1 = new FruitLineItem("apple", 1, 0.1);
        FruitLineItem item2 = new FruitLineItem("banana", 2, 0.2);
        FruitLineItem item3 = new FruitLineItem("orange", 3, 0.3);
        toTest.addItemToBasket(id, item1);
        toTest.addItemToBasket(id, item2);
        toTest.addItemToBasket(id, item3);
        
        Collection<FruitLineItem> result = toTest.getBasket(id).getFruits();
        assertNotNull(result);
        assertThat(result, hasSize(3));
        assertThat(result, containsInAnyOrder(item1, item2, item3));
    }
    
    @Test
    public void testAddItemToBasketWillMergeWhenSameTypeAdded() {
        FruitBasket fullBasket = toTest.createBasket(null);
        String id = fullBasket.getId();
        when(fruitTypeService.getFruitType("apple")).thenReturn(new FruitType("apple", 2.8, true));
        when(fruitTypeService.getFruitType("banana")).thenReturn(new FruitType("banana", 2.2, false));
        
        FruitLineItem item1 = new FruitLineItem("apple", 1, 0.1);
        FruitLineItem item2 = new FruitLineItem("banana", 2, 0.2);
        FruitLineItem item3 = new FruitLineItem("banana", 3, 0.3);
        toTest.addItemToBasket(id, item1);
        toTest.addItemToBasket(id, item2);
        toTest.addItemToBasket(id, item3);
        
        Collection<FruitLineItem> result = toTest.getBasket(id).getFruits();
        assertNotNull(result);
        assertThat(result, hasSize(2));
    }

    @Test(expected=ResourceNotFoundException.class)
    public void testAddItemToBasketFailsWithWrongTypeOfFruit() {
        FruitBasket basket = toTest.createBasket(null);
        assertNotNull(basket);
        String id = basket.getId();
        
        toTest.addItemToBasket(id, new FruitLineItem("exotic_fuit", 3, 0.3));
    }
    
    @Test
    public void testRemoveItemFromBasket() {
        FruitBasket fullBasket = toTest.createBasket(null);
        String id = fullBasket.getId();
        when(fruitTypeService.getFruitType("apple")).thenReturn(new FruitType("apple", 2.8, true));
        when(fruitTypeService.getFruitType("banana")).thenReturn(new FruitType("banana", 2.2, false));
        
        FruitLineItem item1 = new FruitLineItem("apple", 1, 0.1);
        FruitLineItem item2 = new FruitLineItem("banana", 2, 0.2);
        toTest.addItemToBasket(id, item1);
        toTest.addItemToBasket(id, item2);
        
        Collection<FruitLineItem> result = toTest.getBasket(id).getFruits();
        assertNotNull(result);
        assertThat(result, hasSize(2));
        
        toTest.removeItemFromBasket(id, item1.getFruitTypeName());
        result = toTest.getBasket(id).getFruits();
        assertNotNull(result);
        assertThat(result, hasSize(1));
        
        toTest.removeItemFromBasket(id, item2.getFruitTypeName());
        result = toTest.getBasket(id).getFruits();
        assertNotNull(result);
        assertThat(result, hasSize(0));
    }
}
