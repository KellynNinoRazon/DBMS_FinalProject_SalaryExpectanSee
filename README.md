# Salary Expectansee - Database Integration

## Overview
The **Salary Expectansee** project integrates with a MySQL database to ensure persistent and efficient management of employee data. The database stores crucial information such as user credentials, hourly rates, work logs, and total salaries. This allows for transparency, accountability, and scalability in managing employee records.

---

## Database Schema

### 1. **Database Name**
`salary_expectansee`

### 2. **Tables**

#### `employees`
Here’s the schema presented in the format you want:

### `admins`
| Column Name  | Data Type    | Constraints               |
|--------------|--------------|---------------------------|
| `username`   | VARCHAR(50)  | Primary Key               |
| `password`   | VARCHAR(50)  | Not Null                  |
---

### `employees`
| Column Name   | Data Type    | Constraints               |
|---------------|--------------|---------------------------|
| `username`    | VARCHAR(50)  | Primary Key               |
| `password`    | VARCHAR(50)  | Not Null                  |
| `hourly_rate` | DOUBLE       | Not Null                  |
---

### `work_hours`
| Column Name    | Data Type    | Constraints                                             |
|----------------|--------------|---------------------------------------------------------|
| `id`           | INT          | Primary Key, Auto Increment                             |
| `username`     | VARCHAR(50)  | Not Null, Foreign Key (References `employees.username`) |
| `date`         | DATE         | Not Null                                                |
| `hours_worked` | DOUBLE       | Not Null                                                |
---

## Database Connection

### Prerequisites
1. Install MySQL server and MySQL Workbench.
2. Add the MySQL JDBC Driver (MySQL Connector/J) to your project.

### Configuration
- Database URL: `jdbc:mysql://localhost:3306/salary_expectansee`
- MySQL Username: `sqluser`
- MySQL Password: `password`

### JDBC Setup
Use the `DBConnection` class to manage database connections:
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/salary_expectansee";
    private static final String USER = "sqluser";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        
        return DriverManager.getConnection(URL, USER, PASSWORD);
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
Table admins {
  username varchar(50) [pk]
  password varchar(50) [not null]
}

Table employees {
  username varchar(50) [pk]
  password varchar(50) [not null]
  hourly_rate double [not null]
}

Table work_hours {
  id int [pk, increment]
  username varchar(50) [not null, ref: > employees.username]
  date date [not null]
  hours_worked double [not null]
}
```

### Insert Sample Data
```sql
INSERT INTO admins (username, password) VALUES ('admin', 'adminpass');
INSERT INTO employees (username, password, hourly_rate) VALUES ('employee1', 'emp123', 15.0);
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

