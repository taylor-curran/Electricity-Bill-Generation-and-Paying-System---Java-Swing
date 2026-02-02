# Architecture Documentation

This document describes the system architecture of the Electricity Bill Generation and Paying System, including the database schema, class relationships, authentication flow, and UI navigation structure.

## System Overview

The application follows a traditional desktop application architecture with a Java Swing frontend connected directly to a MySQL database via JDBC. There is no separate backend server; the application runs entirely on the client machine and communicates directly with the database.

```
┌─────────────────────────────────────────────────────────────┐
│                    Desktop Application                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │  UI Layer   │  │  Business   │  │   Data Access       │  │
│  │ (Java Swing)│──│   Logic     │──│   (JDBC)            │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
                              │ JDBC Connection
                              ▼
                    ┌─────────────────┐
                    │  MySQL Database │
                    │  (localhost)    │
                    └─────────────────┘
```

## Database Schema

The application uses a MySQL database named `swing_electricity_bill` containing four tables.

### Entity Relationship Diagram

```
┌─────────────────┐         ┌─────────────────────┐
│     users       │         │   customerdetails   │
├─────────────────┤         ├─────────────────────┤
│ id (PK)         │         │ id                  │
│ uname           │         │ acno                │◄──────┐
│ name            │         │ custname            │       │
│ upass           │         │ custContact         │       │
│ confirmed       │         │ custaddr            │       │
└─────────────────┘         │ custcity            │       │
                            └─────────────────────┘       │
                                                          │
┌─────────────────────┐     ┌─────────────────────┐       │
│  merchantfeedback   │     │    billdetails      │       │
├─────────────────────┤     ├─────────────────────┤       │
│ id (PK)             │     │ id (PK)             │       │
│ uname               │     │ acno ───────────────┼───────┘
│ email               │     │ billDate            │
│ msg                 │     │ paid                │
└─────────────────────┘     │ billAmt             │
                            │ custname            │
                            │ custaddr            │
                            │ custcity            │
                            │ custcontact         │
                            │ unitconsumed        │
                            │ unitprice           │
                            │ xyz                 │
                            └─────────────────────┘
```

### Table Descriptions

#### users

Stores merchant account information for authentication and authorization.

| Column    | Type         | Description                                      |
|-----------|--------------|--------------------------------------------------|
| id        | INT(100)     | Primary key, auto-increment                      |
| uname     | VARCHAR(1000)| Username for login                               |
| name      | VARCHAR(1000)| Full name of the merchant                        |
| upass     | VARCHAR(1000)| Password (stored in plain text)                  |
| confirmed | INT(2)       | Verification status (0=pending, 1=verified)      |

#### customerdetails

Stores customer registration information.

| Column      | Type          | Description                          |
|-------------|---------------|--------------------------------------|
| id          | INT(100)      | Record identifier                    |
| acno        | VARCHAR(1000) | Account number (unique identifier)   |
| custname    | VARCHAR(10000)| Customer full name                   |
| custContact | VARCHAR(1000) | Contact phone number                 |
| custaddr    | VARCHAR(1000) | Street address                       |
| custcity    | VARCHAR(10000)| City or district                     |

#### billdetails

Stores generated electricity bills with payment status.

| Column       | Type          | Description                              |
|--------------|---------------|------------------------------------------|
| id           | INT(100)      | Primary key, auto-increment              |
| acno         | VARCHAR(1000) | Customer account number (foreign key)    |
| billDate     | VARCHAR(1000) | Billing cycle (month and year)           |
| paid         | INT(2)        | Payment status (0=unpaid, 1=paid)        |
| billAmt      | VARCHAR(1000) | Total bill amount                        |
| custname     | VARCHAR(1000) | Customer name (denormalized)             |
| custaddr     | VARCHAR(10000)| Customer address (denormalized)          |
| custcity     | VARCHAR(1000) | Customer city (denormalized)             |
| custcontact  | VARCHAR(1000) | Customer contact (denormalized)          |
| unitconsumed | VARCHAR(1000) | Number of units consumed                 |
| unitprice    | VARCHAR(1000) | Price per unit                           |
| xyz          | VARCHAR(1000) | Reserved field                           |

