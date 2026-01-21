Feature: Late Payment Fee Calculation
  As an electricity billing system
  I want to calculate late payment fees for overdue bills
  So that customers are charged appropriately for late payments

  Scenario: Calculate late payment fee for standard overdue bill
    Given a bill amount of 500
    And the bill is 15 days overdue
    When the late payment fee is calculated
    Then the late fee should be 25

  Scenario: Calculate late payment fee for longer overdue period
    Given a bill amount of 1000
    And the bill is 30 days overdue
    When the late payment fee is calculated
    Then the late fee should be 100

  Scenario: No late fee for bills not overdue
    Given a bill amount of 500
    And the bill is 0 days overdue
    When the late payment fee is calculated
    Then the late fee should be 0

  Scenario: Reject negative bill amount
    Given a bill amount of -100
    And the bill is 10 days overdue
    When the late payment fee is calculated
    Then an IllegalArgumentException should be thrown for late fee

  Scenario: Reject negative days overdue
    Given a bill amount of 500
    And the bill is -5 days overdue
    When the late payment fee is calculated
    Then an IllegalArgumentException should be thrown for late fee
