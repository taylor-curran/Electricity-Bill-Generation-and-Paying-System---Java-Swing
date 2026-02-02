# User Guide

This guide provides a comprehensive walkthrough of the Electricity Bill Generation and Paying System, covering all features and workflows for each user role.

## Getting Started

### Launching the Application

When you start the application by running `EntryPage.java`, a splash screen appears for approximately 9 seconds displaying the application branding. After the splash screen, the main Start Page is displayed.

### Start Page Overview

The Start Page serves as the main entry point and presents three primary options:

**Merchant Section (Left Side)**: This section is for billing operators who handle day-to-day operations like adding customers, generating bills, and processing payments. Merchants can either log in with existing credentials or sign up for a new account.

**Admin Section (Right Side)**: This section is for administrators who manage merchant accounts and oversee the system. Administrators use hardcoded credentials to access their dashboard.

**Configure Button**: This button (currently disabled in the UI) allows super users to modify database connection settings. It requires a special password for access.

**About Developer**: A button at the bottom provides information about the application developer.

## User Roles

The system supports three distinct user roles, each with different access levels and capabilities.

### Merchant Role

Merchants are the primary users of the system who perform billing operations. They must register for an account and wait for administrator approval before they can log in.

**Capabilities**:
- Add new customers to the system
- Generate electricity bills for customers
- Process bill payments
- View all customers
- View previous bills (paid and unpaid)
- Submit feedback to administrators

**Account Creation Process**:
1. Click "Sign Up" on the Start Page
2. Enter a username (no spaces allowed)
3. Enter your full name
4. Enter a password
5. Confirm the password
6. Click Submit

After registration, your account will have a "confirmed" status of 0, meaning you cannot log in until an administrator verifies your account.

### Administrator Role

Administrators manage the system and merchant accounts. They use pre-configured credentials to access the admin dashboard.

**Capabilities**:
- Verify new merchant accounts (approve or reject)
- View all registered merchants
- Read feedback submitted by merchants
- Log out and return to the Start Page

**Login Credentials**:
- Username: `admin`, Password: `pass`
- Username: `arav`, Password: `arav`

### Super User Role

Super Users have the highest level of access and can modify system configuration settings, specifically the database connection parameters.

**Capabilities**:
- Access the database configuration screen
- Modify database host, port, name, username, and password

**Access Method**:
The Configure button on the Start Page (when enabled) prompts for a password. Enter `iamarav@` to access the configuration screen.

## Merchant Workflows

### Logging In as a Merchant

1. From the Start Page, click "Log In" under the Merchant Login section
2. Enter your username (no spaces allowed)
3. Enter your password
4. Click "Log In"

If your credentials are correct and your account has been verified by an administrator, you will be taken to the Merchant Landing Page. If your account is not yet verified, you will see an error message indicating that you need to contact the administrator.

### Merchant Landing Page

After successful login, the Merchant Landing Page displays a welcome message with your username and provides access to all merchant functions:

- **Add New Customer**: Register new customers in the system
- **Generate a new bill**: Create bills for existing customers
- **Pay Bill**: Process payments for unpaid bills
- **View All Customers**: See a list of all registered customers
- **View Previous Bills**: Access bill history
- **Feedback**: Submit feedback to administrators
- **Log Out**: Return to the Start Page

### Adding a New Customer

1. From the Landing Page, click "Add New Customer"
2. Fill in the customer details:
   - **Customer Name**: Full name of the customer (required)
   - **Account Number**: Unique identifier for the customer (required, no spaces)
   - **Address**: Customer's address (required)
   - **City/District**: Select from the dropdown (Panipat, Rohtak, Gurugram, Samalkha, or Other)
   - **Contact Number**: 10-digit phone number (required)
3. Click "Submit"

The system validates all inputs before saving. If the account number already exists, an error message is displayed. On successful creation, you can choose to add another customer or return to the Landing Page.

### Generating a New Bill

1. From the Landing Page, click "Generate a new bill"
2. Enter the customer's Account Number
3. Click "Get Details" to retrieve customer information
4. If the account exists, the customer's name, address, contact, and city will be populated automatically
5. Enter billing details:
   - **Units Consumed**: Number of electricity units used (use the spinner control)
   - **Unit Price**: Price per unit, must be between 6 and 10 (use the spinner control)
   - **Bill Cycle Month**: Select the billing month
   - **Bill Cycle Year**: Select the billing year
6. Click "Submit" to generate the bill

The bill amount is calculated automatically as: `Units Consumed × Unit Price`

After successful bill generation, you can choose to generate another bill or return to the Landing Page.

### Processing a Bill Payment

1. From the Landing Page, click "Pay Bill"
2. Enter the customer's Account Number
3. Click "Get Amount" to retrieve unpaid bill information
4. The system displays:
   - Customer Name
   - Bill Amount
   - Contact Number
   - Bill Cycle (month and year)
5. Click "Pay Now" to process the payment

If there are no unpaid bills for the account, a message will indicate "No Unpaid Bills". After successful payment, the bill status is updated to "paid" in the database.

### Viewing All Customers

1. From the Landing Page, click "View All Customers"
2. A table displays all registered customers with their:
   - Account Number
   - Customer Name
   - Contact Number
   - Address
   - City

### Viewing Previous Bills

