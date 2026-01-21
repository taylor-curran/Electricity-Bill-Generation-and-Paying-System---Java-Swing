package com.arav.minorproject;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for DBValues class.
 * Tests the database configuration values to ensure they are properly set.
 */
public class DBValuesTest {

    @Test
    public void testDatabaseNameIsSet() {
        assertNotNull("Database name should not be null", DBValues.dbname);
        assertFalse("Database name should not be empty", DBValues.dbname.isEmpty());
        assertEquals("swing_electricity_bill", DBValues.dbname);
    }

    @Test
    public void testDatabaseHostIsSet() {
        assertNotNull("Database host should not be null", DBValues.dbhost);
        assertFalse("Database host should not be empty", DBValues.dbhost.isEmpty());
    }

    @Test
    public void testDatabasePortIsSet() {
        assertNotNull("Database port should not be null", DBValues.dbport);
        assertFalse("Database port should not be empty", DBValues.dbport.isEmpty());
        assertEquals("3306", DBValues.dbport);
    }

    @Test
    public void testDatabaseUsernameIsSet() {
        assertNotNull("Database username should not be null", DBValues.dbuname);
        assertFalse("Database username should not be empty", DBValues.dbuname.isEmpty());
    }

    @Test
    public void testDatabasePasswordIsNotNull() {
        assertNotNull("Database password should not be null", DBValues.dbpass);
    }

    @Test
    public void testDefaultHostIsLocalhost() {
        assertEquals("localhost", DBValues.dbhost);
    }

    @Test
    public void testDefaultUsernameIsRoot() {
        assertEquals("root", DBValues.dbuname);
    }
}
