# Salary Expectansee - Database Integration

## Overview
The **Salary Expectansee** project integrates with a MySQL database to ensure persistent and efficient management of employee data. The database stores crucial information such as user credentials, hourly rates, work logs, and total salaries. This allows for transparency, accountability, and scalability in managing employee records.

---

## Database Schema

### 1. **Database Name**
`salary_expectansee`

### 2. **Tables**

#### `employees`
| Column Name   | Data Type       | Constraints                        |
|---------------|-----------------|------------------------------------|
| `id`          | INT             | Primary Key, Auto Increment        |
| `username`    | VARCHAR(50)     | Unique, Not Null                   |
| `password`    | VARCHAR(50)     | Not Null                           |
| `hourly_rate` | DECIMAL(10, 2)  | Default: 0.00                      |
| `total_hours` | DECIMAL(10, 2)  | Default: 0.00                      |

#### `work_logs`
| Column Name     | Data Type       | Constraints                        |
|-----------------|-----------------|------------------------------------|
| `log_id`        | INT             | Primary Key, Auto Increment        |
| `employee_id`   | INT             | Foreign Key (References `employees.id`) |
| `work_date`     | DATE            | Not Null                           |
| `hours_worked`  | DECIMAL(10, 2)  | Not Null                           |

---

## Database Connection

### Prerequisites
1. Install MySQL server and MySQL Workbench.
2. Add the MySQL JDBC Driver (MySQL Connector/J) to your project.

### Configuration
- Database URL: `jdbc:mysql://localhost:3306/salary_expectansee`
- MySQL Username: `root`
- MySQL Password: `your_password`

### JDBC Setup
Use the `DBConnection` class to manage database connections:
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/salary_expectansee";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database");
        }
    }
}
```

---

## Key Features

### Employee Functionalities
- **Login Authentication**: Validates employee credentials stored in the `employees` table.
- **Log Work Hours**: Inserts records into the `work_logs` table with the date and hours worked.
- **View Work Logs and Salary**: Retrieves and calculates total salary based on hours worked and hourly rate.
- **Clear Records**: Deletes all work logs for the employee.

### Admin Functionalities
- **Manage Employees**:
  - Add new employees.
  - Remove employees.
- **View Employee Records**:
  - List all employees with their total salary and hourly rate.
  - Search for specific employee details.
- **Update Hourly Rates**: Adjust hourly rates for specific employees.

---

## Sample SQL Scripts

### Create Database and Tables
```sql
CREATE DATABASE salary_expectansee;

USE salary_expectansee;

CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    hourly_rate DECIMAL(10, 2) DEFAULT 0.00,
    total_hours DECIMAL(10, 2) DEFAULT 0.00
);

CREATE TABLE work_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    work_date DATE,
    hours_worked DECIMAL(10, 2),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);
```

### Insert Sample Data
```sql
INSERT INTO employees (username, password, hourly_rate) VALUES ('employee1', 'emp123', 15.00);
INSERT INTO employees (username, password, hourly_rate) VALUES ('employee2', 'emp456', 20.00);

INSERT INTO work_logs (employee_id, work_date, hours_worked) VALUES (1, '2024-12-01', 8.0);
INSERT INTO work_logs (employee_id, work_date, hours_worked) VALUES (1, '2024-12-02', 6.5);
INSERT INTO work_logs (employee_id, work_date, hours_worked) VALUES (2, '2024-12-01', 7.0);
```

---

## Instructions for Running with Database

1. **Set Up MySQL Database**
   - Execute the provided SQL scripts to create the database and tables.

2. **Configure the Project**
   - Ensure the `DBConnection` class contains the correct database URL, username, and password.
   - Add the MySQL Connector/J JAR to your project classpath.

3. **Compile and Run**
   - Compile the Java classes.
   - Run the `Main.java` file.

4. **Test Features**
   - Login as an admin or employee.
   - Test employee functionalities like logging hours and viewing salaries.
   - Test admin functionalities like managing employee records and updating hourly rates.

---

## Future Enhancements
- Implement password hashing for secure authentication.
- Add support for detailed reports and analytics.
- Enable real-time updates using web technologies.

---

Integrating a database ensures data persistence and scalability for **Salary Expectansee**, providing a robust foundation for future growth and additional features.