1. From the Landing Page, click "View Previous Bills"
2. Choose to view either:
   - **Paid Bills**: Bills that have been paid
   - **Unpaid Bills**: Bills that are still pending payment
3. Enter an Account Number and click "Get" to retrieve bills for that customer
4. The bill details are displayed including amount, date, and status

### Submitting Feedback

1. From the Landing Page, click "Feedback"
2. Enter your feedback details:
   - **Username**: Your merchant username (auto-filled if logged in)
   - **Email**: Your email address
   - **Message**: Your feedback or comments
3. Click "Submit" to send the feedback to administrators

## Administrator Workflows

### Logging In as an Administrator

1. From the Start Page, click "Login" under the Admin Login section
2. Enter the admin username (either `admin` or `arav`)
3. Enter the corresponding password (`pass` for admin, `arav` for arav)
4. Click "Log In"

### Admin Landing Page

After successful login, the Admin Landing Page displays "Hello Admin!" and provides access to administrative functions:

- **Verify Merchants**: Approve or reject new merchant registrations
- **View All Merchants**: See a list of all registered merchants
- **View Feedbacks from Merchant**: Read feedback submitted by merchants
- **Log out**: Return to the Start Page

### Verifying Merchant Accounts

1. From the Admin Landing Page, click "Verify Merchants"
2. A list of unverified merchants is displayed
3. For each merchant, you can:
   - **Verify**: Approve the account, allowing the merchant to log in
   - **Reject**: Deny the account (the merchant will not be able to log in)

When you verify a merchant, their "confirmed" status in the database is updated from 0 to 1.

### Viewing All Merchants

1. From the Admin Landing Page, click "View All Merchants"
2. A table displays all registered merchants with their:
   - Username
   - Full Name
   - Confirmation Status (0 = unverified, 1 = verified)

### Reading Merchant Feedback

1. From the Admin Landing Page, click "View Feedbacks from Merchant"
2. A list of all submitted feedback is displayed, showing:
   - Merchant Username
   - Email Address
   - Feedback Message

## Bill Calculation Details

The system calculates electricity bills using a simple formula:

```
Bill Amount = Units Consumed × Unit Price
```

### Unit Price Validation

The unit price must be within the range of 6 to 10 (inclusive). This validation is enforced both in the UI (through spinner controls) and in the business logic layer.

### Example Calculations

| Units Consumed | Unit Price | Bill Amount |
|----------------|------------|-------------|
| 100            | 8          | 800         |
| 250            | 6          | 1500        |
| 500            | 10         | 5000        |
| 0              | 8          | 0           |

## Input Validation Rules

The system enforces several validation rules to ensure data integrity:

### Username Validation
- Cannot be blank or empty
- Cannot contain spaces (including leading or trailing spaces)

### Password Validation
- Cannot be blank or empty
- Spaces are allowed within passwords

### Account Number Validation
- Cannot be blank or empty
- Cannot contain spaces

### Contact Number Validation
- Cannot be blank or empty
- Must contain only digits (0-9)
- Must be at least 10 digits long

### Customer Name Validation
- Cannot be blank or empty

### Address Validation
- Cannot be blank or empty

## Navigation Tips

### Going Back

Most screens include a "Go Back" or "Cancel" button that returns you to the previous screen without saving changes.

### Logging Out

To log out from any screen, navigate back to the Landing Page and click "Log Out". This returns you to the Start Page where you can log in as a different user or close the application.

### Continuing After Operations

After completing operations like adding a customer or generating a bill, the system typically asks if you want to continue. Clicking "Yes" allows you to perform another operation of the same type, while clicking "No" returns you to the Landing Page.

## Error Messages

The system displays informative error messages when validation fails or operations cannot be completed:

| Error Message | Cause | Solution |
|---------------|-------|----------|
| "Username cannot be blank" | Empty username field | Enter a valid username |
| "Username cannot have blank spaces" | Username contains spaces | Remove spaces from username |
| "Password cannot be blank" | Empty password field | Enter a password |
| "Please enter Account Number" | Empty account number field | Enter an account number |
| "Account Number cannot have spaces" | Account number contains spaces | Remove spaces from account number |
| "Contact Number cannot be blank" | Empty contact field | Enter a contact number |
| "Invalid Contact Number" | Contact number is not 10 digits | Enter a 10-digit number |
| "Account Number already exists" | Duplicate account number | Use a different account number |
| "No such Account Number exist" | Account not found | Verify the account number |
| "No Unpaid Bills" | No pending bills for account | No action needed |
| "Username/Password is Wrong or User is not verified" | Invalid credentials or unverified account | Check credentials or contact admin |

## Best Practices

### For Merchants

1. Always verify customer information before generating bills
2. Use the "Get Details" button to confirm account numbers exist before billing
3. Keep track of bill cycles to avoid duplicate billing
4. Submit feedback for any issues or suggestions

### For Administrators

1. Regularly check for new merchant registrations that need verification
2. Review merchant feedback to identify system issues or improvement opportunities
3. Verify merchant identities before approving accounts

### General Tips

1. Always log out when finished to prevent unauthorized access
2. Use strong passwords for merchant accounts
3. Keep account numbers consistent and well-organized
4. Regularly back up the MySQL database to prevent data loss
