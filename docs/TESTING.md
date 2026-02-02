# Testing Documentation

This document describes the testing infrastructure, test execution procedures, and test coverage for the Electricity Bill Generation and Paying System.

## Testing Overview

The project employs a dual testing strategy combining traditional unit tests with behavior-driven development (BDD) tests. This approach ensures both technical correctness through unit tests and business requirement validation through BDD scenarios.

**Unit Testing Framework**: JUnit 4.13.2 with Hamcrest matchers for expressive assertions

**BDD Testing Framework**: Cucumber 6.10.4 with Gherkin feature files for human-readable test scenarios

## Test Structure

Tests are located in the `source/test/` directory with the following organization:

```
source/test/
├── com/arav/minorproject/
│   ├── BillCalculatorTest.java      # Unit tests for bill calculations
│   ├── InputValidatorTest.java      # Unit tests for input validation
│   ├── DBValuesTest.java            # Unit tests for database configuration
│   ├── BillCalculatorSteps.java     # Cucumber step definitions
│   └── CucumberTestRunner.java      # Cucumber test runner
└── features/
    └── bill_calculation.feature     # BDD feature file
```

## Running Tests

### Using the Test Runner Script

The simplest way to run all tests is using the provided shell script:

```bash
./run_tests.sh
```

This script performs the following steps:
1. Verifies required dependencies (JUnit, Cucumber JARs) are present
2. Compiles source files to `source/bin/`
3. Compiles test files to `source/test-bin/`
4. Copies feature files to the test output directory
5. Runs JUnit tests
6. Runs Cucumber BDD tests

### Running Tests Manually

If you prefer to run tests manually or need more control over the process:

**Compile source files:**
```bash
cd source
mkdir -p bin
javac -d bin src/com/arav/minorproject/BillCalculator.java \
    src/com/arav/minorproject/InputValidator.java \
    src/com/arav/minorproject/DBValues.java
```

**Compile test files:**
```bash
mkdir -p test-bin
javac -cp "bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:lib/cucumber-java-6.10.4.jar:lib/cucumber-junit-6.10.4.jar" \
    -d test-bin \
    test/com/arav/minorproject/*Test.java \
    test/com/arav/minorproject/BillCalculatorSteps.java \
    test/com/arav/minorproject/CucumberTestRunner.java
```

**Run JUnit tests:**
```bash
java -cp "test-bin:bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" \
    org.junit.runner.JUnitCore \
    com.arav.minorproject.BillCalculatorTest \
    com.arav.minorproject.InputValidatorTest \
    com.arav.minorproject.DBValuesTest
```

**Run Cucumber tests:**
```bash
cp -r test/features test-bin/
java -cp "test-bin:bin:lib/*" \
    io.cucumber.core.cli.Main \
    --glue com.arav.minorproject \
    --plugin pretty \
    test-bin/features
```

### Running Tests in an IDE

**Eclipse:**
1. Right-click on a test class (e.g., `BillCalculatorTest.java`)
2. Select Run As > JUnit Test
3. View results in the JUnit view

**IntelliJ IDEA:**
1. Right-click on the test directory or a specific test class
2. Select Run 'Tests in...'
3. View results in the Run tool window

## Test Coverage

### BillCalculatorTest

Tests the core bill calculation logic with 18 test cases covering:

