# Testing Documentation

This document describes the testing infrastructure, test execution procedures, and test coverage for the Electricity Bill Generation and Paying System.

## Testing Overview

The project employs a dual testing strategy combining traditional unit testing with behavior-driven development (BDD) testing:

**JUnit Unit Tests**: Test individual methods and classes in isolation, focusing on technical correctness and edge cases.

**Cucumber BDD Tests**: Test business scenarios using human-readable Gherkin syntax, ensuring the system behaves correctly from a business perspective.

## Testing Framework

### JUnit 4.13.2

JUnit is the primary unit testing framework used for testing Java code. The project uses JUnit 4 with Hamcrest matchers for expressive assertions.

Key dependencies:
- `junit-4.13.2.jar` - Core JUnit framework
- `hamcrest-core-1.3.jar` - Matcher library for assertions

### Cucumber 6.10.4

Cucumber is used for BDD testing, allowing tests to be written in plain English using Gherkin syntax. This makes tests readable by non-technical stakeholders and serves as living documentation.

Key dependencies:
- `cucumber-java-6.10.4.jar` - Java bindings for Cucumber
- `cucumber-junit-6.10.4.jar` - JUnit integration
- `cucumber-core-6.10.4.jar` - Core Cucumber functionality
- `gherkin-15.0.2.jar` - Gherkin parser
- Additional supporting JARs for expressions, datatables, and reporting

## Test Execution

### Running All Tests

The simplest way to run all tests is using the provided shell script:

```bash
./run_tests.sh
```

This script performs the following steps:

1. Creates output directories (`source/bin` and `source/test-bin`)
2. Verifies that required JAR files are present
3. Compiles source files to `source/bin`
4. Compiles test files to `source/test-bin`
5. Copies feature files to the test output directory
6. Runs JUnit tests
7. Runs Cucumber BDD tests

### Running Tests Manually

If you need more control over test execution, you can run tests manually.

#### Compiling Source and Tests

```bash
cd source

# Create output directories
mkdir -p bin test-bin

# Compile source files
javac -d bin src/com/arav/minorproject/BillCalculator.java \
             src/com/arav/minorproject/InputValidator.java \
             src/com/arav/minorproject/DBValues.java

# Set up classpath
CLASSPATH="bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar"
CLASSPATH="$CLASSPATH:lib/cucumber-java-6.10.4.jar:lib/cucumber-junit-6.10.4.jar"
CLASSPATH="$CLASSPATH:lib/cucumber-core-6.10.4.jar:lib/gherkin-15.0.2.jar"
CLASSPATH="$CLASSPATH:lib/cucumber-expressions-10.3.0.jar:lib/datatable-3.5.0.jar"
CLASSPATH="$CLASSPATH:lib/messages-15.0.0.jar:lib/tag-expressions-3.0.1.jar"
CLASSPATH="$CLASSPATH:lib/cucumber-plugin-6.10.4.jar:lib/cucumber-docstring-6.10.4.jar"
CLASSPATH="$CLASSPATH:lib/cucumber-gherkin-15.0.2.jar:lib/cucumber-gherkin-messages-15.0.2.jar"
CLASSPATH="$CLASSPATH:lib/html-formatter-13.0.0.jar:lib/create-meta-4.0.0.jar"
CLASSPATH="$CLASSPATH:lib/ci-environment-8.1.0.jar:lib/apiguardian-api-1.1.0.jar"

# Compile test files
javac -cp "$CLASSPATH" -d test-bin \
    test/com/arav/minorproject/BillCalculatorTest.java \
    test/com/arav/minorproject/InputValidatorTest.java \
    test/com/arav/minorproject/DBValuesTest.java \
    test/com/arav/minorproject/BillCalculatorSteps.java \
    test/com/arav/minorproject/CucumberTestRunner.java

# Copy feature files
mkdir -p test-bin/features
cp test/features/*.feature test-bin/features/
```

#### Running JUnit Tests Only

```bash
java -cp "test-bin:$CLASSPATH" org.junit.runner.JUnitCore \
    com.arav.minorproject.BillCalculatorTest \
    com.arav.minorproject.InputValidatorTest \
    com.arav.minorproject.DBValuesTest
```

#### Running Cucumber Tests Only

```bash
java -cp "test-bin:$CLASSPATH" io.cucumber.core.cli.Main \
    --glue com.arav.minorproject \
    --plugin pretty \
    test-bin/features
```

