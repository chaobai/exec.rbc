package org.ruthie.exec.rbc.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.ruthie.exec.rbc.model.FruitBasket;
import org.ruthie.exec.rbc.model.FruitLineItem;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class FruitBasketSteps extends AbstractSteps {
    private FruitBasket basket;
    
    @Given("^I create a new basket$")
    public void iCreateANewBasket() throws Throwable {
        basket = restTestHelper.createBasket();
        Assert.assertNotNull(basket.getId());
    }

    @When("^I add (\\d+) \"([^\"]*)\" of (.+) kg to the basket$")
    public void iAddOfKgToTheBasket(int quantity, String type, double weight) throws Throwable {
        Assert.assertNotNull(basket.getId());
        FruitLineItem fruitItem = new FruitLineItem(type, quantity, weight);
        restTestHelper.addFruitItemToBakset(basket.getId(), fruitItem);
    }

    @Then("^I verify the total price of the basket is (.+) pounds$")
    public void iVerifyTheTotalPriceOfTheBasketIsTotal(double expectedTotal) throws Throwable {
        Assert.assertNotNull(basket.getId());
        double actualTotal = restTestHelper.getTotalPrice(basket.getId());
        assertEquals(expectedTotal, actualTotal, 1e-10);
    }
}
