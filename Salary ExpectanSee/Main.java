import java.util.Scanner;

public class Main 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        while (true) 
        {
            System.out.print("Enter your role (Admin/Employee): ");
            String role = scanner.nextLine();

            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (role.equalsIgnoreCase("Admin") && UserManager.authenticateAdmin(username, password)) 
            {
                boolean loggedOut = AdminMenu.displayAdminMenu();
                if (loggedOut) 
                {
                    continue;
                } 
                else 
                {
                    break;
                }
            } 
            else if (role.equalsIgnoreCase("Employee") && UserManager.authenticateEmployee(username, password)) 
            {
                boolean loggedOut = EmployeeMenu.displayEmployeeMenu(username);
                if (loggedOut) 
                {
                    continue; 
                } 
                else 
                {
                    break;
                }
            } 
            else 
            {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
        scanner.close();
    }
}