#### merchantfeedback

Stores feedback messages submitted by merchants.

| Column | Type          | Description                    |
|--------|---------------|--------------------------------|
| id     | INT(100)      | Primary key, auto-increment    |
| uname  | VARCHAR(1000) | Merchant username              |
| email  | VARCHAR(1000) | Merchant email address         |
| msg    | TEXT          | Feedback message content       |

### Database Design Notes

The schema uses denormalization in the `billdetails` table, storing customer information directly rather than just referencing the `customerdetails` table. This design choice preserves historical bill data even if customer details change, but increases storage requirements and potential data inconsistency.

All tables use the MyISAM storage engine with latin1 character set. For production use, consider migrating to InnoDB for better transaction support and data integrity.

## Class Architecture

### Package Structure

All application classes reside in the `com.arav.minorproject` package under `source/src/`.

### Class Categories

The application classes can be grouped into the following categories:

**Entry Points:**
- `EntryPage.java` - Main entry point, displays splash screen and launches StartPage
- `StartPage.java` - Main menu with login options

**Authentication:**
- `LoginPage.java` - Merchant login form
- `SignUpPage.java` - Merchant registration form
- `AdminLogin.java` - Administrator login form

**Merchant Features:**
- `LandingPage.java` - Merchant dashboard after login
- `BillAddNewCustomer.java` - Customer registration form
- `BillGenerateNewBill.java` - Bill generation form
- `BillPayBill.java` - Payment processing form
- `BillViewOldBillOptions.java` - Bill history menu
- `BillViewPaidBills.java` - View paid bills
- `BillViewUnpaidBills.java` - View unpaid bills
- `MerchantFeedback.java` - Feedback submission form
- `ViewAllCustomer.java` - Customer list view

**Admin Features:**
- `AdminLanding.java` - Administrator dashboard
- `AdminVerifyMerchant.java` - Merchant verification
- `ViewAllMerchant.java` - Merchant list view
- `ViewFeedbacks.java` - Feedback review

**Configuration:**
- `SetDBValues.java` - Database configuration form
- `DBValues.java` - Database connection constants

**Utilities:**
- `BillCalculator.java` - Bill calculation logic
- `InputValidator.java` - Input validation logic
- `Splash.java` - Splash screen display
- `AboutDeveloper.java` - Developer information

### Class Relationship Diagram

```
                         ┌──────────────┐
                         │  EntryPage   │
                         │   (main)     │
                         └──────┬───────┘
                                │
                         ┌──────▼───────┐
                         │   Splash     │
                         └──────┬───────┘
                                │
                         ┌──────▼───────┐
                         │  StartPage   │
                         └──────┬───────┘
              ┌─────────────────┼─────────────────┐
              │                 │                 │
       ┌──────▼───────┐  ┌──────▼───────┐  ┌──────▼───────┐
       │  LoginPage   │  │  AdminLogin  │  │  SignUpPage  │
       └──────┬───────┘  └──────┬───────┘  └──────────────┘
              │                 │
       ┌──────▼───────┐  ┌──────▼───────┐
       │ LandingPage  │  │ AdminLanding │
       └──────┬───────┘  └──────┬───────┘
              │                 │
    ┌─────────┼─────────┐      │
    │         │         │      ├── AdminVerifyMerchant
    │         │         │      ├── ViewAllMerchant
    │         │         │      ├── ViewAllCustomer
    │         │         │      └── ViewFeedbacks
    │         │         │
    │         │         └── BillAddNewCustomer
    │         │
    │         ├── BillGenerateNewBill ──► BillCalculator
    │         │
    │         ├── BillPayBill
    │         │
    │         └── BillViewOldBillOptions
    │                    │
    │              ┌─────┴─────┐
    │              │           │
    │     BillViewPaidBills  BillViewUnpaidBills
    │
    └── MerchantFeedback
```

### Utility Classes

#### BillCalculator

A stateless utility class for bill calculations, extracted from UI code to enable unit testing.

