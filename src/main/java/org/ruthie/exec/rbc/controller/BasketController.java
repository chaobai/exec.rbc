package org.ruthie.exec.rbc.controller;

import java.util.Collection;

import org.ruthie.exec.rbc.model.FruitBasket;
import org.ruthie.exec.rbc.model.FruitLineItem;
import org.ruthie.exec.rbc.service.FruitBasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class BasketController extends AbstractController {
    @Autowired
    private FruitBasketService basketService;
    
    BasketController() {
        super();
    }

    @ApiOperation(value = "Create basket")
    @RequestMapping(method = RequestMethod.POST, path="/baskets", produces = "application/json")
    public FruitBasket createBasket(@RequestBody FruitBasket basket) {
        return basketService.createBasket(basket);
    }
    
    @ApiOperation(value = "Get basket")
    @RequestMapping(method = RequestMethod.GET, path="/baskets/{id}", produces = "application/json")
    public FruitBasket getBasket(@PathVariable String id) {
        return basketService.getBasket(id);
    }
    
    @ApiOperation(value = "Discard basket")
    @RequestMapping(method = RequestMethod.DELETE, path="/baskets", produces = "application/json")
    public void discardBasket(@RequestBody FruitBasket basket) {
        basketService.discardBasket(basket.getId());
    }
    
    @ApiOperation(value = "Add fruit item to basket")
    @RequestMapping(method = RequestMethod.POST, path="/baskets/{id}/fruits", produces = "application/json")
    public FruitBasket addFruitToBasket(@PathVariable String id, @RequestBody FruitLineItem item) {
        basketService.addItemToBasket(id, item);
        return basketService.getBasket(id);
    }
    
    @ApiOperation(value = "Get all baskets")
    @RequestMapping(method = RequestMethod.GET, path="/baskets", produces = "application/json")
    public Collection<FruitBasket> getAllBaskets() {
        return basketService.getAllBaskets();
    }
    
    @ApiOperation(value = "Remove item from basket")
    @RequestMapping(method = RequestMethod.DELETE, path="/baskets/{id}/fruits", produces = "application/json")
    public void removeItemFromBasket(@PathVariable String id, @RequestParam String type) {
        basketService.removeItemFromBasket(id, type);
    }
    
    @ApiOperation(value = "Get total price of basket")
    @RequestMapping(method = RequestMethod.GET, path="/baskets/{id}/total", produces = "application/json")
    public Double getTotalPriceOfBasket(@PathVariable String id) {
        return basketService.getBasketTotalPrice(id);
    }
}