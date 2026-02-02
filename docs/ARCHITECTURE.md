# Architecture Documentation

This document describes the system architecture of the Electricity Bill Generation and Paying System, including the database schema, class relationships, authentication flows, and UI navigation structure.

## System Overview

The application follows a traditional desktop application architecture with three main layers:

**Presentation Layer**: Java Swing UI components that handle user interaction and display. Each major feature has its own JFrame-based class.

**Business Logic Layer**: Utility classes that encapsulate core business rules for bill calculation and input validation. These classes are stateless and use static methods for easy testing and reuse.

**Data Access Layer**: Direct JDBC connections to MySQL database for data persistence. Database operations are embedded within the UI classes using the connection parameters from DBValues.

## Database Schema

The application uses a MySQL database named `swing_electricity_bill` with four tables.

### Entity Relationship Diagram

```
+------------------+       +--------------------+       +------------------+
|      users       |       |  customerdetails   |       |   billdetails    |
+------------------+       +--------------------+       +------------------+
| id (PK, AUTO)    |       | id                 |       | id (PK, AUTO)    |
| uname            |       | acno               |       | acno             |
| name             |       | custname           |       | billDate         |
| upass            |       | custContact        |       | paid             |
| confirmed        |       | custaddr           |       | billAmt          |
+------------------+       | custcity           |       | custname         |
                           +--------------------+       | custaddr         |
                                    |                   | custcity         |
                                    |                   | custcontact      |
                                    +------------------>| unitconsumed     |
                                    (acno reference)    | unitprice        |
                                                        | xyz              |
                                                        +------------------+

+--------------------+
| merchantfeedback   |
+--------------------+
| id (PK, AUTO)      |
| uname              |
| email              |
| msg                |
+--------------------+
```

### Table Definitions

#### users

This table stores merchant account information. Merchants must register and be verified by an administrator before they can access the system.

| Column | Type | Description |
|--------|------|-------------|
| id | INT(100) | Primary key, auto-increment |
| uname | VARCHAR(1000) | Username for login |
| name | VARCHAR(1000) | Full name of the merchant |
| upass | VARCHAR(1000) | Password (stored in plain text) |
| confirmed | INT(2) | Verification status: 0 = unverified, 1 = verified |

#### customerdetails

This table stores information about electricity customers who receive bills.

| Column | Type | Description |
|--------|------|-------------|
| id | INT(100) | Identifier (not auto-increment) |
| acno | VARCHAR(1000) | Account number (unique identifier for customers) |
| custname | VARCHAR(10000) | Customer's full name |
| custContact | VARCHAR(1000) | Contact phone number |
| custaddr | VARCHAR(1000) | Street address |
| custcity | VARCHAR(10000) | City or district |

#### billdetails

This table stores all generated bills and their payment status.

| Column | Type | Description |
|--------|------|-------------|
| id | INT(100) | Primary key, auto-increment |
| acno | VARCHAR(1000) | Customer account number (references customerdetails) |
| billDate | VARCHAR(1000) | Billing period (e.g., "January 2024") |
| paid | INT(2) | Payment status: 0 = unpaid, 1 = paid |
| billAmt | VARCHAR(1000) | Total bill amount |
| custname | VARCHAR(1000) | Customer name (denormalized) |
| custaddr | VARCHAR(10000) | Customer address (denormalized) |
| custcity | VARCHAR(1000) | Customer city (denormalized) |
| custcontact | VARCHAR(1000) | Customer contact (denormalized) |
| unitconsumed | VARCHAR(1000) | Number of electricity units consumed |
| unitprice | VARCHAR(1000) | Price per unit |
| xyz | VARCHAR(1000) | Reserved field |

#### merchantfeedback

This table stores feedback submitted by merchants to administrators.

| Column | Type | Description |
|--------|------|-------------|
| id | INT(100) | Primary key, auto-increment |
| uname | VARCHAR(1000) | Username of the merchant submitting feedback |
| email | VARCHAR(1000) | Email address of the merchant |
| msg | TEXT | Feedback message content |

### Database Design Notes