```java
public class BillCalculator {
    // Core calculation: units × price
    public static int calculateBillAmount(int unitsConsumed, int unitPrice)
    
    // Validates unit price is within range [6, 10]
    public static boolean isValidUnitPrice(int unitPrice)
    
    // String adapter for UI spinner values
    public static String calculateBillAmountFromStrings(String unitsConsumedStr, String unitPriceStr)
}
```

#### InputValidator

Centralized validation logic for user inputs, returning null for valid input or an error message string for invalid input.

```java
public class InputValidator {
    // Username: non-blank, no spaces
    public static String validateUsername(String username)
    
    // Password: non-blank only
    public static String validatePassword(String password)
    
    // Account number: non-blank, no spaces
    public static String validateAccountNumber(String accountNumber)
    
    // Contact: non-blank, digits only, minimum 10 digits
    public static String validateContactNumber(String contactNumber)
    
    // Helper methods
    public static boolean isBlank(String value)
    public static boolean containsSpaces(String value)
}
```

#### DBValues

Static configuration class holding database connection parameters.

```java
public class DBValues {
    static String dbname = "swing_electricity_bill";
    static String dbpass = "";
    static String dbhost = "localhost";
    static String dbuname = "root";
    static String dbport = "3306";
}
```

## Authentication Flow

The application implements different authentication mechanisms for each user type.

### Merchant Authentication

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  StartPage  │────►│  LoginPage  │────►│  Database   │────►│ LandingPage │
└─────────────┘     └──────┬──────┘     │   Query     │     └─────────────┘
                           │            └─────────────┘
                           │
                    Validates:
                    - Username not blank
                    - No spaces in username
                    - Password not blank
                           │
                           ▼
                    SQL Query:
                    SELECT * FROM users
                    WHERE uname='...'
                    AND upass='...'
                    AND confirmed='1'
```

Merchant authentication requires:
1. Valid username format (non-blank, no spaces)
2. Non-blank password
3. Matching credentials in the `users` table
4. Account verification status (`confirmed = 1`)

### Administrator Authentication

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  StartPage  │────►│ AdminLogin  │────►│  Hardcoded  │────►│AdminLanding │
└─────────────┘     └──────┬──────┘     │   Check     │     └─────────────┘
                           │            └─────────────┘
                           │
                    Validates:
                    - Username not blank
                    - No spaces in username
                    - Password not blank
                           │
                           ▼
                    Hardcoded credentials
                    (see AdminLogin.java)
```

Administrator authentication uses hardcoded credentials checked directly in the code, not against the database.

### Super User Authentication

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  StartPage  │────►│  Password   │────►│  Hardcoded  │────►│ SetDBValues │
│  Configure  │     │   Dialog    │     │   Check     │     └─────────────┘
└─────────────┘     └─────────────┘     └─────────────┘
                           │
                           ▼
                    Hardcoded password
                    (see StartPage.java)
```

Super user access is controlled by a single hardcoded password entered via a dialog prompt.

## UI Navigation Map

The following diagram shows the navigation paths between screens.

```
                              ┌─────────────────┐
                              │    EntryPage    │
                              │   (Splash 9s)   │
                              └────────┬────────┘
                                       │
                              ┌────────▼────────┐
                              │    StartPage    │
                              │  (Main Menu)    │
                              └────────┬────────┘
           ┌───────────────────────────┼───────────────────────────┐
           │                           │                           │
    ┌──────▼──────┐            ┌───────▼───────┐           ┌───────▼───────┐
    │  LoginPage  │            │  AdminLogin   │           │  SignUpPage   │
    │ (Merchant)  │            │               │           │               │
    └──────┬──────┘            └───────┬───────┘           └───────────────┘
           │                           │                           │
           │ Success                   │ Success                   │ Success
           │                           │                           │
    ┌──────▼──────┐            ┌───────▼───────┐                   │
    │ LandingPage │            │ AdminLanding  │                   │
    │ (Dashboard) │            │  (Dashboard)  │                   │
    └──────┬──────┘            └───────┬───────┘                   │
           │                           │                           │
    ┌──────┴──────────────┐    ┌───────┴───────────────┐           │
    │                     │    │                       │           │
    ▼                     ▼    ▼                       ▼           │
