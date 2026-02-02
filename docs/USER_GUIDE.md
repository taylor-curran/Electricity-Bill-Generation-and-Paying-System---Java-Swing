# User Guide

This guide provides a comprehensive walkthrough of the Electricity Bill Generation and Paying System, covering all features and workflows for each user role.

## Application Overview

The Electricity Bill Generation and Paying System is a desktop application designed for electricity utility companies to manage their billing operations. The application supports three types of users: Merchants (billing operators), Administrators, and Super Users. Each role has different access levels and capabilities within the system.

## Getting Started

### Launching the Application

When you start the application, a splash screen appears for a few seconds displaying the application branding. After the splash screen, the main welcome page loads with the following options:

- **Merchant Login**: For billing operators to access customer and billing functions
- **Sign Up**: For new merchants to create an account
- **Admin Login**: For administrators to manage the system
- **Configure**: For super users to modify database settings (disabled by default)
- **About Developer**: Information about the application creator

## User Roles and Access Levels

### Merchant Role

Merchants are the primary users of the system who handle day-to-day billing operations. After logging in, merchants can:

- Add new customers to the system
- Generate electricity bills for existing customers
- Process bill payments
- View paid and unpaid bills
- Submit feedback to administrators

Merchant accounts must be created through the Sign Up process and verified by an administrator before they can log in.

### Administrator Role

Administrators manage the system and user accounts. Admin capabilities include:

- Verify pending merchant registrations
- View all registered merchants
- View all customers in the system
- View merchant feedback

Admin login uses hardcoded credentials (see Default Credentials section).

### Super User Role

Super users have access to system configuration, specifically database connection settings. This role is accessed through the Configure button on the main page using a special password.

## Merchant Workflows

### Creating a Merchant Account

1. From the welcome page, click **Sign Up**
2. Fill in the registration form:
   - **Name**: Your full name
   - **Username**: A unique identifier (no spaces allowed)
   - **Password**: Your account password
   - **Confirm Password**: Re-enter your password
3. Click **Sign Up** to submit your registration
4. Wait for an administrator to verify your account
5. Once verified, you can log in using your credentials

### Logging In as a Merchant

1. From the welcome page, click **Log In** under "Merchant Login"
2. Enter your username and password
3. Click **Log In**
4. If successful, you'll be taken to the Landing Page

Note: If login fails, ensure your account has been verified by an administrator. Unverified accounts cannot log in.

### Adding a New Customer

Before generating bills, customers must be registered in the system.

1. From the Landing Page, click **Add New Customer**
2. Fill in the customer details:
   - **Customer Name**: Full name of the customer
   - **Account Number**: Unique identifier for the customer (no spaces allowed)
   - **Address**: Customer's street address
   - **City/District**: Select from the dropdown (Panipat, Rohtak, Gurugram, Samalkha, or Other)
   - **Contact Number**: 10-digit phone number
3. Click **Submit** to save the customer
4. A confirmation dialog appears asking if you want to add another customer

### Generating a New Bill

1. From the Landing Page, click **Generate New Bill**
2. Enter the customer's **Account Number**
3. Click **Get Details** to retrieve customer information
4. If the account exists, customer details (name, address, contact, city) are displayed
5. Enter billing information:
   - **Units Consumed**: Number of electricity units used (use the spinner to adjust)
   - **Unit Price**: Price per unit (range 6-10, adjustable via spinner)
   - **Bill Cycle Month**: Select the billing month
   - **Bill Cycle Year**: Select the billing year
6. Click **Submit** to generate the bill
7. The bill amount is calculated as: Units Consumed × Unit Price
8. A confirmation dialog appears with options to generate another bill or return to the Landing Page

### Paying a Bill

1. From the Landing Page, click **Pay Bill**
2. Enter the customer's **Account Number**
3. Click **Get Amount** to retrieve unpaid bill details
4. If unpaid bills exist, the following information is displayed:
   - Customer Name
   - Bill Amount
   - Contact Number
   - Bill Cycle
5. Click **Pay Now** to process the payment
6. The bill status is updated to "paid" in the database
7. A confirmation dialog appears with options to pay another bill or return home

### Viewing Bill History

The system provides options to view both paid and unpaid bills.

**Viewing Unpaid Bills:**
1. From the Landing Page, click **View Old Bills**
2. Click **View Unpaid Bills**
3. Enter the Account Number and click **Get Details**
4. Unpaid bills for that account are displayed

**Viewing Paid Bills:**
1. From the Landing Page, click **View Old Bills**
2. Click **View Paid Bills**
3. Enter the Account Number and click **Get Details**
4. Paid bills for that account are displayed

### Submitting Feedback

Merchants can submit feedback or report issues to administrators.

