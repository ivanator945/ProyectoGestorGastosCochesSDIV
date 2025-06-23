package views;

import controllers.CarController;
import controllers.ExpenseController;
import controllers.UserController;
import modelsentities.User;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);
    private final UserController userController = new UserController();
    private final CarController carController = new CarController();
    private final ExpenseController expenseController = new ExpenseController();
    private final UserView userView = new UserView();

    private User currentUser;

    public void start() {
        userView.showLoginMenu();
        currentUser = userView.getCurrentUser();

        if (currentUser == null) {
            System.out.println("❌ No se ha iniciado sesión. Cerrando aplicación.");
            return;
        }

        while (true) {
            showMainMenu();
            if (currentUser == null) {
                System.out.println("✅ Sesión cerrada. Hasta luego.");
                break;
            }
        }
    }

    private void showMainMenu() {
        while (true) {
            try {
                System.out.println("\n--- Menú Principal ---");
                System.out.println("1. Gestionar coches");
                System.out.println("2. Gestionar gastos");
                System.out.println("3. Mostrar tu UUID");
                System.out.println("4. Logout");
                System.out.print("Opción: ");
                String opt = scanner.nextLine().trim();

                switch (opt) {
                    case "1":
                        CarView carView = new CarView(currentUser);
                        carView.manageCars();
                        break;
                    case "2":
                        GastosView gastosView = new GastosView(currentUser, expenseController, scanner);
                        gastosView.manageExpenses();
                        break;
                    case "3":
                        if (currentUser != null) {
                            System.out.println("🆔 Tu UUID es: " + currentUser.getUuid());
                        } else {
                            System.out.println("⚠️ No hay un usuario activo.");
                        }
                        break;
                    case "4":
                        currentUser = null;
                        return; 
                    default:
                        System.out.println("❗ Opción no válida. Elige entre 1 y 4.");
                }

            } catch (Exception e) {
                System.out.println("⚠️ Error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

  
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("🔢 Por favor, introduce un número entero válido.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("💰 Por favor, introduce un número decimal válido.");
            }
        }
    }
    
}
