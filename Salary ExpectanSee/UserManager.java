import java.sql.*;

public class UserManager {
    public static boolean authenticateAdmin(String username, String password) {
        return authenticateUser(username, password, "admins");
    }

    public static boolean authenticateEmployee(String username, String password) {
        return authenticateUser(username, password, "employees");
    }

    private static boolean authenticateUser(String username, String password, String table) {
        String query = "SELECT * FROM " + table + " WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addEmployee(String username, String password, double hourlyRate) {
        String query = "INSERT INTO employees (username, password, hourly_rate) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setDouble(3, hourlyRate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeEmployee(String username) {
        String query = "DELETE FROM employees WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