### Running Tests in an IDE

#### Eclipse

1. Right-click on a test class (e.g., `BillCalculatorTest.java`)
2. Select Run As > JUnit Test
3. View results in the JUnit view

For Cucumber tests:
1. Right-click on `CucumberTestRunner.java`
2. Select Run As > JUnit Test

#### IntelliJ IDEA

1. Right-click on a test class or test method
2. Select Run 'TestName'
3. View results in the Run tool window

For Cucumber tests:
1. Right-click on a `.feature` file
2. Select Run 'Feature: ...'

## Test Classes

### BillCalculatorTest

Location: `source/test/com/arav/minorproject/BillCalculatorTest.java`

This test class validates the `BillCalculator` utility class, which handles all bill amount calculations.

#### Test Methods

| Test Method | Description | Expected Result |
|-------------|-------------|-----------------|
| `testCalculateBillAmount_BasicCalculation` | Tests basic multiplication (100 units × 8 price) | Returns 800 |
| `testCalculateBillAmount_ZeroUnits` | Tests calculation with zero units | Returns 0 |
| `testCalculateBillAmount_MinUnitPrice` | Tests with minimum valid price (6) | Returns 600 for 100 units |
| `testCalculateBillAmount_MaxUnitPrice` | Tests with maximum valid price (10) | Returns 1000 for 100 units |
| `testCalculateBillAmount_LargeUnits` | Tests with large unit values (10000) | Returns 80000 |
| `testCalculateBillAmount_NegativeUnits` | Tests rejection of negative units | Throws IllegalArgumentException |
| `testCalculateBillAmount_NegativePrice` | Tests rejection of negative price | Throws IllegalArgumentException |
| `testIsValidUnitPrice_ValidMin` | Tests minimum valid price (6) | Returns true |
| `testIsValidUnitPrice_ValidMax` | Tests maximum valid price (10) | Returns true |
| `testIsValidUnitPrice_ValidMiddle` | Tests middle valid price (8) | Returns true |
| `testIsValidUnitPrice_TooLow` | Tests price below range (5) | Returns false |
| `testIsValidUnitPrice_TooHigh` | Tests price above range (11) | Returns false |
| `testIsValidUnitPrice_Negative` | Tests negative price (-1) | Returns false |
| `testCalculateBillAmountFromStrings_Basic` | Tests string input conversion | Returns "800" |
| `testCalculateBillAmountFromStrings_Zero` | Tests string conversion with zero | Returns "0" |
| `testCalculateBillAmountFromStrings_InvalidUnits` | Tests invalid string input | Throws NumberFormatException |
| `testCalculateBillAmountFromStrings_InvalidPrice` | Tests invalid price string | Throws NumberFormatException |
| `testCalculateBillAmountFromStrings_EmptyUnits` | Tests empty string input | Throws NumberFormatException |

### InputValidatorTest

Location: `source/test/com/arav/minorproject/InputValidatorTest.java`

This test class validates the `InputValidator` utility class, which handles all input validation for the application.

#### Test Methods

**Username Validation Tests**

| Test Method | Description | Expected Result |
|-------------|-------------|-----------------|
| `testValidateUsername_Valid` | Tests valid username | Returns null (valid) |
| `testValidateUsername_Empty` | Tests empty username | Returns error message |
| `testValidateUsername_Null` | Tests null username | Returns error message |
| `testValidateUsername_WithSpaces` | Tests username with spaces | Returns error message |
| `testValidateUsername_LeadingSpace` | Tests username with leading space | Returns error message |
| `testValidateUsername_TrailingSpace` | Tests username with trailing space | Returns error message |

**Password Validation Tests**

| Test Method | Description | Expected Result |
|-------------|-------------|-----------------|
| `testValidatePassword_Valid` | Tests valid password | Returns null (valid) |
| `testValidatePassword_Empty` | Tests empty password | Returns error message |
| `testValidatePassword_Null` | Tests null password | Returns error message |
| `testValidatePassword_WithSpaces` | Tests password with spaces | Returns null (spaces allowed) |

**Account Number Validation Tests**

| Test Method | Description | Expected Result |
|-------------|-------------|-----------------|
| `testValidateAccountNumber_Valid` | Tests valid account number | Returns null (valid) |
| `testValidateAccountNumber_Empty` | Tests empty account number | Returns error message |
| `testValidateAccountNumber_Null` | Tests null account number | Returns error message |
| `testValidateAccountNumber_WithSpaces` | Tests account number with spaces | Returns error message |

