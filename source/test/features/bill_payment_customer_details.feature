Feature: Display Customer Details During Bill Payment
  As a merchant
  I want to see customer name and address when paying a bill
  So that I can confirm I'm paying the correct customer's bill

  Background:
    Given a merchant is on the bill payment screen

  Scenario: Display customer details when fetching bill information
    Given a customer exists with account number "ACC123456"
    And the customer has name "John Smith"
    And the customer has address "123 Main Street, Springfield"
    And the customer has an unpaid bill of 500
    When the merchant enters account number "ACC123456"
    And the merchant clicks the Get Amount button
    Then the customer name "John Smith" should be displayed
    And the customer address "123 Main Street, Springfield" should be displayed
    And the account number "ACC123456" should be displayed
    And the bill amount 500 should be displayed

  Scenario: Display customer details for different customer
    Given a customer exists with account number "ACC789012"
    And the customer has name "Jane Doe"
    And the customer has address "456 Oak Avenue, Riverside"
    And the customer has an unpaid bill of 750
    When the merchant enters account number "ACC789012"
    And the merchant clicks the Get Amount button
    Then the customer name "Jane Doe" should be displayed
    And the customer address "456 Oak Avenue, Riverside" should be displayed
    And the account number "ACC789012" should be displayed
    And the bill amount 750 should be displayed

  Scenario: Verify customer details help prevent payment errors
    Given a customer exists with account number "ACC111111"
    And the customer has name "Robert Johnson"
    And the customer has address "789 Pine Road, Lakewood"
    And the customer has an unpaid bill of 325
    When the merchant enters account number "ACC111111"
    And the merchant clicks the Get Amount button
    Then the merchant can verify the customer identity by seeing name "Robert Johnson"
    And the merchant can verify the customer location by seeing address "789 Pine Road, Lakewood"

  Scenario: Handle account with no unpaid bills
    Given a customer exists with account number "ACC222222"
    And the customer has name "Sarah Williams"
    And the customer has address "321 Elm Street, Greenville"
    And the customer has no unpaid bills
    When the merchant enters account number "ACC222222"
    And the merchant clicks the Get Amount button
    Then a message "No Unpaid Bills" should be displayed

  Scenario: Handle invalid account number
    When the merchant enters account number ""
    And the merchant clicks the Get Amount button
    Then a message "Please enter Account Number" should be displayed

  Scenario: Handle account number with spaces
    When the merchant enters account number "ACC 123 456"
    And the merchant clicks the Get Amount button
    Then a message "Account Number cannot have spaces." should be displayed
