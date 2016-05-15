package org.ruthie.exec.rbc.integration;

import org.junit.Assert;
import org.ruthie.exec.rbc.model.FruitBasket;
import org.ruthie.exec.rbc.model.FruitLineItem;
import org.ruthie.exec.rbc.model.FruitType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class RestTestHelper {

    private int port = 8080;

    private String base;
    private RestTemplate template;

    public RestTestHelper() {
        this.base = "http://localhost:" + port;
        template = new RestTemplate();
    }
    
    public FruitBasket createBasket() {
        String url = base.toString() + "/baskets/";
        ResponseEntity<FruitBasket> response = template.postForEntity(url, new FruitBasket(), FruitBasket.class);
        return response.getBody();
    }

    public Double getTotalPrice(String id) {
        String url = base.toString() + "/baskets/" + id + "/total";
        Double response = getForObject(url, Double.class, null);
        return response;
    }

    public FruitBasket addFruitItemToBakset(String id, FruitLineItem fruitItem) {
        String url = base.toString() + "/baskets/" + id + "/fruits";
        ResponseEntity<FruitBasket> response = template.postForEntity(url, fruitItem, FruitBasket.class);
        return response.getBody();
    }
    
    public FruitType getFruitType(String fruitTypeName) {
        String url = base.toString() + "/fruits/" + fruitTypeName;
        FruitType response = getForObject(url, FruitType.class, null);
        return response;
    }

    public void updateFruitType(FruitType type) {
        String url = base.toString() + "/fruits";
        template.put(url, type);
    }
    
    private <T> T getForObject(String url, Class<T> clazz, HttpStatus expectedStatusCode) {
        T response = null;
        try {
            response = template.getForObject(url, clazz);
        } catch(HttpClientErrorException ex) {
            if (expectedStatusCode != null) {
                Assert.assertEquals(expectedStatusCode, ex.getStatusCode());
            }
        }
        return response;
    }
}