┌────────────┐    ┌────────────┐    ┌────────────┐    ┌────────────┐
│ AddCustomer│    │GenerateBill│    │VerifyMerch │    │ViewMerchant│
└────────────┘    └────────────┘    └────────────┘    └────────────┘
                                                                   
┌────────────┐    ┌────────────┐    ┌────────────┐    ┌────────────┐
│  PayBill   │    │ViewOldBills│    │ViewCustomer│    │ViewFeedback│
└────────────┘    └─────┬──────┘    └────────────┘    └────────────┘
                        │
              ┌─────────┴─────────┐
              │                   │
       ┌──────▼──────┐     ┌──────▼──────┐
       │ ViewPaid    │     │ ViewUnpaid  │
       │   Bills     │     │   Bills     │
       └─────────────┘     └─────────────┘
```

### Navigation Patterns

Most screens follow a consistent navigation pattern:

1. **Go Back**: Returns to the previous screen in the navigation hierarchy
2. **Go to Home**: Returns directly to the Landing Page (merchant) or Admin Landing (admin)
3. **Cancel**: Cancels the current operation and returns to the previous screen

When operations complete successfully (e.g., adding a customer, generating a bill), a confirmation dialog offers options to continue with another operation or return to the home screen.

## Data Flow

### Bill Generation Flow

```
┌─────────────────┐
│ BillGenerateNew │
│     Bill        │
└────────┬────────┘
         │
         │ 1. Enter Account Number
         │
         ▼
┌─────────────────┐     ┌─────────────────┐
│  Get Details    │────►│ customerdetails │
│    Button       │     │     table       │
└────────┬────────┘     └─────────────────┘
         │
         │ 2. Customer info displayed
         │
         ▼
┌─────────────────┐
│ Enter Units,    │
│ Price, Cycle    │
└────────┬────────┘
         │
         │ 3. Calculate bill amount
         │    (units × price)
         │
         ▼
┌─────────────────┐     ┌─────────────────┐
│  Submit Bill    │────►│  billdetails    │
│                 │     │     table       │
└─────────────────┘     └─────────────────┘
```

### Payment Processing Flow

```
┌─────────────────┐
│   BillPayBill   │
└────────┬────────┘
         │
         │ 1. Enter Account Number
         │
         ▼
┌─────────────────┐     ┌─────────────────┐
│  Get Amount     │────►│  billdetails    │
│    Button       │     │ WHERE paid='0'  │
└────────┬────────┘     └─────────────────┘
         │
         │ 2. Unpaid bill info displayed
         │
         ▼
┌─────────────────┐     ┌─────────────────┐
│   Pay Now       │────►│  billdetails    │
│    Button       │     │ SET paid='1'    │
└─────────────────┘     └─────────────────┘
```

## Security Considerations

The current implementation has several security limitations that should be addressed for production use:

**Password Storage**: Passwords are stored in plain text in the database. Implement password hashing using bcrypt or similar algorithms.

**SQL Injection**: The application constructs SQL queries using string concatenation, making it vulnerable to SQL injection attacks. Use prepared statements instead.

**Hardcoded Credentials**: Admin and super user credentials are hardcoded in the source code. Move these to secure configuration or implement proper authentication.

**No Session Management**: The application relies on static variables to track logged-in users, which is not secure for multi-user scenarios.

**No Encryption**: Database connections are not encrypted. Enable SSL/TLS for database connections in production.

## Performance Considerations

**Connection Management**: Each database operation creates a new connection. Implement connection pooling for better performance.

**Query Optimization**: Some queries retrieve all columns when only specific fields are needed. Optimize queries to select only required columns.

**Indexing**: The database schema doesn't define indexes beyond primary keys. Add indexes on frequently queried columns like `acno` and `uname`.

## Extensibility

The architecture supports extension in several ways:

**Adding New Features**: Create new JFrame classes following the existing pattern and add navigation from appropriate screens.

**Database Changes**: Modify the schema in `swing_electricity_bill.sql` and update corresponding Java classes.

**Business Logic**: Add new utility classes following the `BillCalculator` and `InputValidator` patterns for testable business logic.

**UI Customization**: The Swing-based UI can be modified using Eclipse WindowBuilder or by editing the Java code directly.