The database uses the MyISAM storage engine and latin1 character set. Some design decisions worth noting:

**Denormalization in billdetails**: Customer information is copied into the billdetails table when a bill is generated. This preserves the customer details at the time of billing, even if the customer record is later modified.

**No Foreign Keys**: The tables do not use foreign key constraints. Referential integrity is maintained at the application level.

**String Storage for Numeric Values**: Several numeric values (bill amount, units consumed, unit price) are stored as VARCHAR. This is a design choice from the original implementation.

## Class Architecture

### Class Diagram

```
+-------------------+     +-------------------+     +-------------------+
|    EntryPage      |---->|      Splash       |---->|    StartPage      |
|   (main entry)    |     |  (splash screen)  |     |   (main menu)     |
+-------------------+     +-------------------+     +-------------------+
                                                            |
                          +------------------+--------------+---------------+
                          |                  |                              |
                          v                  v                              v
                   +-------------+    +-------------+               +---------------+
                   |  LoginPage  |    | SignUpPage  |               |  AdminLogin   |
                   | (merchant)  |    | (register)  |               | (admin auth)  |
                   +-------------+    +-------------+               +---------------+
                          |                                                 |
                          v                                                 v
                   +-------------+                                  +---------------+
                   | LandingPage |                                  | AdminLanding  |
                   | (merchant   |                                  | (admin        |
                   |  dashboard) |                                  |  dashboard)   |
                   +-------------+                                  +---------------+
                          |                                                 |
        +-----------------+------------------+              +---------------+---------------+
        |        |        |        |         |              |               |               |
        v        v        v        v         v              v               v               v
+----------+ +--------+ +------+ +------+ +--------+ +----------+ +------------+ +----------+
|BillAdd   | |BillGen | |Bill  | |View  | |Merchant| |AdminVer  | |ViewAll     | |View      |
|NewCust   | |NewBill | |PayBill| |AllCust| |Feedback| |Merchant  | |Merchant    | |Feedbacks |
+----------+ +--------+ +------+ +------+ +--------+ +----------+ +------------+ +----------+

+-------------------+     +-------------------+
|   BillCalculator  |     |  InputValidator   |
|   (utility)       |     |   (utility)       |
+-------------------+     +-------------------+
        ^                         ^
        |                         |
        +-------------------------+
                  |
          Used by UI classes
          for validation and
          calculation

+-------------------+
|     DBValues      |
|  (configuration)  |
+-------------------+
        ^
        |
  Used by all classes
  for DB connection
```

### Core Classes

#### Entry Point Classes

**EntryPage.java**: The main entry point of the application. Contains the `main()` method that launches the splash screen and then displays the StartPage.

**Splash.java**: Displays the application splash screen for 9 seconds during startup.

**StartPage.java**: The main menu screen that provides navigation to merchant login, admin login, sign up, and configuration options.

#### Authentication Classes

**LoginPage.java**: Handles merchant authentication. Validates credentials against the users table and checks that the account is verified (confirmed = 1).

**SignUpPage.java**: Handles new merchant registration. Creates new records in the users table with confirmed = 0.

**AdminLogin.java**: Handles administrator authentication using hardcoded credentials (admin/pass or arav/arav).

#### Dashboard Classes

**LandingPage.java**: The merchant dashboard displayed after successful login. Provides navigation to all merchant functions.

**AdminLanding.java**: The administrator dashboard displayed after admin login. Provides navigation to admin functions.

#### Billing Classes

**BillAddNewCustomer.java**: Form for adding new customers to the system. Validates input and inserts records into customerdetails table.

**BillGenerateNewBill.java**: Form for generating new bills. Retrieves customer details by account number, calculates bill amount, and inserts records into billdetails table.

**BillPayBill.java**: Form for processing bill payments. Retrieves unpaid bills by account number and updates the paid status to 1.

**BillViewOldBillOptions.java**: Navigation screen for viewing bill history. Provides options to view paid or unpaid bills.

**BillViewPaidBills.java**: Displays paid bills for a given account number.

**BillViewUnpaidBills.java**: Displays unpaid bills for a given account number.

#### Customer Management Classes

