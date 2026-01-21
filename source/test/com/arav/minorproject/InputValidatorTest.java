package com.arav.minorproject;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for InputValidator class.
 * Tests input validation logic which is critical for data integrity and security.
 */
public class InputValidatorTest {

    // Username validation tests
    @Test
    public void testValidateUsername_Valid() {
        assertNull(InputValidator.validateUsername("validuser"));
    }

    @Test
    public void testValidateUsername_Empty() {
        assertEquals("Username cannot be blank", InputValidator.validateUsername(""));
    }

    @Test
    public void testValidateUsername_Null() {
        assertEquals("Username cannot be blank", InputValidator.validateUsername(null));
    }

    @Test
    public void testValidateUsername_WithSpaces() {
        assertEquals("Username cannot have blank spaces", InputValidator.validateUsername("user name"));
    }

    @Test
    public void testValidateUsername_LeadingSpace() {
        assertEquals("Username cannot have blank spaces", InputValidator.validateUsername(" username"));
    }

    @Test
    public void testValidateUsername_TrailingSpace() {
        assertEquals("Username cannot have blank spaces", InputValidator.validateUsername("username "));
    }

    // Password validation tests
    @Test
    public void testValidatePassword_Valid() {
        assertNull(InputValidator.validatePassword("password123"));
    }

    @Test
    public void testValidatePassword_Empty() {
        assertEquals("Password cannot be blank", InputValidator.validatePassword(""));
    }

    @Test
    public void testValidatePassword_Null() {
        assertEquals("Password cannot be blank", InputValidator.validatePassword(null));
    }

    @Test
    public void testValidatePassword_WithSpaces() {
        // Passwords can have spaces (unlike usernames)
        assertNull(InputValidator.validatePassword("pass word"));
    }

    // Account number validation tests
    @Test
    public void testValidateAccountNumber_Valid() {
        assertNull(InputValidator.validateAccountNumber("ACC123456"));
    }

    @Test
    public void testValidateAccountNumber_Empty() {
        assertEquals("Please enter Account Number", InputValidator.validateAccountNumber(""));
    }

    @Test
    public void testValidateAccountNumber_Null() {
        assertEquals("Please enter Account Number", InputValidator.validateAccountNumber(null));
    }

    @Test
    public void testValidateAccountNumber_WithSpaces() {
        assertEquals("Account Number cannot have spaces", InputValidator.validateAccountNumber("ACC 123"));
    }

    // isBlank tests
    @Test
    public void testIsBlank_Empty() {
        assertTrue(InputValidator.isBlank(""));
    }

    @Test
    public void testIsBlank_Null() {
        assertTrue(InputValidator.isBlank(null));
    }

    @Test
    public void testIsBlank_Whitespace() {
        assertTrue(InputValidator.isBlank("   "));
    }

    @Test
    public void testIsBlank_NotBlank() {
        assertFalse(InputValidator.isBlank("text"));
    }

    // containsSpaces tests
    @Test
    public void testContainsSpaces_WithSpace() {
        assertTrue(InputValidator.containsSpaces("has space"));
    }

    @Test
    public void testContainsSpaces_NoSpace() {
        assertFalse(InputValidator.containsSpaces("nospace"));
    }

    @Test
    public void testContainsSpaces_Null() {
        assertFalse(InputValidator.containsSpaces(null));
    }

    // Contact number validation tests
    @Test
    public void testValidateContactNumber_Valid() {
        assertNull(InputValidator.validateContactNumber("9876543210"));
    }

    @Test
    public void testValidateContactNumber_Empty() {
        assertEquals("Contact number cannot be blank", InputValidator.validateContactNumber(""));
    }

    @Test
    public void testValidateContactNumber_Null() {
        assertEquals("Contact number cannot be blank", InputValidator.validateContactNumber(null));
    }

    @Test
    public void testValidateContactNumber_WithLetters() {
        assertEquals("Contact number must contain only digits", InputValidator.validateContactNumber("98765abc10"));
    }

    @Test
    public void testValidateContactNumber_TooShort() {
        assertEquals("Contact number must be at least 10 digits", InputValidator.validateContactNumber("12345"));
    }

    @Test
    public void testValidateContactNumber_ExactlyTenDigits() {
        assertNull(InputValidator.validateContactNumber("1234567890"));
    }

    @Test
    public void testValidateContactNumber_MoreThanTenDigits() {
        assertNull(InputValidator.validateContactNumber("12345678901234"));
    }
}
