package com.arav.minorproject;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class BillCalculatorSteps {
    private int unitsConsumed;
    private int unitPrice;
    private int result;
    private Exception exception;

    @Given("the units consumed is {int}")
    public void setUnitsConsumed(int units) {
        this.unitsConsumed = units;
    }

    @Given("the unit price is {int}")
    public void setUnitPrice(int price) {
        this.unitPrice = price;
    }

    @When("the bill is calculated")
    public void calculateBill() {
        try {
            result = BillCalculator.calculateBillAmount(unitsConsumed, unitPrice);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the total amount should be {int}")
    public void verifyAmount(int expected) {
        assertEquals(expected, result);
    }

    @Then("a RuntimeException should be thrown")
    public void verifyException() {
        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }
}
