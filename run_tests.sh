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

# Compile test files
echo "Compiling test files..."
javac -cp "$BIN_DIR:$LIB_DIR/junit-4.13.2.jar:$LIB_DIR/hamcrest-core-1.3.jar" \
    -d "$TEST_BIN_DIR" \
    "$TEST_DIR"/com/arav/minorproject/*Test.java
echo "Test compilation complete."

# Run tests
echo ""
echo "=== Running Tests ==="
echo ""

java -cp "$TEST_BIN_DIR:$BIN_DIR:$LIB_DIR/junit-4.13.2.jar:$LIB_DIR/hamcrest-core-1.3.jar" \
    org.junit.runner.JUnitCore \
    com.arav.minorproject.BillCalculatorTest \
    com.arav.minorproject.InputValidatorTest \
    com.arav.minorproject.DBValuesTest

echo ""
echo "=== All tests completed ==="
