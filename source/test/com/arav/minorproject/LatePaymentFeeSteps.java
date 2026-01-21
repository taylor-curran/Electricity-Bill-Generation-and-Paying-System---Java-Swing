package com.arav.minorproject;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class LatePaymentFeeSteps {
    private int billAmount;
    private int daysOverdue;
    private int lateFee;
    private Exception exception;

    @Given("a bill amount of {int}")
    public void setBillAmount(int amount) {
        this.billAmount = amount;
    }

    @Given("the bill is {int} days overdue")
    public void setDaysOverdue(int days) {
        this.daysOverdue = days;
    }

    @When("the late payment fee is calculated")
    public void calculateLateFee() {
        try {
            lateFee = BillCalculator.calculateLatePaymentFee(billAmount, daysOverdue);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the late fee should be {int}")
    public void verifyLateFee(int expected) {
        assertEquals(expected, lateFee);
    }

    @Then("an IllegalArgumentException should be thrown for late fee")
    public void verifyLateFeException() {
        assertNotNull(exception);
        assertTrue(exception instanceof IllegalArgumentException);
    }
}
