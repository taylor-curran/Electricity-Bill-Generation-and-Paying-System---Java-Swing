# Setup Guide

This guide provides detailed instructions for setting up the Electricity Bill Generation and Paying System on your local machine for development or production use.

## System Requirements

### Hardware Requirements

The application is a lightweight desktop application with minimal hardware requirements. Any modern computer capable of running Java should be sufficient, though the following are recommended:

- Processor: 1 GHz or faster
- RAM: 512 MB minimum, 1 GB recommended
- Disk Space: 100 MB for the application, plus space for MySQL database
- Display: 1024x768 resolution or higher

### Software Requirements

**Java Development Kit (JDK)**

The application requires Java 8 or higher. JDK 17 is recommended for development as it is used in the CI/CD pipeline.

To check your Java version:
```bash
java -version
javac -version
```

To install Java on Ubuntu/Debian:
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

To install Java on macOS using Homebrew:
```bash
brew install openjdk@17
```

To install Java on Windows, download the installer from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/).

**MySQL Server**

MySQL 5.7 or higher is required for the database backend.

To install MySQL on Ubuntu/Debian:
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

To install MySQL on macOS using Homebrew:
```bash
brew install mysql
brew services start mysql
```

For Windows, download the MySQL Installer from [MySQL Downloads](https://dev.mysql.com/downloads/installer/).

## Database Setup

### Creating the Database

1. Start the MySQL service if it is not already running:
   ```bash
   # Ubuntu/Debian
   sudo systemctl start mysql
   
   # macOS
   brew services start mysql
   
   # Windows (from Command Prompt as Administrator)
   net start mysql
   ```

2. Log in to MySQL as root:
   ```bash
   mysql -u root -p
   ```

3. Import the database schema from the provided SQL file:
   ```bash
   mysql -u root -p < mysql_db_files/swing_electricity_bill.sql
   ```

   Alternatively, from within the MySQL prompt:
   ```sql
   SOURCE /path/to/mysql_db_files/swing_electricity_bill.sql;
   ```

### Database Schema Overview

The SQL file creates a database named `swing_electricity_bill` with four tables:

**users**: Stores merchant account information including username, password, name, and confirmation status. New merchants must be confirmed by an administrator before they can log in.

**customerdetails**: Stores customer information including account number, name, contact number, address, and city.

**billdetails**: Stores bill records including account number, bill date, payment status, bill amount, customer details, units consumed, and unit price.

**merchantfeedback**: Stores feedback submitted by merchants including username, email, and message content.

### Verifying the Database Setup

After importing, verify the database was created correctly:

```sql
mysql -u root -p
USE swing_electricity_bill;
SHOW TABLES;
```

You should see:
```
+----------------------------------+
| Tables_in_swing_electricity_bill |
+----------------------------------+
| billdetails                      |
| customerdetails                  |
| merchantfeedback                 |
| users                            |
+----------------------------------+
```

### Creating Test Data (Optional)

To test the application, you may want to add some sample data:

```sql
USE swing_electricity_bill;

-- Add a verified merchant user
INSERT INTO users (uname, name, upass, confirmed) 
VALUES ('testuser', 'Test User', 'testpass', 1);

-- Add a sample customer
INSERT INTO customerdetails (acno, custname, custContact, custaddr, custcity) 
VALUES ('ACC001', 'John Doe', '9876543210', '123 Main Street', 'Panipat');

-- Add a sample unpaid bill
INSERT INTO billdetails (acno, billDate, paid, billAmt, custname, custaddr, custcity, custcontact, unitconsumed, unitprice) 
VALUES ('ACC001', 'January 2024', 0, '800', 'John Doe', '123 Main Street', 'Panipat', '9876543210', '100', '8');
```

## JDBC Driver Configuration

The application uses the MySQL JDBC driver to connect to the database. The driver JAR file (`mysql-connector-java-8.0.30.jar`) is already included in the `source/lib/` directory.

### Important Note on Driver Class

The current codebase uses the legacy driver class name:
```java
Class.forName("com.mysql.jdbc.Driver");
```

While this still works with the included MySQL Connector/J 8.0.30, you may see a deprecation warning. The modern driver class name is `com.mysql.cj.jdbc.Driver`, but changing this is not required for the application to function.

### Database Connection Configuration

The database connection parameters are stored in `source/src/com/arav/minorproject/DBValues.java`:

```java
package com.arav.minorproject;

public class DBValues {
    static String dbname = "swing_electricity_bill";
    static String dbpass = "";           // MySQL password (empty by default)
    static String dbhost = "localhost";  // MySQL host
    static String dbuname = "root";      // MySQL username
    static String dbport = "3306";       // MySQL port
}
```

Modify these values to match your MySQL configuration:

- **dbname**: The database name (should remain `swing_electricity_bill` unless you renamed it)
- **dbpass**: Your MySQL root password (or the password for the user you are using)
- **dbhost**: The hostname where MySQL is running (use `localhost` for local development)
- **dbuname**: The MySQL username (default is `root`)
- **dbport**: The MySQL port (default is `3306`)

### Connecting to a Remote Database

If you need to connect to a remote MySQL server, update the `dbhost` value with the server's IP address or hostname:

```java
static String dbhost = "192.168.1.100";  // Remote server IP
```

Ensure the remote MySQL server is configured to accept connections from your machine and that the appropriate firewall rules are in place.

## IDE Setup

### Eclipse IDE Setup

Eclipse is the recommended IDE for this project as it was originally developed using Eclipse with the WindowBuilder extension.

1. **Download and Install Eclipse**:
   Download Eclipse IDE for Java Developers from [eclipse.org](https://www.eclipse.org/downloads/packages/).

2. **Install WindowBuilder Extension** (optional, for UI editing):
   - Go to Help > Eclipse Marketplace
   - Search for "WindowBuilder"
   - Install "WindowBuilder" and restart Eclipse

3. **Import the Project**:
   - Go to File > Import
   - Select "General > Existing Projects into Workspace"
   - Browse to the cloned repository directory
   - Select the project and click Finish

4. **Configure Build Path**:
   - Right-click on the project > Properties
   - Go to Java Build Path > Libraries
   - Ensure all JARs in `source/lib/` are included
   - If not, click "Add JARs" and select all JARs from the lib folder

5. **Run the Application**:
   - Navigate to `source/src/com/arav/minorproject/EntryPage.java`
   - Right-click > Run As > Java Application

### IntelliJ IDEA Setup

1. **Open the Project**:
   - Go to File > Open
   - Navigate to the cloned repository and select it

2. **Configure Project SDK**:
   - Go to File > Project Structure > Project
   - Set the Project SDK to JDK 8 or higher

3. **Add Library Dependencies**:
   - Go to File > Project Structure > Libraries
   - Click + > Java
   - Navigate to `source/lib/` and select all JAR files

4. **Mark Source Directories**:
   - Right-click on `source/src` > Mark Directory as > Sources Root
   - Right-click on `source/test` > Mark Directory as > Test Sources Root

5. **Run the Application**:
   - Navigate to `EntryPage.java`
   - Right-click > Run 'EntryPage.main()'

### VS Code Setup

1. **Install Java Extension Pack**:
   Install the "Extension Pack for Java" from the VS Code marketplace.

2. **Open the Project**:
   Open the cloned repository folder in VS Code.

3. **Configure Classpath**:
   Create or edit `.vscode/settings.json`:
   ```json
   {
     "java.project.sourcePaths": ["source/src", "source/test"],
     "java.project.referencedLibraries": ["source/lib/**/*.jar"]
   }
   ```

4. **Run the Application**:
   Open `EntryPage.java` and click the "Run" button above the main method.

## Command Line Compilation

If you prefer not to use an IDE, you can compile and run the application from the command line.

### Compiling the Source Code

```bash
cd source

# Create output directory
mkdir -p bin

# Compile all Java files
javac -cp "lib/*" -d bin src/com/arav/minorproject/*.java
```

### Running the Application

```bash
cd source
java -cp "bin:lib/*" com.arav.minorproject.EntryPage
```

On Windows, use semicolons instead of colons in the classpath:
```cmd
java -cp "bin;lib\*" com.arav.minorproject.EntryPage
```

## Troubleshooting

### Common Issues and Solutions

**Issue: "Cannot find MySQL JDBC Driver" or ClassNotFoundException**

Solution: Ensure the MySQL connector JAR is in the classpath. If using an IDE, verify that `mysql-connector-java-8.0.30.jar` is added to the project's build path.

**Issue: "Access denied for user 'root'@'localhost'"**

Solution: Verify your MySQL password in `DBValues.java`. If you set a password during MySQL installation, update the `dbpass` field accordingly.

**Issue: "Unknown database 'swing_electricity_bill'"**

Solution: The database has not been created. Run the SQL import command:
```bash
mysql -u root -p < mysql_db_files/swing_electricity_bill.sql
```

**Issue: "Communications link failure" or "Connection refused"**

Solution: MySQL server is not running. Start the MySQL service:
```bash
# Ubuntu/Debian
sudo systemctl start mysql

# macOS
brew services start mysql
```

**Issue: Application window appears but is blank or has missing images**

Solution: Ensure you are running the application from the correct directory so that image resources can be found. The images are located in `source/src/com/arav/minorproject/` and are loaded as resources.

**Issue: "No suitable driver found for jdbc:mysql://..."**

Solution: The JDBC driver is not in the classpath. When running from command line, ensure you include the lib directory:
```bash
java -cp "bin:lib/*" com.arav.minorproject.EntryPage
```

**Issue: Compilation errors about missing symbols**

Solution: Ensure all source files are being compiled together. The classes have dependencies on each other, so compile all files at once:
```bash
javac -cp "lib/*" -d bin src/com/arav/minorproject/*.java
```

### Getting Help

If you encounter issues not covered here, please check the following resources:

1. Review the [Architecture documentation](ARCHITECTURE.md) to understand the system design
2. Check the [Testing documentation](TESTING.md) to verify your setup by running tests
3. Open an issue on the GitHub repository with details about your problem
