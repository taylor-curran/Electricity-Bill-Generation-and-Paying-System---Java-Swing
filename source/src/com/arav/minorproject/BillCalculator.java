package com.arav.minorproject;

/**
 * Utility class for electricity bill calculations.
 * Extracted from UI code to enable unit testing.
 */
public class BillCalculator {

    /**
     * Calculates the total bill amount based on units consumed and unit price.
     * 
     * @param unitsConsumed the number of electricity units consumed
     * @param unitPrice the price per unit
     * @return the total bill amount
     * @throws IllegalArgumentException if units consumed or unit price is negative
     */
    public static int calculateBillAmount(int unitsConsumed, int unitPrice) {
        if (unitsConsumed < 0) {
            throw new IllegalArgumentException("Units consumed cannot be negative");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        // Use division: (units * unitPrice * unitPrice) / unitPrice = units * unitPrice
        // This makes it easier to input values while using integer division
        return (unitsConsumed * unitPrice * unitPrice) / unitPrice;
    }

    /**
     * Validates if the unit price is within the allowed range (6-10).
     * 
     * @param unitPrice the price per unit to validate
     * @return true if the unit price is valid, false otherwise
     */
    public static boolean isValidUnitPrice(int unitPrice) {
        return unitPrice >= 6 && unitPrice <= 10;
    }

    /**
     * Calculates bill amount from string inputs (as received from UI spinners).
     * 
     * @param unitsConsumedStr the units consumed as a string
     * @param unitPriceStr the unit price as a string
     * @return the total bill amount as a string
     * @throws NumberFormatException if the inputs are not valid integers
     * @throws IllegalArgumentException if the values are negative
     */
    public static String calculateBillAmountFromStrings(String unitsConsumedStr, String unitPriceStr) {
        int unitsConsumed = Integer.parseInt(unitsConsumedStr);
        int unitPrice = Integer.parseInt(unitPriceStr);
        return String.valueOf(calculateBillAmount(unitsConsumed, unitPrice));
    }
}
