# Electricity Bill Generation and Paying System

A desktop application built with Java Swing for generating and managing electricity bills. This system provides a complete solution for electricity utility companies to manage customers, generate bills based on consumption, and process payments through an intuitive graphical interface.

## Features

The application provides comprehensive billing functionality through three distinct user roles. Merchants can log in to manage customers, generate new bills, process payments, and view billing history. Administrators have access to verify merchant accounts and view all registered merchants and customers. A super user role provides access to database configuration settings.

Core capabilities include customer registration with account numbers, bill generation based on units consumed and configurable unit prices (range 6-10), payment processing with bill status tracking, and viewing of paid and unpaid bills. The system uses MySQL for persistent data storage and includes input validation for usernames, passwords, account numbers, and contact information.

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 8 or higher
- **MySQL Server**: Version 5.7 or higher
- **MySQL JDBC Driver**: The connector JAR for database connectivity
- **IDE (optional)**: Eclipse IDE with WindowBuilder plugin for development

## Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/taylor-curran/Electricity-Bill-Generation-and-Paying-System---Java-Swing.git
   cd Electricity-Bill-Generation-and-Paying-System---Java-Swing
   ```

2. **Set up the MySQL database**
   ```bash
   mysql -u root -p < mysql_db_files/swing_electricity_bill.sql
   ```

3. **Configure database connection** (if needed)
   
   Edit `source/src/com/arav/minorproject/DBValues.java` to match your MySQL configuration:
   ```java
   static String dbname = "swing_electricity_bill";
   static String dbhost = "localhost";
   static String dbport = "3306";
   static String dbuname = "root";
   static String dbpass = "";  // Add your MySQL password here
   ```

4. **Compile and run the application**
   ```bash
   cd source
   javac -d bin src/com/arav/minorproject/*.java
   java -cp bin com.arav.minorproject.EntryPage
   ```

   Alternatively, import the project into Eclipse IDE and run `EntryPage.java`.

## Default Credentials

Default credentials for development and testing are documented in the [User Guide](docs/USER_GUIDE.md). These include hardcoded admin credentials and a super user password for database configuration access.

**Important**: For production deployments, these credentials should be changed. See the [Architecture documentation](docs/ARCHITECTURE.md#security-considerations) for security recommendations.

**Merchant accounts** must be created through the Sign Up page and verified by an administrator before they can log in.

## Basic Usage

After launching the application, you will see the main welcome screen with options for Merchant Login, Admin Login, and Sign Up. Merchants can log in to access the landing page where they can add new customers, generate bills, pay bills, and view billing history. The bill generation process involves entering a customer's account number, retrieving their details, specifying units consumed and unit price, selecting the billing cycle, and submitting the bill.

For detailed usage instructions, see [docs/USER_GUIDE.md](docs/USER_GUIDE.md).

## Project Structure

```
├── source/
│   ├── src/com/arav/minorproject/    # Java source files (24 classes)
│   ├── test/com/arav/minorproject/   # JUnit and Cucumber tests
│   ├── lib/                          # Third-party JAR dependencies
│   └── bin/                          # Compiled class files
├── mysql_db_files/
│   └── swing_electricity_bill.sql    # Database schema
├── docs/                             # Documentation
├── jar/                              # Executable JAR files
└── run_tests.sh                      # Test runner script
```

## Documentation

- [Setup Guide](docs/SETUP.md) - Detailed installation and configuration instructions
- [User Guide](docs/USER_GUIDE.md) - Feature walkthrough and workflows
- [Architecture](docs/ARCHITECTURE.md) - System design and database schema
- [Testing](docs/TESTING.md) - Test execution and coverage information

## Running Tests

The project includes JUnit unit tests and Cucumber BDD tests. Run all tests using:

```bash
./run_tests.sh
```

See [docs/TESTING.md](docs/TESTING.md) for detailed testing information.

## Technologies Used

- **Java Swing**: GUI framework for the desktop interface
- **JDBC**: Database connectivity to MySQL
- **MySQL**: Relational database for data persistence
- **JUnit 4.13.2**: Unit testing framework
- **Cucumber 6.10.4**: BDD testing framework

## License

This project is available under the MIT License. See the [LICENSE](LICENSE) file for details.

## Author

Originally developed by Gaurav Sachdeva as a minor project demonstrating Java Swing and JDBC integration.
