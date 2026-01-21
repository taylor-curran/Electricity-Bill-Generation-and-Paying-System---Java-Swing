#!/bin/bash

# Test runner script for Electricity Bill Generation System
# This script compiles and runs all JUnit tests

set -e

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
SOURCE_DIR="$PROJECT_DIR/source"
SRC_DIR="$SOURCE_DIR/src"
TEST_DIR="$SOURCE_DIR/test"
LIB_DIR="$SOURCE_DIR/lib"
BIN_DIR="$SOURCE_DIR/bin"
TEST_BIN_DIR="$SOURCE_DIR/test-bin"

echo "=== Electricity Bill System - Test Runner ==="
echo ""

# Create output directories
mkdir -p "$BIN_DIR"
mkdir -p "$TEST_BIN_DIR"

# Check for JUnit
if [ ! -f "$LIB_DIR/junit-4.13.2.jar" ]; then
    echo "Error: JUnit not found. Please download junit-4.13.2.jar to $LIB_DIR"
    exit 1
fi

# Check for Cucumber
if [ ! -f "$LIB_DIR/cucumber-java-6.10.4.jar" ]; then
    echo "Error: Cucumber not found. Please download cucumber-java-6.10.4.jar to $LIB_DIR"
    exit 1
fi

# Compile source files
echo "Compiling source files..."
javac -d "$BIN_DIR" "$SRC_DIR"/com/arav/minorproject/*.java 2>/dev/null || {
    # If compilation fails with all files, try just the utility classes
    javac -d "$BIN_DIR" \
        "$SRC_DIR/com/arav/minorproject/BillCalculator.java" \
        "$SRC_DIR/com/arav/minorproject/InputValidator.java" \
        "$SRC_DIR/com/arav/minorproject/DBValues.java"
}
echo "Source compilation complete."

# Define classpath with all JARs (including Cucumber and its dependencies)
CLASSPATH="$BIN_DIR:$LIB_DIR/junit-4.13.2.jar:$LIB_DIR/hamcrest-core-1.3.jar"
CLASSPATH="$CLASSPATH:$LIB_DIR/cucumber-java-6.10.4.jar:$LIB_DIR/cucumber-junit-6.10.4.jar:$LIB_DIR/cucumber-core-6.10.4.jar"
CLASSPATH="$CLASSPATH:$LIB_DIR/cucumber-plugin-6.10.4.jar:$LIB_DIR/cucumber-expressions-10.3.0.jar"
CLASSPATH="$CLASSPATH:$LIB_DIR/gherkin-15.0.2.jar:$LIB_DIR/messages-15.0.0.jar"
CLASSPATH="$CLASSPATH:$LIB_DIR/cucumber-gherkin-15.0.2.jar:$LIB_DIR/cucumber-gherkin-messages-15.0.2.jar"
CLASSPATH="$CLASSPATH:$LIB_DIR/datatable-3.5.0.jar:$LIB_DIR/tag-expressions-3.0.1.jar:$LIB_DIR/cucumber-docstring-6.10.4.jar"
CLASSPATH="$CLASSPATH:$LIB_DIR/html-formatter-13.0.0.jar:$LIB_DIR/create-meta-4.0.0.jar"
CLASSPATH="$CLASSPATH:$LIB_DIR/ci-environment-8.1.0.jar:$LIB_DIR/apiguardian-api-1.1.0.jar"

# Compile test files
echo "Compiling test files..."
javac -cp "$CLASSPATH" \
    -d "$TEST_BIN_DIR" \
    "$TEST_DIR"/com/arav/minorproject/*Test.java \
    "$TEST_DIR"/com/arav/minorproject/BillCalculatorSteps.java \
    "$TEST_DIR"/com/arav/minorproject/LatePaymentFeeSteps.java \
    "$TEST_DIR"/com/arav/minorproject/CucumberTestRunner.java
echo "Test compilation complete."

# Copy feature files to test-bin for classpath access
echo "Copying feature files..."
mkdir -p "$TEST_BIN_DIR/features"
cp "$TEST_DIR"/features/*.feature "$TEST_BIN_DIR/features/"
echo "Feature files copied."

# Run JUnit tests
echo ""
echo "=== Running JUnit Tests ==="
echo ""

java -cp "$TEST_BIN_DIR:$CLASSPATH" \
    org.junit.runner.JUnitCore \
    com.arav.minorproject.BillCalculatorTest \
    com.arav.minorproject.InputValidatorTest \
    com.arav.minorproject.DBValuesTest

echo ""
echo "=== Running Cucumber BDD Tests ==="
echo ""

java -cp "$TEST_BIN_DIR:$CLASSPATH" \
    io.cucumber.core.cli.Main \
    --glue com.arav.minorproject \
    --plugin pretty \
    "$TEST_BIN_DIR/features"

echo ""
echo "=== All tests completed ==="
