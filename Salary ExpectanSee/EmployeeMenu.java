import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeMenu 
{
    private static final Scanner scanner = new Scanner(System.in);

    public static boolean displayEmployeeMenu(String username) 
    {
        while (true) 
        {
            System.out.println("\nEmployee Menu:");
            System.out.println("1. Log Hours Worked");
            System.out.println("2. View Worked Hours and Salary");
            System.out.println("3. Clear Entries");
            System.out.println("4. Log Out");
            System.out.print("Enter your choice: ");
            
            int choice = -1;
            try 
            {
                choice = scanner.nextInt();
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                scanner.next();
                continue;
            }

            switch (choice) 
            {
                case 1 -> logHours(username);
                case 2 -> viewHoursAndSalary(username);
                case 3 -> clearEntries(username);
                case 4 -> 
                {
                    System.out.println("Logging out...");
                    return true;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private static void logHours(String username) 
    {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.next();  
        System.out.print("Enter hours worked: ");
        double hours = scanner.nextDouble();

        String query = "INSERT INTO work_hours (username, date, hours_worked) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) 
            {
            stmt.setString(1, username);
            stmt.setString(2, date);
            stmt.setDouble(3, hours);
            stmt.executeUpdate();
            System.out.println("Hours logged successfully.");
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    private static void viewHoursAndSalary(String username) 
    {
        String query = "SELECT date, hours_worked FROM work_hours WHERE username = ?";
        double totalHours = 0;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) 
            {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nDate        | Hours Worked");
            System.out.println("--------------------------");
            while (rs.next()) 
            {
                String date = rs.getString("date");
                double hours = rs.getDouble("hours_worked");
                totalHours += hours;
                System.out.printf("%s | %.2f%n", date, hours);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        double hourlyRate = getHourlyRate(username);
        double totalSalary = totalHours * hourlyRate;
        System.out.printf("Total Hours: %.2f%n", totalHours);
        System.out.printf("Hourly Rate: %.2f%n", hourlyRate);
        System.out.printf("Total Salary: %.2f%n", totalSalary);
    }

    private static double getHourlyRate(String username) 
    {
        String query = "SELECT hourly_rate FROM employees WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) 
            {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) 
            {
                return rs.getDouble("hourly_rate");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return 0;
    }

    private static void clearEntries(String username) 
    {
        String query = "DELETE FROM work_hours WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) 
            {
            stmt.setString(1, username);
            stmt.executeUpdate();
            System.out.println("All entries cleared.");
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}