1. From the Landing Page, click **Feedback**
2. Fill in the feedback form:
   - **Username**: Your merchant username
   - **Email**: Your email address
   - **Message**: Your feedback or issue description
3. Click **Submit** to send the feedback

## Administrator Workflows

### Logging In as Administrator

1. From the welcome page, click **Login** under "Admin Login"
2. Enter admin credentials (hardcoded in `AdminLogin.java`)
3. Click **Log In**
4. You'll be taken to the Admin Landing Page

### Verifying Merchant Accounts

New merchant registrations require admin verification before they can log in.

1. From the Admin Landing Page, click **Verify Merchant**
2. A list of unverified merchants is displayed
3. Select a merchant to verify
4. Confirm the verification
5. The merchant can now log in to the system

### Viewing All Merchants

1. From the Admin Landing Page, click **View All Merchants**
2. A table displays all registered merchants with their details
3. Use this to monitor registered users in the system

### Viewing All Customers

1. From the Admin Landing Page, click **View All Customers**
2. A table displays all registered customers with their details:
   - Account Number
   - Customer Name
   - Address
   - City
   - Contact Number

### Viewing Merchant Feedback

1. From the Admin Landing Page, click **View Feedbacks**
2. A list of submitted feedback messages is displayed
3. Review feedback to address merchant concerns or issues

## Super User Workflow

### Accessing Database Configuration

The Configure feature allows modification of database connection settings. This is useful when the database server location or credentials change.

1. From the welcome page, click **Configure** (note: this button may be disabled)
2. Enter the super user password (hardcoded in `StartPage.java`)
3. If correct, the Database Configuration page opens
4. Modify the connection settings:
   - Database Name
   - Host
   - Port
   - Username
   - Password
5. Save the changes

Note: Incorrect database settings will prevent the application from connecting to the database. Only modify these settings if you understand the implications.

## Input Validation Rules

The application enforces several validation rules to ensure data integrity:

**Username Validation:**
- Cannot be blank
- Cannot contain spaces (including leading or trailing spaces)

**Password Validation:**
- Cannot be blank
- Spaces are allowed within the password

**Account Number Validation:**
- Cannot be blank
- Cannot contain spaces

**Contact Number Validation:**
- Cannot be blank
- Must contain only digits
- Must be at least 10 digits long

**Unit Price Validation:**
- Must be between 6 and 10 (inclusive)
- Enforced by the spinner control

## Bill Calculation

Bills are calculated using a simple formula:

```
Bill Amount = Units Consumed × Unit Price
```

For example, if a customer consumed 150 units and the unit price is 8:
```
Bill Amount = 150 × 8 = 1200
```

The unit price is configurable within the range of 6 to 10 per unit, allowing flexibility based on tariff structures.

## Navigation

Most screens include navigation buttons to move between different parts of the application:

- **Go Back**: Returns to the previous screen
- **Go to Home**: Returns to the Landing Page (for merchants) or Admin Landing Page (for admins)
- **Cancel**: Cancels the current operation and returns to the previous screen

## Tips for Efficient Use

1. **Verify account numbers**: Always double-check account numbers before generating bills or processing payments to avoid errors.

2. **Use Get Details**: Before generating a bill, use the "Get Details" button to verify you have the correct customer.

3. **Check for unpaid bills**: Before generating a new bill, check if the customer has unpaid bills that should be addressed first.

4. **Regular backups**: Ensure your database is backed up regularly to prevent data loss.

5. **Secure credentials**: Keep admin and super user passwords secure and change them if they become compromised.

## Error Messages

Common error messages and their meanings:

- **"Username cannot be blank"**: Enter a username before proceeding
- **"Username cannot have blank spaces"**: Remove spaces from the username
- **"Password cannot be blank"**: Enter a password before proceeding
- **"Please enter Account Number"**: The account number field is required
- **"Account Number cannot have spaces"**: Remove spaces from the account number
- **"No such Account Number exist!"**: The entered account number is not in the database
- **"Account Number already exists!!"**: When adding a customer, this account number is already registered
- **"No Unpaid Bills"**: The customer has no outstanding bills to pay
- **"Username/Password is Wrong or User is not verified by Admin"**: Check credentials or contact admin for verification

## Keyboard Shortcuts

The application primarily uses mouse-based navigation. Standard keyboard shortcuts apply:

- **Tab**: Move between form fields
- **Enter**: Activate the focused button
- **Escape**: Close dialog boxes (where applicable)

## Getting Help

If you encounter issues not covered in this guide:

1. Check the [Setup Guide](SETUP.md) for installation and configuration issues
2. Review the [Architecture](ARCHITECTURE.md) document for technical details
3. Submit feedback through the application's Feedback feature
4. Contact your system administrator
