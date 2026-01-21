package com.arav.minorproject;

/**
 * Utility class for input validation.
 * Extracted from UI code to enable unit testing and centralize validation logic.
 */
public class InputValidator {

    /**
     * Validates a username for login/signup.
     * Username cannot be blank or contain spaces.
     * 
     * @param username the username to validate
     * @return null if valid, error message if invalid
     */
    public static String validateUsername(String username) {
        if (isBlank(username)) {
            return "Username cannot be blank";
        }
        if (username.contains(" ")) {
            return "Username cannot have blank spaces";
        }
        return null;
    }

    /**
     * Validates a password for login/signup.
     * Password cannot be blank.
     * 
     * @param password the password to validate
     * @return null if valid, error message if invalid
     */
    public static String validatePassword(String password) {
        if (isBlank(password)) {
            return "Password cannot be blank";
        }
        return null;
    }

    /**
     * Validates an account number.
     * Account number cannot be blank or contain spaces.
     * 
     * @param accountNumber the account number to validate
     * @return null if valid, error message if invalid
     */
    public static String validateAccountNumber(String accountNumber) {
        if (isBlank(accountNumber)) {
            return "Please enter Account Number";
        }
        if (accountNumber.contains(" ")) {
            return "Account Number cannot have spaces";
        }
        return null;
    }

    /**
     * Checks if a string is blank (null or empty).
     * 
     * @param value the string to check
     * @return true if blank, false otherwise
     */
    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Checks if a string contains spaces.
     * 
     * @param value the string to check
     * @return true if contains spaces, false otherwise
     */
    public static boolean containsSpaces(String value) {
        return value != null && value.contains(" ");
    }

    /**
     * Validates contact number format (basic validation).
     * 
     * @param contactNumber the contact number to validate
     * @return null if valid, error message if invalid
     */
    public static String validateContactNumber(String contactNumber) {
        if (isBlank(contactNumber)) {
            return "Contact number cannot be blank";
        }
        if (!contactNumber.matches("\\d+")) {
            return "Contact number must contain only digits";
        }
        if (contactNumber.length() < 10) {
            return "Contact number must be at least 10 digits";
        }
        return null;
    }
}
