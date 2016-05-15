package org.ruthie.exec.rbc.model;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.google.common.collect.Lists;

public class FruitBasketTest {

    private FruitBasket basket = new FruitBasket("abc_123");
    @Test
    public void testAddItem() {
        basket.addItem(new FruitLineItem("apple", 1, 1));
        assertThat(basket.getFruits(), Matchers.hasSize(1));
        
        basket.addItem(new FruitLineItem("banana", 2, 2));
        assertThat(basket.getFruits(), Matchers.hasSize(2));
    }
    
    @Test
    public void testAddItemWithSameNameWillMerge() {
        basket.addItem(new FruitLineItem("apple", 1, 1));
        assertThat(basket.getFruits(), Matchers.hasSize(1));
        
        basket.addItem(new FruitLineItem("apple", 2, 2));
        assertThat(basket.getFruits(), Matchers.hasSize(1));
    }

    @Test
    public void testRemoveItem() {
        basket.addItem(new FruitLineItem("apple", 1, 1));
        assertThat(basket.getFruits(), Matchers.hasSize(1));
        
        basket.addItem(new FruitLineItem("banana", 2, 2));
        assertThat(basket.getFruits(), Matchers.hasSize(2));
        
        basket.removeItem("apple");
        assertThat(basket.getFruits(), Matchers.hasSize(1));
    }

    @Test
    public void testAddItems() {
        basket.addItems(Lists.newArrayList(
                new FruitLineItem("apple", 1, 1),
                new FruitLineItem("banana", 2, 2)));
        assertThat(basket.getFruits(), Matchers.hasSize(2));
    }

}