**Helper Method Tests**

| Test Method | Description | Expected Result |
|-------------|-------------|-----------------|
| `testIsBlank_Empty` | Tests empty string | Returns true |
| `testIsBlank_Null` | Tests null value | Returns true |
| `testIsBlank_Whitespace` | Tests whitespace-only string | Returns true |
| `testIsBlank_NotBlank` | Tests non-blank string | Returns false |
| `testContainsSpaces_WithSpace` | Tests string with space | Returns true |
| `testContainsSpaces_NoSpace` | Tests string without space | Returns false |
| `testContainsSpaces_Null` | Tests null value | Returns false |

**Contact Number Validation Tests**

| Test Method | Description | Expected Result |
|-------------|-------------|-----------------|
| `testValidateContactNumber_Valid` | Tests valid 10-digit number | Returns null (valid) |
| `testValidateContactNumber_Empty` | Tests empty contact | Returns error message |
| `testValidateContactNumber_Null` | Tests null contact | Returns error message |
| `testValidateContactNumber_WithLetters` | Tests contact with letters | Returns error message |
| `testValidateContactNumber_TooShort` | Tests contact under 10 digits | Returns error message |
| `testValidateContactNumber_ExactlyTenDigits` | Tests exactly 10 digits | Returns null (valid) |
| `testValidateContactNumber_MoreThanTenDigits` | Tests more than 10 digits | Returns null (valid) |

### DBValuesTest

Location: `source/test/com/arav/minorproject/DBValuesTest.java`

This test class validates the `DBValues` configuration class to ensure database connection parameters are properly set.

#### Test Methods

| Test Method | Description | Expected Result |
|-------------|-------------|-----------------|
| `testDatabaseNameIsSet` | Verifies database name is set | dbname = "swing_electricity_bill" |
| `testDatabaseHostIsSet` | Verifies host is not empty | dbhost is not null/empty |
| `testDatabasePortIsSet` | Verifies port is set | dbport = "3306" |
| `testDatabaseUsernameIsSet` | Verifies username is set | dbuname is not null/empty |
| `testDatabasePasswordIsNotNull` | Verifies password field exists | dbpass is not null |
| `testDefaultHostIsLocalhost` | Verifies default host | dbhost = "localhost" |
| `testDefaultUsernameIsRoot` | Verifies default username | dbuname = "root" |

## BDD Feature Files

### bill_calculation.feature

Location: `source/test/features/bill_calculation.feature`

This feature file describes the bill calculation behavior in business terms.

```gherkin
Feature: Electricity Bill Calculation
  As a electricity billing system
  I want to calculate bills based on units consumed and unit price
  So that customers are charged correctly

  Scenario: Calculate basic bill amount
    Given the units consumed is 100
    And the unit price is 8
    When the bill is calculated
    Then the total amount should be 800

  Scenario: Reject negative units
    Given the units consumed is -10
    And the unit price is 8
    When the bill is calculated
    Then an IllegalArgumentException should be thrown
```

### Step Definitions

Location: `source/test/com/arav/minorproject/BillCalculatorSteps.java`

The step definitions class implements the Gherkin steps in Java:

```java
@Given("the units consumed is {int}")
public void theUnitsConsumedIs(int units) {
    this.unitsConsumed = units;
}

@Given("the unit price is {int}")
public void theUnitPriceIs(int price) {
    this.unitPrice = price;
}

@When("the bill is calculated")
public void theBillIsCalculated() {
    try {
        this.result = BillCalculator.calculateBillAmount(unitsConsumed, unitPrice);
        this.exceptionThrown = false;
    } catch (IllegalArgumentException e) {
        this.exceptionThrown = true;
    }
}

@Then("the total amount should be {int}")
public void theTotalAmountShouldBe(int expectedAmount) {
    assertEquals(expectedAmount, result);
}

@Then("an IllegalArgumentException should be thrown")
public void anIllegalArgumentExceptionShouldBeThrown() {
    assertTrue(exceptionThrown);
}
```

### Cucumber Test Runner

Location: `source/test/com/arav/minorproject/CucumberTestRunner.java`

The runner class configures Cucumber to run with JUnit:

