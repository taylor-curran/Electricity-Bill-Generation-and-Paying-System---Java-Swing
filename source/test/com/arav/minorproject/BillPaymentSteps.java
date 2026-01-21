package com.arav.minorproject;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class BillPaymentSteps {
    private String currentAccountNumber;
    private String customerName;
    private String customerAddress;
    private int billAmount;
    private boolean hasUnpaidBills;
    private String displayedName;
    private String displayedAddress;
    private String displayedAccountNumber;
    private int displayedBillAmount;
    private String errorMessage;

    @Given("a merchant is on the bill payment screen")
    public void merchantOnBillPaymentScreen() {
    }

    @Given("a customer exists with account number {string}")
    public void customerExistsWithAccountNumber(String accountNumber) {
        this.currentAccountNumber = accountNumber;
    }

    @Given("the customer has name {string}")
    public void customerHasName(String name) {
        this.customerName = name;
    }

    @Given("the customer has address {string}")
    public void customerHasAddress(String address) {
        this.customerAddress = address;
    }

    @Given("the customer has an unpaid bill of {int}")
    public void customerHasUnpaidBill(int amount) {
        this.billAmount = amount;
        this.hasUnpaidBills = true;
    }

    @Given("the customer has no unpaid bills")
    public void customerHasNoUnpaidBills() {
        this.hasUnpaidBills = false;
    }

    @When("the merchant enters account number {string}")
    public void merchantEntersAccountNumber(String accountNumber) {
        this.currentAccountNumber = accountNumber;
    }

    @When("the merchant clicks the Get Amount button")
    public void merchantClicksGetAmountButton() {
        String validationError = InputValidator.validateAccountNumber(currentAccountNumber);
        if (validationError != null) {
            if (validationError.contains("enter")) {
                errorMessage = "Please enter Account Number";
            } else if (validationError.contains("spaces")) {
                errorMessage = "Account Number cannot have spaces.";
            }
            return;
        }

        if (!hasUnpaidBills) {
            errorMessage = "No Unpaid Bills";
            return;
        }

        displayedName = customerName;
        displayedAddress = customerAddress;
        displayedAccountNumber = currentAccountNumber;
        displayedBillAmount = billAmount;
    }

    @Then("the customer name {string} should be displayed")
    public void customerNameShouldBeDisplayed(String expectedName) {
        assertEquals(expectedName, displayedName);
    }

    @Then("the customer address {string} should be displayed")
    public void customerAddressShouldBeDisplayed(String expectedAddress) {
        assertEquals(expectedAddress, displayedAddress);
    }

    @Then("the account number {string} should be displayed")
    public void accountNumberShouldBeDisplayed(String expectedAccountNumber) {
        assertEquals(expectedAccountNumber, displayedAccountNumber);
    }

    @Then("the bill amount {int} should be displayed")
    public void billAmountShouldBeDisplayed(int expectedAmount) {
        assertEquals(expectedAmount, displayedBillAmount);
    }

    @Then("the merchant can verify the customer identity by seeing name {string}")
    public void merchantCanVerifyCustomerIdentity(String expectedName) {
        assertEquals(expectedName, displayedName);
    }

    @Then("the merchant can verify the customer location by seeing address {string}")
    public void merchantCanVerifyCustomerLocation(String expectedAddress) {
        assertEquals(expectedAddress, displayedAddress);
    }

    @Then("a message {string} should be displayed")
    public void messageShouldBeDisplayed(String expectedMessage) {
        assertEquals(expectedMessage, errorMessage);
    }
}