**ViewAllCustomer.java**: Displays a table of all registered customers.

#### Admin Classes

**AdminVerifyMerchant.java**: Allows administrators to verify or reject pending merchant registrations.

**ViewAllMerchant.java**: Displays a table of all registered merchants.

**ViewFeedbacks.java**: Displays all feedback submitted by merchants.

#### Utility Classes

**BillCalculator.java**: Stateless utility class for bill calculations. Provides methods for calculating bill amounts and validating unit prices. Extracted from UI code to enable unit testing.

**InputValidator.java**: Stateless utility class for input validation. Provides methods for validating usernames, passwords, account numbers, and contact numbers. Extracted from UI code to enable unit testing.

**DBValues.java**: Configuration class containing database connection parameters (host, port, database name, username, password).

**SetDBValues.java**: Form for modifying database connection parameters (accessible only to super users).

#### Other Classes

**MerchantFeedback.java**: Form for merchants to submit feedback to administrators.

**AboutDeveloper.java**: Displays information about the application developer.

## Authentication Flow

### Merchant Authentication

```
                    +------------------+
                    |    StartPage     |
                    +------------------+
                            |
                            | Click "Log In"
                            v
                    +------------------+
                    |    LoginPage     |
                    +------------------+
                            |
                            | Enter credentials
                            v
                    +------------------+
                    | Validate Input   |
                    | - Username blank?|
                    | - Has spaces?    |
                    | - Password blank?|
                    +------------------+
                            |
                            | If valid
                            v
                    +------------------+
                    | Query Database   |
                    | SELECT * FROM    |
                    | users WHERE      |
                    | uname=? AND      |
                    | upass=? AND      |
                    | confirmed=1      |
                    +------------------+
                            |
              +-------------+-------------+
              |                           |
              v                           v
    +------------------+        +------------------+
    | User Found       |        | User Not Found   |
    | -> LandingPage   |        | -> Error Message |
    +------------------+        +------------------+
```

### Admin Authentication

```
                    +------------------+
                    |    StartPage     |
                    +------------------+
                            |
                            | Click "Login" (Admin)
                            v
                    +------------------+
                    |   AdminLogin     |
                    +------------------+
                            |
                            | Enter credentials
                            v
                    +------------------+
                    | Validate Input   |
                    | - Username blank?|
                    | - Has spaces?    |
                    | - Password blank?|
                    +------------------+
                            |
                            | If valid
                            v
                    +------------------+
                    | Check Hardcoded  |
                    | Credentials:     |
                    | admin/pass OR    |
                    | arav/arav        |
                    +------------------+
                            |
              +-------------+-------------+
              |                           |
              v                           v
    +------------------+        +------------------+
    | Match Found      |        | No Match         |
    | -> AdminLanding  |        | -> Error Message |
    +------------------+        +------------------+
```

### Super User Authentication

```
                    +------------------+
                    |    StartPage     |
                    +------------------+
                            |
                            | Click "Configure"
                            v
                    +------------------+
                    | Password Prompt  |
                    | (JOptionPane)    |
                    +------------------+
                            |
                            | Enter password
                            v
                    +------------------+
                    | Check Password   |
                    | == "iamarav@"    |
                    +------------------+
                            |
              +-------------+-------------+
              |                           |
              v                           v
    +------------------+        +------------------+
    | Correct          |        | Incorrect        |
    | -> SetDBValues   |        | -> Error Message |
    +------------------+        +------------------+
```

## UI Navigation Map

### Main Navigation Flow

```
                              +------------------+
                              |    EntryPage     |
                              |    (main())      |
                              +------------------+
                                      |
                                      v
                              +------------------+
                              |     Splash       |
                              |   (9 seconds)    |
                              +------------------+
                                      |
                                      v
                              +------------------+
                              |    StartPage     |
                              +------------------+
                                      |
            +------------+------------+------------+------------+
            |            |            |            |            |
            v            v            v            v            v
      +---------+  +---------+  +---------+  +---------+  +---------+
      |LoginPage|  |SignUp   |  |AdminLogin|  |Configure|  |About    |
      |         |  |Page     |  |         |  |(Super)  |  |Developer|
      +---------+  +---------+  +---------+  +---------+  +---------+
            |                         |
            v                         v
      +-----------+            +-----------+
      |LandingPage|            |AdminLanding|
      +-----------+            +-----------+
```

