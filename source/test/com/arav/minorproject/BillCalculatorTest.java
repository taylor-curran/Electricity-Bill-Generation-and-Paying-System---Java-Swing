package com.arav.minorproject;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for BillCalculator class.
 * Tests the core bill calculation logic which is critical for correct billing.
 */
public class BillCalculatorTest {

    @Test
    public void testCalculateBillAmount_BasicCalculation() {
        // 100 units * 8 price + 50 service fee = 850
        assertEquals(850, BillCalculator.calculateBillAmount(100, 8));
    }

    @Test
    public void testCalculateBillAmount_ZeroUnits() {
        // 0 units * 8 price + 50 service fee = 50
        assertEquals(50, BillCalculator.calculateBillAmount(0, 8));
    }

    @Test
    public void testCalculateBillAmount_MinUnitPrice() {
        // 100 units * 6 price + 50 service fee = 650
        assertEquals(650, BillCalculator.calculateBillAmount(100, 6));
    }

    @Test
    public void testCalculateBillAmount_MaxUnitPrice() {
        // 100 units * 10 price + 50 service fee = 1050
        assertEquals(1050, BillCalculator.calculateBillAmount(100, 10));
    }

    @Test
    public void testCalculateBillAmount_LargeUnits() {
        // 10000 units * 8 price + 50 service fee = 80050
        assertEquals(80050, BillCalculator.calculateBillAmount(10000, 8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateBillAmount_NegativeUnits() {
        BillCalculator.calculateBillAmount(-10, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateBillAmount_NegativePrice() {
        BillCalculator.calculateBillAmount(100, -5);
    }

    @Test
    public void testIsValidUnitPrice_ValidMin() {
        assertTrue(BillCalculator.isValidUnitPrice(6));
    }

    @Test
    public void testIsValidUnitPrice_ValidMax() {
        assertTrue(BillCalculator.isValidUnitPrice(10));
    }

    @Test
    public void testIsValidUnitPrice_ValidMiddle() {
        assertTrue(BillCalculator.isValidUnitPrice(8));
    }

    @Test
    public void testIsValidUnitPrice_TooLow() {
        assertFalse(BillCalculator.isValidUnitPrice(5));
    }

    @Test
    public void testIsValidUnitPrice_TooHigh() {
        assertFalse(BillCalculator.isValidUnitPrice(11));
    }

    @Test
    public void testIsValidUnitPrice_Negative() {
        assertFalse(BillCalculator.isValidUnitPrice(-1));
    }

    @Test
    public void testCalculateBillAmountFromStrings_Basic() {
        // 100 units * 8 price + 50 service fee = 850
        assertEquals("850", BillCalculator.calculateBillAmountFromStrings("100", "8"));
    }

    @Test
    public void testCalculateBillAmountFromStrings_Zero() {
        // 0 units * 8 price + 50 service fee = 50
        assertEquals("50", BillCalculator.calculateBillAmountFromStrings("0", "8"));
    }

    @Test(expected = NumberFormatException.class)
    public void testCalculateBillAmountFromStrings_InvalidUnits() {
        BillCalculator.calculateBillAmountFromStrings("abc", "8");
    }

    @Test(expected = NumberFormatException.class)
    public void testCalculateBillAmountFromStrings_InvalidPrice() {
        BillCalculator.calculateBillAmountFromStrings("100", "xyz");
    }

    @Test(expected = NumberFormatException.class)
    public void testCalculateBillAmountFromStrings_EmptyUnits() {
        BillCalculator.calculateBillAmountFromStrings("", "8");
    }
}