| Test Case | Description |
|-----------|-------------|
| `testCalculateBillAmount_BasicCalculation` | Verifies basic multiplication (100 units × 8 = 800) |
| `testCalculateBillAmount_ZeroUnits` | Handles zero units consumed |
| `testCalculateBillAmount_MinUnitPrice` | Tests minimum valid unit price (6) |
| `testCalculateBillAmount_MaxUnitPrice` | Tests maximum valid unit price (10) |
| `testCalculateBillAmount_LargeUnits` | Handles large unit values (10000 units) |
| `testCalculateBillAmount_NegativeUnits` | Expects IllegalArgumentException for negative units |
| `testCalculateBillAmount_NegativePrice` | Expects IllegalArgumentException for negative price |
| `testIsValidUnitPrice_ValidMin` | Validates minimum price (6) is accepted |
| `testIsValidUnitPrice_ValidMax` | Validates maximum price (10) is accepted |
| `testIsValidUnitPrice_ValidMiddle` | Validates middle price (8) is accepted |
| `testIsValidUnitPrice_TooLow` | Rejects price below minimum (5) |
| `testIsValidUnitPrice_TooHigh` | Rejects price above maximum (11) |
| `testIsValidUnitPrice_Negative` | Rejects negative prices |
| `testCalculateBillAmountFromStrings_Basic` | Tests string-based calculation |
| `testCalculateBillAmountFromStrings_Zero` | Handles zero as string input |
| `testCalculateBillAmountFromStrings_InvalidUnits` | Expects NumberFormatException for non-numeric units |
| `testCalculateBillAmountFromStrings_InvalidPrice` | Expects NumberFormatException for non-numeric price |
| `testCalculateBillAmountFromStrings_EmptyUnits` | Expects NumberFormatException for empty string |

### InputValidatorTest

Tests input validation logic with 28 test cases covering:

**Username Validation (6 tests):**
- Valid username acceptance
- Empty string rejection
- Null value rejection
- Space-containing username rejection
- Leading space rejection
- Trailing space rejection

**Password Validation (4 tests):**
- Valid password acceptance
- Empty string rejection
- Null value rejection
- Password with spaces acceptance (spaces allowed in passwords)

**Account Number Validation (4 tests):**
- Valid account number acceptance
- Empty string rejection
- Null value rejection
- Space-containing account number rejection

**Helper Method Tests (7 tests):**
- `isBlank()` with empty string, null, whitespace, and non-blank values
- `containsSpaces()` with space, no space, and null values

**Contact Number Validation (7 tests):**
- Valid 10-digit number acceptance
- Empty string rejection
- Null value rejection
- Non-digit character rejection
- Too-short number rejection
- Exactly 10 digits acceptance
- More than 10 digits acceptance

### DBValuesTest

Tests database configuration values with 7 test cases:

| Test Case | Description |
|-----------|-------------|
| `testDatabaseNameIsSet` | Verifies database name is "swing_electricity_bill" |
| `testDatabaseHostIsSet` | Verifies host is not null or empty |
| `testDatabasePortIsSet` | Verifies port is "3306" |
| `testDatabaseUsernameIsSet` | Verifies username is not null or empty |
| `testDatabasePasswordIsNotNull` | Verifies password field exists (can be empty) |
| `testDefaultHostIsLocalhost` | Verifies default host is "localhost" |
| `testDefaultUsernameIsRoot` | Verifies default username is "root" |

## BDD Tests (Cucumber)

### Feature File: bill_calculation.feature

The BDD tests use Gherkin syntax to describe business scenarios in human-readable format:

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

The `BillCalculatorSteps.java` class implements the Gherkin steps:

- `@Given("the units consumed is {int}")` - Sets the units consumed value
- `@Given("the unit price is {int}")` - Sets the unit price value
- `@When("the bill is calculated")` - Calls `BillCalculator.calculateBillAmount()`
- `@Then("the total amount should be {int}")` - Verifies the calculated amount
- `@Then("an IllegalArgumentException should be thrown")` - Verifies exception handling

## Test Dependencies

The following JAR files in `source/lib/` are required for testing:

**JUnit:**
- `junit-4.13.2.jar` - Core JUnit framework
- `hamcrest-core-1.3.jar` - Assertion matchers

