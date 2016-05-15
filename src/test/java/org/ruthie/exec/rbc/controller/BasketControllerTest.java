package org.ruthie.exec.rbc.controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.ruthie.exec.rbc.model.FruitBasket;
import org.ruthie.exec.rbc.model.FruitLineItem;
import org.ruthie.exec.rbc.service.FruitBasketService;

import com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class BasketControllerTest {
    @Mock
    private FruitBasketService basketService;
    @InjectMocks
    private BasketController toTest = new BasketController();
    
    private ArgumentCaptor<FruitBasket> basketCaptor = ArgumentCaptor.forClass(FruitBasket.class);
    private ArgumentCaptor<String> basketKeyCaptor = ArgumentCaptor.forClass(String.class);
    private ArgumentCaptor<String> fruitTypeCaptor = ArgumentCaptor.forClass(String.class);
    private ArgumentCaptor<FruitLineItem> fruitItemCaptor = ArgumentCaptor.forClass(FruitLineItem.class);

    @Test
    public void testCreateBasket() {
        FruitBasket basket = new FruitBasket();
        FruitBasket basketWithId = new FruitBasket("new-id");
        when(basketService.createBasket(Mockito.any(FruitBasket.class))).thenReturn(basketWithId);
        FruitBasket result = toTest.createBasket(basket);
        
        verify(basketService, times(1)).createBasket(basketCaptor.capture());
        assertEquals(basketCaptor.getValue(), basket);
        assertEquals(result, basketWithId);
    }

    @Test
    public void testGetBasket() {
        FruitBasket basketWithId = new FruitBasket("new-id");
        when(basketService.getBasket(Mockito.anyString())).thenReturn(basketWithId);
        FruitBasket result = toTest.getBasket(basketWithId.getId());
        
        verify(basketService, times(1)).getBasket(basketKeyCaptor.capture());
        assertEquals(basketKeyCaptor.getValue(), basketWithId.getId());
        assertEquals(result, basketWithId);
    }

    @Test
    public void testDiscardBasket() {
        FruitBasket basketWithId = new FruitBasket("new-id");
        toTest.discardBasket(basketWithId);
        
        verify(basketService, times(1)).discardBasket(basketKeyCaptor.capture());
        assertEquals(basketKeyCaptor.getValue(), basketWithId.getId());
    }

    @Test
    public void testAddFruitToBasket() {
        FruitBasket basketWithId = new FruitBasket("new-id");
        FruitLineItem item = new FruitLineItem("apple", 1, 0.5);
        toTest.addFruitToBasket(basketWithId.getId(), item);
        
        verify(basketService, times(1)).addItemToBasket(basketKeyCaptor.capture(), fruitItemCaptor.capture());
        assertEquals(basketKeyCaptor.getValue(), basketWithId.getId());
        assertEquals(fruitItemCaptor.getValue(), item);
    }

    @Test
    public void testGetAllBaskets() {
        FruitBasket basketWithId = new FruitBasket("new-id");
        when(basketService.getAllBaskets()).thenReturn(Sets.newHashSet(basketWithId));
        Collection<FruitBasket> result = toTest.getAllBaskets();
        
        verify(basketService, times(1)).getAllBaskets();
        assertNotNull(result);
        assertEquals(result.size(), 1);
    }
    
    @Test
    public void testRemoveItemFromBasket() {
        FruitBasket basketWithId = new FruitBasket("new-id");
        when(basketService.removeItemFromBasket(Mockito.anyString(), Mockito.anyString())).thenReturn(basketWithId);
        String fruitTypeName = "apple";
        toTest.removeItemFromBasket(basketWithId.getId(), fruitTypeName);
        
        verify(basketService, times(1)).removeItemFromBasket(basketKeyCaptor.capture(), fruitTypeCaptor.capture());
        assertEquals(basketKeyCaptor.getValue(), basketWithId.getId());
        assertEquals(fruitTypeCaptor.getValue(), fruitTypeName);
    }

    @Test
    public void testGetTotalPriceOfBasket() {
        FruitBasket basketWithId = new FruitBasket("new-id");
        double total = 100.5;
        when(basketService.getBasketTotalPrice(Mockito.anyString())).thenReturn(total);
        Double result = toTest.getTotalPriceOfBasket(basketWithId.getId());
        
        verify(basketService, times(1)).getBasketTotalPrice(basketKeyCaptor.capture());
        assertEquals(basketKeyCaptor.getValue(), basketWithId.getId());
        assertEquals(result, total, 1e-10);
    }
}
