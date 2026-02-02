# Electricity Bill Generation and Paying System

A comprehensive desktop application built with Java Swing for generating and managing electricity bills. This system provides a complete solution for electricity utility companies to manage customer accounts, generate bills based on consumption, process payments, and maintain records.

## Features

The application provides a full-featured billing management system with the following capabilities:

**Customer Management**: Add new customers with account numbers, contact details, and addresses. View all registered customers and their information.

**Bill Generation**: Generate electricity bills by entering units consumed and unit price. The system calculates the total amount automatically with validation to ensure unit prices fall within the acceptable range of 6-10 per unit.

**Payment Processing**: Process bill payments with instant status updates. View unpaid bills by account number and mark them as paid.

**Bill History**: View previous bills including both paid and unpaid bills. Track billing cycles by month and year.

**User Role Management**: The system supports three distinct user roles with different access levels - Merchants (billing operators), Administrators, and Super Users.

**Feedback System**: Merchants can submit feedback to administrators, and administrators can view all submitted feedback.

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 8 or higher (JDK 17 recommended for development)
- **MySQL Server**: Version 5.7 or higher
- **MySQL JDBC Driver**: Included in the `source/lib/` directory (mysql-connector-java-8.0.30.jar)

For development, you will also need:
- An IDE such as Eclipse (Oxygen or later recommended) with WindowBuilder extension
- Git for version control

## Quick Start

Follow these steps to get the application running:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/taylor-curran/Electricity-Bill-Generation-and-Paying-System---Java-Swing.git
   cd Electricity-Bill-Generation-and-Paying-System---Java-Swing
   ```

2. **Set up the MySQL database**:
   ```bash
   mysql -u root -p < mysql_db_files/swing_electricity_bill.sql
   ```

3. **Configure database connection** (if needed):
   Edit `source/src/com/arav/minorproject/DBValues.java` to match your MySQL configuration:
   ```java
   static String dbname = "swing_electricity_bill";
   static String dbpass = "";        // Your MySQL password
   static String dbhost = "localhost";
   static String dbuname = "root";
   static String dbport = "3306";
   ```

4. **Compile and run**:
   Using an IDE like Eclipse, import the project and run `EntryPage.java` as a Java Application.

   Or compile from command line:
   ```bash
   cd source
   javac -cp "lib/*" -d bin src/com/arav/minorproject/*.java
   java -cp "bin:lib/*" com.arav.minorproject.EntryPage
   ```

## Default Credentials

**Admin Login** (for administrative functions):
- Username: `admin`, Password: `pass`
- Username: `arav`, Password: `arav`

**Super User** (for database configuration):
- Password: `iamarav@`

Merchant accounts must be created through the Sign Up process and verified by an administrator before they can log in.

## Basic Usage

1. **Launch the application**: Run `EntryPage.java` to start. A splash screen will appear followed by the main menu.

2. **Merchant Login**: Click "Log In" on the left side, enter your credentials, and access the merchant dashboard where you can:
   - Add new customers
   - Generate bills
   - Process payments
   - View customer records
   - Submit feedback

3. **Admin Login**: Click "Login" on the right side under "Admin Login" to access administrative functions:
   - Verify new merchant accounts
   - View all merchants
   - Read merchant feedback

## Project Structure

```
Electricity-Bill-Generation-and-Paying-System---Java-Swing/
├── README.md                    # This file
├── docs/                        # Documentation
│   ├── SETUP.md                # Detailed setup instructions
│   ├── USER_GUIDE.md           # Feature walkthrough
│   ├── ARCHITECTURE.md         # System design documentation
│   └── TESTING.md              # Testing documentation
├── mysql_db_files/             # Database schema
│   └── swing_electricity_bill.sql
├── source/
│   ├── src/com/arav/minorproject/  # Java source files
│   ├── test/                       # Test files
│   └── lib/                        # Dependencies (JARs)
├── jar/                        # Compiled JAR files
└── run_tests.sh               # Test runner script
```

## Running Tests

The project includes a comprehensive test suite using JUnit and Cucumber BDD:

```bash
./run_tests.sh
```

This will compile the source code and tests, then run both JUnit unit tests and Cucumber BDD tests.

## Documentation

For more detailed information, see:

- [Setup Guide](docs/SETUP.md) - Detailed installation and configuration instructions
- [User Guide](docs/USER_GUIDE.md) - Complete feature walkthrough with screenshots
- [Architecture](docs/ARCHITECTURE.md) - System design and database schema
- [Testing](docs/TESTING.md) - Test execution and coverage information

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

Originally developed by Gaurav Sachdeva as a minor project demonstrating Java Swing and JDBC integration.
