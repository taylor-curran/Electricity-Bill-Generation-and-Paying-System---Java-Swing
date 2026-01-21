Feature: Electricity Bill Calculation
  As a electricity billing system
  I want to calculate bills based on units consumed and unit price
  So that customers are charged correctly

  Scenario: Calculate basic bill amount
    Given the units consumed is 100
    And the unit price is 8
    When the bill is calculated
    Then the total amount should be 800

  Scenario: Calculate bill with negative units
    Given the units consumed is -10
    And the unit price is 8
    When the bill is calculated
    Then the total amount should be -80