```java
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    glue = "com.arav.minorproject",
    plugin = {"pretty"}
)
public class CucumberTestRunner {
}
```

## Test Coverage

### Covered Components

The test suite covers the following components:

**BillCalculator Class** (100% method coverage)
- `calculateBillAmount(int, int)` - Fully tested with positive, zero, and negative inputs
- `isValidUnitPrice(int)` - Fully tested with boundary values
- `calculateBillAmountFromStrings(String, String)` - Fully tested with valid and invalid inputs

**InputValidator Class** (100% method coverage)
- `validateUsername(String)` - Fully tested with all edge cases
- `validatePassword(String)` - Fully tested including space handling
- `validateAccountNumber(String)` - Fully tested with all edge cases
- `validateContactNumber(String)` - Fully tested with format validation
- `isBlank(String)` - Fully tested with null, empty, and whitespace
- `containsSpaces(String)` - Fully tested with various inputs

**DBValues Class** (Configuration verification)
- All static fields verified for expected values

### Not Covered by Automated Tests

The following components are not covered by the automated test suite:

**UI Classes**: All Swing UI classes (LoginPage, LandingPage, etc.) are not unit tested. These would require UI testing frameworks like AssertJ Swing or TestFX.

**Database Operations**: Actual database queries and operations are not tested. Integration tests with a test database would be needed.

**End-to-End Workflows**: Complete user workflows spanning multiple screens are not automated.

## CI/CD Integration

### GitHub Actions

The project includes a GitHub Actions workflow that runs tests automatically on every push and pull request.

Location: `.github/workflows/test.yml`

```yaml
name: Run Tests

on:
  push:
  pull_request:

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Run tests
        run: ./run_tests.sh
```

### Expected CI Output

When tests pass, you should see output similar to:

```
=== Electricity Bill System - Test Runner ===

Compiling source files...
Source compilation complete.
Compiling test files...
Test compilation complete.
Copying feature files...
Feature files copied.

=== Running JUnit Tests ===

JUnit version 4.13.2
...................
Time: 0.XXX

OK (18 tests)

=== Running Cucumber BDD Tests ===

Feature: Electricity Bill Calculation

  Scenario: Calculate basic bill amount
    Given the units consumed is 100
    And the unit price is 8
    When the bill is calculated
    Then the total amount should be 800

  Scenario: Reject negative units
    Given the units consumed is -10
    And the unit price is 8
    When the bill is calculated
    Then an IllegalArgumentException should be thrown

2 Scenarios (2 passed)
8 Steps (8 passed)

=== All tests completed ===
```

## Writing New Tests

### Adding JUnit Tests

1. Create a new test class in `source/test/com/arav/minorproject/`
2. Import JUnit and the class under test:
   ```java
   import org.junit.Test;
   import static org.junit.Assert.*;
   ```
3. Write test methods annotated with `@Test`
4. Add the test class to the `run_tests.sh` script

### Adding Cucumber Scenarios

1. Add new scenarios to existing `.feature` files or create new ones in `source/test/features/`
2. Implement step definitions in `BillCalculatorSteps.java` or create a new steps class
3. Ensure the glue package is specified in the runner

### Test Naming Conventions

Follow these naming conventions for consistency:

**JUnit Tests**: `test<MethodName>_<Scenario>`
- Example: `testCalculateBillAmount_NegativeUnits`

**Cucumber Scenarios**: Use descriptive business language
- Example: "Calculate basic bill amount"

## Troubleshooting Tests

### Common Issues

**Tests fail to compile**

Ensure all JAR files are present in `source/lib/` and the classpath is correctly set.

**Cucumber tests not found**

Verify that feature files are copied to `test-bin/features/` and the glue package matches the step definitions package.

**JUnit tests not running**

Check that test classes are compiled to `test-bin/` and the fully qualified class names are correct.

**"No tests found" error**

Ensure test methods are annotated with `@Test` and the test class is public.

### Debugging Tests

To get more verbose output from JUnit:
```bash
java -cp "test-bin:$CLASSPATH" org.junit.runner.JUnitCore \
    com.arav.minorproject.BillCalculatorTest 2>&1
```

To get more verbose output from Cucumber:
```bash
java -cp "test-bin:$CLASSPATH" io.cucumber.core.cli.Main \
    --glue com.arav.minorproject \
    --plugin pretty \
    --plugin html:target/cucumber-report.html \
    test-bin/features
```
