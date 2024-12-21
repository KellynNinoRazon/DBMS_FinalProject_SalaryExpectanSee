import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMenu 
{
    private static final Scanner scanner = new Scanner(System.in);

    public static boolean displayAdminMenu() 
    {
        while (true) 
        {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View All Employees' Total Salary and Hourly Rate");
            System.out.println("2. Search for a Specific Employee's Record");
            System.out.println("3. Change Hourly Rate for an Employee");
            System.out.println("4. Add a New Employee");
            System.out.println("5. Erase an Employee");
            System.out.println("6. Log Out");
            System.out.print("Enter your choice: ");
            
            int choice = -1;
            try 
            {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) 
            {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                scanner.next();
                continue;
            }

            switch (choice) 
            {
                case 1 -> viewAllEmployees();
                case 2 -> searchEmployeeRecord();
                case 3 -> changeHourlyRate();
                case 4 -> addNewEmployee();
                case 5 -> eraseEmployee();
                case 6 -> 
                {
                    System.out.println("Logging out...");
                    return true;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

    private static void viewAllEmployees() 
    {
        String query = """
                SELECT e.username, e.hourly_rate, 
                       COALESCE(SUM(w.hours_worked), 0) AS total_hours, 
                       COALESCE(SUM(w.hours_worked * e.hourly_rate), 0) AS total_salary
                FROM employees e
                LEFT JOIN work_hours w ON e.username = w.username
                GROUP BY e.username, e.hourly_rate
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) 
            {

            System.out.println("\nUsername | Hourly Rate | Total Hours | Total Salary");
            System.out.println("--------------------------------------------------");
            while (rs.next()) 
            {
                String username = rs.getString("username");
                double hourlyRate = rs.getDouble("hourly_rate");
                double totalHours = rs.getDouble("total_hours");
                double totalSalary = rs.getDouble("total_salary");
                System.out.printf("%s | %.2f | %.2f | %.2f%n", username, hourlyRate, totalHours, totalSalary);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    private static void searchEmployeeRecord() 
    {
        System.out.print("Enter the employee's username: ");
        String username = scanner.next();

        String query = """
                SELECT e.username, e.hourly_rate, 
                       COALESCE(SUM(w.hours_worked), 0) AS total_hours, 
                       COALESCE(SUM(w.hours_worked * e.hourly_rate), 0) AS total_salary
                FROM employees e
                LEFT JOIN work_hours w ON e.username = w.username
                WHERE e.username = ?
                GROUP BY e.username, e.hourly_rate
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) 
            {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) 
            {
                double hourlyRate = rs.getDouble("hourly_rate");
                double totalHours = rs.getDouble("total_hours");
                double totalSalary = rs.getDouble("total_salary");
                System.out.printf("\nUsername: %s%nHourly Rate: %.2f%nTotal Hours: %.2f%nTotal Salary: %.2f%n",
                        username, hourlyRate, totalHours, totalSalary);
            } 
            else 
            {
                System.out.println("No record found for the specified username.");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    private static void changeHourlyRate() 
    {
        System.out.print("Enter the employee's username: ");
        String username = scanner.next();
        System.out.print("Enter the new hourly rate: ");
        double newHourlyRate = scanner.nextDouble();

        String query = "UPDATE employees SET hourly_rate = ? WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
            {
            stmt.setDouble(1, newHourlyRate);
            stmt.setString(2, username);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) 
            {
                System.out.println("Hourly rate updated successfully.");
            } 
            else 
            {
                System.out.println("No record found for the specified username.");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    private static void addNewEmployee() 
    {
        System.out.print("Enter the new employee's username: ");
        String username = scanner.next();
        System.out.print("Enter the new employee's password: ");
        String password = scanner.next();
        System.out.print("Enter the new employee's hourly rate: ");
        double hourlyRate = scanner.nextDouble();

        String query = "INSERT INTO employees (username, password, hourly_rate) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
            {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setDouble(3, hourlyRate);
            stmt.executeUpdate();
            System.out.println("New employee added successfully.");
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    private static void eraseEmployee() 
    {
        System.out.print("Enter the employee's username: ");
        String username = scanner.next();

        String query = "DELETE FROM employees WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) 
            {
            stmt.setString(1, username);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee erased successfully.");
            } else {
                System.out.println("No record found for the specified username.");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}
