# Setup Guide

This guide provides detailed instructions for setting up the Electricity Bill Generation and Paying System on your development machine.

## System Requirements

The application requires a Java runtime environment and MySQL database server. Ensure your system meets these minimum requirements before proceeding with installation.

**Operating System**: Windows, macOS, or Linux with GUI support

**Java**: JDK 8 or higher (JDK 11+ recommended for modern systems)

**MySQL**: Version 5.7 or higher

**Memory**: At least 512MB RAM available for the application

**Disk Space**: Approximately 100MB for the application and dependencies

## MySQL Database Setup

### Installing MySQL

If you don't have MySQL installed, download and install it from the official MySQL website or use your system's package manager.

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install mysql-server
sudo systemctl start mysql
sudo mysql_secure_installation
```

**macOS (using Homebrew):**
```bash
brew install mysql
brew services start mysql
mysql_secure_installation
```

**Windows:**
Download the MySQL Installer from https://dev.mysql.com/downloads/installer/ and follow the installation wizard.

### Importing the Database Schema

The database schema is provided in the `mysql_db_files/swing_electricity_bill.sql` file. This file creates the `swing_electricity_bill` database and all required tables.

1. Open a terminal or command prompt and connect to MySQL:
   ```bash
   mysql -u root -p
   ```

2. Create the database and import the schema:
   ```sql
   SOURCE /path/to/mysql_db_files/swing_electricity_bill.sql;
   ```

   Alternatively, import directly from the command line:
   ```bash
   mysql -u root -p < mysql_db_files/swing_electricity_bill.sql
   ```

3. Verify the database was created:
   ```sql
   USE swing_electricity_bill;
   SHOW TABLES;
   ```

   You should see four tables: `users`, `customerdetails`, `billdetails`, and `merchantfeedback`.

### Database Tables Overview

The schema creates the following tables:

**users**: Stores merchant account information including username, password, name, and verification status.

**customerdetails**: Contains customer records with account numbers, names, addresses, cities, and contact numbers.

**billdetails**: Stores generated bills with customer information, units consumed, unit price, bill amount, payment status, and billing cycle date.

**merchantfeedback**: Holds feedback messages submitted by merchants including username, email, and message content.

## JDBC Driver Configuration

The application uses the MySQL JDBC driver to connect to the database. The current codebase references the legacy driver class `com.mysql.jdbc.Driver`.

### Driver Location

Ensure the MySQL Connector/J JAR file is available in your classpath. If you're using Eclipse, add it to your project's build path. For command-line compilation, include it in the classpath when running the application.

Download the MySQL Connector/J from: https://dev.mysql.com/downloads/connector/j/

### Connection Configuration

Database connection parameters are defined in `source/src/com/arav/minorproject/DBValues.java`:

```java
static String dbname = "swing_electricity_bill";
static String dbpass = "";
static String dbhost = "localhost";
static String dbuname = "root";
static String dbport = "3306";
```

Modify these values to match your MySQL configuration:

- **dbname**: The database name (default: `swing_electricity_bill`)
- **dbhost**: MySQL server hostname (default: `localhost`)
- **dbport**: MySQL server port (default: `3306`)
- **dbuname**: MySQL username (default: `root`)
- **dbpass**: MySQL password (default: empty string)

If your MySQL installation uses a password for the root user, update the `dbpass` field accordingly.

### Note on Driver Deprecation

The codebase uses `com.mysql.jdbc.Driver` which is deprecated in newer versions of MySQL Connector/J. For MySQL Connector/J 8.0+, the driver class is `com.mysql.cj.jdbc.Driver`. The legacy driver name still works but may produce deprecation warnings.

## IDE Setup

### Eclipse IDE Setup

Eclipse is the recommended IDE for this project as it was originally developed using Eclipse with the WindowBuilder plugin.

1. **Download Eclipse**: Get Eclipse IDE for Java Developers from https://www.eclipse.org/downloads/

2. **Install WindowBuilder Plugin** (optional, for GUI editing):
   - Go to Help > Eclipse Marketplace
   - Search for "WindowBuilder"
   - Install "WindowBuilder" and restart Eclipse

3. **Import the Project**:
   - Go to File > Import > General > Existing Projects into Workspace
   - Select the `source` directory as the root directory
   - Click Finish

4. **Configure Build Path**:
   - Right-click the project > Build Path > Configure Build Path
   - Add the MySQL Connector/J JAR to the Libraries tab
   - Ensure all JARs in the `lib` folder are included

5. **Run the Application**:
   - Navigate to `src/com/arav/minorproject/EntryPage.java`
   - Right-click > Run As > Java Application

### IntelliJ IDEA Setup

1. **Open the Project**:
   - Go to File > Open
   - Select the repository root directory

2. **Configure Project Structure**:
   - Go to File > Project Structure
   - Set the Project SDK to JDK 8 or higher
   - Mark `source/src` as Sources root
   - Mark `source/test` as Test Sources root
   - Add all JARs from `source/lib` as libraries

3. **Run Configuration**:
   - Create a new Application run configuration
   - Set the main class to `com.arav.minorproject.EntryPage`

### Command-Line Compilation

If you prefer not to use an IDE, compile and run from the command line:

```bash
cd source