**Cucumber:**
- `cucumber-java-6.10.4.jar` - Java step definitions
- `cucumber-junit-6.10.4.jar` - JUnit integration
- `cucumber-core-6.10.4.jar` - Core Cucumber engine
- `cucumber-plugin-6.10.4.jar` - Plugin support
- `cucumber-expressions-10.3.0.jar` - Expression parsing
- `gherkin-15.0.2.jar` - Gherkin parser
- `messages-15.0.0.jar` - Message protocol
- `cucumber-gherkin-15.0.2.jar` - Gherkin integration
- `cucumber-gherkin-messages-15.0.2.jar` - Gherkin messages
- `datatable-3.5.0.jar` - Data table support
- `tag-expressions-3.0.1.jar` - Tag expression parsing
- `cucumber-docstring-6.10.4.jar` - Docstring support
- `html-formatter-13.0.0.jar` - HTML report generation
- `create-meta-4.0.0.jar` - Metadata creation
- `ci-environment-8.1.0.jar` - CI environment detection
- `apiguardian-api-1.1.0.jar` - API guardian annotations

## Continuous Integration

The project includes a GitHub Actions workflow (`.github/workflows/test.yml`) that automatically runs tests on every push and pull request.

### CI Configuration

```yaml
name: Java CI with Tests

on:
  push:
  pull_request:

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Run tests
        run: ./run_tests.sh
```

### CI Behavior

- Tests run automatically on every push to any branch
- Tests run on all pull requests
- The build fails if any test fails
- Test output is visible in the GitHub Actions log

## Writing New Tests

### Adding Unit Tests

1. Create a new test class in `source/test/com/arav/minorproject/`
2. Import JUnit and the class under test:
   ```java
   import org.junit.Test;
   import static org.junit.Assert.*;
   ```
3. Write test methods annotated with `@Test`
4. Add the test class to the JUnit runner command in `run_tests.sh`

Example:
```java
@Test
public void testNewFeature() {
    // Arrange
    int input = 5;
    
    // Act
    int result = MyClass.myMethod(input);
    
    // Assert
    assertEquals(10, result);
}
```

### Adding BDD Scenarios

1. Add new scenarios to `source/test/features/bill_calculation.feature` or create a new feature file
2. Implement step definitions in a Steps class
3. Use descriptive Given/When/Then format

Example:
```gherkin
Scenario: Calculate bill with boundary unit price
  Given the units consumed is 50
  And the unit price is 6
  When the bill is calculated
  Then the total amount should be 300
```

## Test Best Practices

The test suite follows these best practices:

**Naming Convention**: Test methods use descriptive names following the pattern `test[MethodName]_[Scenario]` (e.g., `testCalculateBillAmount_NegativeUnits`).

**Single Responsibility**: Each test verifies one specific behavior or condition.

**Boundary Testing**: Tests cover edge cases like minimum values, maximum values, zero, and negative numbers.

**Exception Testing**: Tests verify that appropriate exceptions are thrown for invalid inputs using `@Test(expected = Exception.class)`.

**Independence**: Tests don't depend on each other and can run in any order.

**Readability**: BDD tests use human-readable Gherkin syntax that serves as documentation.

## Troubleshooting

### Tests Won't Compile

Ensure all required JARs are in `source/lib/` and the classpath is correctly configured. The `run_tests.sh` script handles this automatically.

### Tests Fail to Run

Check that:
- JDK is installed and `java`/`javac` are in your PATH
- The `run_tests.sh` script has execute permissions (`chmod +x run_tests.sh`)
- You're running from the repository root directory

### Cucumber Tests Not Found

Ensure feature files are copied to `test-bin/features/` before running Cucumber. The test script handles this, but manual runs need this step.

### CI Failures

Check the GitHub Actions log for detailed error messages. Common issues include:
- Missing dependencies
- Compilation errors in source code
- Test assertion failures

## Test Maintenance

When modifying the codebase:

1. **Run existing tests** before making changes to establish a baseline
2. **Update tests** if behavior intentionally changes
3. **Add new tests** for new functionality
4. **Don't modify tests** just to make them pass - fix the underlying code issue instead
5. **Keep BDD tests stable** - they represent expected business behavior
