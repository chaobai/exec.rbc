package org.ruthie.exec.rbc.integration;

import org.junit.Assert;
import org.ruthie.exec.rbc.model.FruitType;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class FruitTypeSteps extends AbstractSteps {

    private HttpClientErrorException exceptionThrown;
    
    @Given("^the fruit type \"([^\"]*)\" is valid$")
    public void theFruitTypeIsValid(String fruitTypeName) throws Throwable {
        FruitType type = restTestHelper.getFruitType(fruitTypeName);
        Assert.assertNotNull(type);
        Assert.assertTrue(type.getUnitPrice() > 0);
    }

    @Given("^the fruit type \"([^\"]*)\" is not valid$")
    public void theFruitTypeIsNotValid(String fruitTypeName) throws Throwable {
        FruitType type = restTestHelper.getFruitType(fruitTypeName);
        Assert.assertNull(type);
    }

    @Given("^the unit price for \"([^\"]*)\" is (.+) pounds each$")
    public void theUnitPriceForByCountIs(String fruitTypeName, double unitPrice) throws Throwable {
        FruitType type = restTestHelper.getFruitType(fruitTypeName);
        Assert.assertNotNull(type);
        Assert.assertFalse(type.isPayByWeight());
        Assert.assertEquals(unitPrice, type.getUnitPrice(), 1e-10);
    }
    
    @Given("^the unit price for \"([^\"]*)\" is (.+) pounds per kg$")
    public void theUnitPriceForByWeightIs(String fruitTypeName, double unitPrice) throws Throwable {
        FruitType type = restTestHelper.getFruitType(fruitTypeName);
        Assert.assertNotNull(type);
        Assert.assertTrue(type.isPayByWeight());
        Assert.assertEquals(unitPrice, type.getUnitPrice(), 1e-10);
    }

    @When("^I update the unit price of \"([^\"]*)\" to (.+) pounds each$")
    public void iUpdateTheUnitPriceOfByCountTo(String fruitTypeName, double newUnitPrice) throws Throwable {
        FruitType type = new FruitType(fruitTypeName, newUnitPrice, false);
        try {
            restTestHelper.updateFruitType(type);
        } catch(HttpClientErrorException ex) {
            exceptionThrown = ex;
        }
    }
    
    @When("^I update the unit price of \"([^\"]*)\" to (.+) pounds per kg$")
    public void iUpdateTheUnitPriceOfByWeightTo(String fruitTypeName, double newUnitPrice) throws Throwable {
        FruitType type = new FruitType(fruitTypeName, newUnitPrice, true);
        try {
            restTestHelper.updateFruitType(type);
        } catch(HttpClientErrorException ex) {
            exceptionThrown = ex;
        }
    }
    

    @Then("^I verify the unit price of \"([^\"]*)\" is (.+) pounds each$")
    public void iVerifyTheUnitPriceOfByCountIs(String fruitTypeName, double unitPrice) throws Throwable {
        Assert.assertNull(exceptionThrown);
        this.theUnitPriceForByCountIs(fruitTypeName, unitPrice);
    }
    
    @Then("^I verify the unit price of \"([^\"]*)\" is (.+) pounds per kg$")
    public void iVerifyTheUnitPriceOfByWeightIs(String fruitTypeName, double unitPrice) throws Throwable {
        Assert.assertNull(exceptionThrown);
        this.theUnitPriceForByWeightIs(fruitTypeName, unitPrice);
    }

    @Then("^resource not found exception is thrown$")
    public void resourceNotFoundExceptionIsThrown() throws Throwable {
        Assert.assertNotNull(exceptionThrown);
        Assert.assertEquals(exceptionThrown.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
