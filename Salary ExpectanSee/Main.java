import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Salary Expectansee!");
        System.out.println("Are you an Admin or Employee?");
        System.out.print("Enter your role: ");
        String role = scanner.nextLine();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (role.equalsIgnoreCase("Admin") && UserManager.authenticateAdmin(username, password)) {
            AdminMenu.displayAdminMenu();
        } else if (role.equalsIgnoreCase("Employee") && UserManager.authenticateEmployee(username, password)) {
            EmployeeMenu.displayEmployeeMenu(username);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }
}