# Create output directory
mkdir -p bin

# Compile all source files
javac -d bin src/com/arav/minorproject/*.java

# Run the application (ensure MySQL Connector/J is in classpath)
java -cp "bin:lib/*" com.arav.minorproject.EntryPage
```

On Windows, use semicolons instead of colons in the classpath:
```cmd
java -cp "bin;lib\*" com.arav.minorproject.EntryPage
```

## Troubleshooting

### Database Connection Errors

**Error: "Communications link failure"**

This error indicates the application cannot connect to MySQL. Verify that:
- MySQL server is running (`sudo systemctl status mysql` on Linux)
- The hostname and port in `DBValues.java` are correct
- No firewall is blocking the connection

**Error: "Access denied for user"**

The username or password is incorrect. Check the credentials in `DBValues.java` match your MySQL user.

**Error: "Unknown database 'swing_electricity_bill'"**

The database hasn't been created. Import the schema file as described in the Database Setup section.

### Compilation Errors

**Error: "package com.mysql.jdbc does not exist"**

The MySQL Connector/J JAR is not in the classpath. Add it to your IDE's build path or include it in the classpath when compiling.

**Error: "cannot find symbol: class JFrame"**

Ensure you're using a JDK (not just JRE) and that the Java Swing libraries are available. This is typically included in standard JDK installations.

### Runtime Errors

**Error: "ClassNotFoundException: com.mysql.jdbc.Driver"**

The MySQL JDBC driver is not in the runtime classpath. Ensure the Connector/J JAR is included when running the application.

**Application window doesn't appear**

Verify you have a graphical display environment. On headless servers, you'll need to configure X11 forwarding or use a virtual display.

### GUI Display Issues

**Fonts appear incorrect or missing**

Install the required fonts on your system. The application uses fonts like "Raleway", "Cookie", and "Segoe UI". If unavailable, the system will substitute default fonts.

**Images not loading**

Ensure the image files in `source/src/com/arav/minorproject/` (like `power_icon.png`, `back_3.jpg`, etc.) are included in the compiled output or classpath.

## Verifying the Installation

After setup, verify everything works correctly:

1. **Start the application** - You should see a splash screen followed by the main welcome page.

2. **Test Admin Login** - Click "Admin Login" and use credentials `admin`/`pass` or `arav`/`arav`.

3. **Test Database Connection** - After logging in as admin, try viewing merchants or customers. If the database is configured correctly, you'll see the data (or empty lists if no data exists).

4. **Run the test suite** - Execute `./run_tests.sh` from the repository root to verify the core business logic works correctly.

## Next Steps

Once setup is complete, refer to the [User Guide](USER_GUIDE.md) for instructions on using the application's features.