### Merchant Navigation

```
                              +------------------+
                              |   LandingPage    |
                              +------------------+
                                      |
      +----------+----------+----------+----------+----------+----------+
      |          |          |          |          |          |          |
      v          v          v          v          v          v          v
+--------+ +--------+ +--------+ +--------+ +--------+ +--------+ +--------+
|AddNew  | |Generate| |PayBill | |ViewAll | |ViewPrev| |Feedback| |LogOut  |
|Customer| |NewBill |          | |Customer| |Bills   |          | |->Start |
+--------+ +--------+ +--------+ +--------+ +--------+ +--------+ +--------+
                                                 |
                                    +------------+------------+
                                    |                         |
                                    v                         v
                              +-----------+            +-----------+
                              |ViewPaid   |            |ViewUnpaid |
                              |Bills      |            |Bills      |
                              +-----------+            +-----------+
```

### Admin Navigation

```
                              +------------------+
                              |  AdminLanding    |
                              +------------------+
                                      |
            +------------+------------+------------+------------+
            |            |            |            |            |
            v            v            v            v            v
      +---------+  +---------+  +---------+  +---------+
      |Verify   |  |ViewAll  |  |View     |  |LogOut   |
      |Merchants|  |Merchants|  |Feedbacks|  |->Start  |
      +---------+  +---------+  +---------+  +---------+
```

## Data Flow

### Bill Generation Flow

```
User Input                    Processing                      Database
----------                    ----------                      --------

Account Number  ─────────────> Query customerdetails ────────> SELECT
                               by acno
                                    |
                                    v
                              Customer Found?
                                    |
                              Yes   |   No
                               |    |    |
                               v    |    v
Units Consumed  ─────────────> |    |  Error Message
Unit Price      ─────────────> |    |
Bill Cycle      ─────────────> |
                               |
                               v
                         Calculate Bill
                         Amount = Units × Price
                               |
                               v
                         Validate Account
                         Number (no spaces)
                               |
                               v
                         INSERT into ─────────────────────────> billdetails
                         billdetails
```

### Payment Processing Flow

```
User Input                    Processing                      Database
----------                    ----------                      --------

Account Number  ─────────────> Query billdetails ─────────────> SELECT
                               WHERE acno=? AND paid=0
                                    |
                                    v
                              Unpaid Bill Found?
                                    |
                              Yes   |   No
                               |    |    |
                               v    |    v
                         Display Bill |  "No Unpaid Bills"
                         Details      |
                               |
                               v
                         User Clicks
                         "Pay Now"
                               |
                               v
                         UPDATE billdetails ──────────────────> UPDATE
                         SET paid=1
                         WHERE acno=? AND
                         billDate=? AND
                         billAmt=?
```

## Security Considerations

The current implementation has several security characteristics that should be noted:

**Password Storage**: Passwords are stored in plain text in the database. In a production environment, passwords should be hashed using a secure algorithm like bcrypt.

**SQL Injection**: The application uses string concatenation for SQL queries, which is vulnerable to SQL injection attacks. Prepared statements should be used instead.

**Hardcoded Credentials**: Admin credentials are hardcoded in the source code. In a production environment, these should be stored securely and configurable.

**No Session Management**: The application does not implement session timeouts or secure session handling.

**No Encryption**: Database connections are not encrypted. In a production environment, SSL/TLS should be used for database connections.

These are acceptable for a demonstration/educational project but would need to be addressed for production use.

## Technology Stack

| Component | Technology |
|-----------|------------|
| Programming Language | Java 8+ |
| UI Framework | Java Swing (AWT) |
| Database | MySQL 5.7+ |
| Database Connectivity | JDBC (MySQL Connector/J 8.0.30) |
| Testing | JUnit 4.13.2, Cucumber 6.10.4 |
| Build | Manual compilation / Eclipse IDE |
