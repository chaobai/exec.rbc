package org.ruthie.exec.rbc.model;

import org.junit.Assert;
import org.junit.Test;

public class FruitLineItemTest {

    private FruitLineItem item = new FruitLineItem("apple", 1, 0.4);
    
    @Test
    public void testMerge() {
        String name = item.getFruitTypeName();
        FruitLineItem item1 = new FruitLineItem(name, 10, 1.4);
        item = item.merge(item1);
        assertEquals(item, new FruitLineItem(name, 11, 1.8));
        
        FruitLineItem item2 = new FruitLineItem(name, 7, 1.41);
        item = item.merge(item2);
        assertEquals(item, new FruitLineItem(name, 18, 3.21));
        
        FruitLineItem item3 = new FruitLineItem(name, 102, 200.4);
        item = item.merge(item3);
        assertEquals(item, new FruitLineItem(name, 120, 203.61));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMergeDifferentType() {
        String name = item.getFruitTypeName();
        FruitLineItem item1 = new FruitLineItem(name, 10, 1.4);
        item = item.merge(item1);
        assertEquals(item, new FruitLineItem(name, 11, 1.8));
        
        FruitLineItem item2 = new FruitLineItem(name + "_diff", 7, 1.41);
        item = item.merge(item2);
    }
    
    private void assertEquals(FruitLineItem item1, FruitLineItem item2) {
        Assert.assertEquals(item1.getFruitTypeName(), item2.getFruitTypeName());
        Assert.assertEquals(item1.getQuantity(), item2.getQuantity());
        Assert.assertEquals(item1.getWeight(), item2.getWeight(), 1e-10);
    }
}
