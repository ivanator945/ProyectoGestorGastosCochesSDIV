package views;

import controllers.UserController;
import modelsentities.User;

import java.sql.SQLException;
import java.util.Scanner;

public class UserView {
    private Scanner scanner = new Scanner(System.in);
    private UserController userController = new UserController();
    private User currentUser = null;

    public void showLoginMenu() {
        while (currentUser == null) {
            System.out.println("===== LOGIN / REGISTRO =====");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.print("Opción: ");
            String opt = scanner.nextLine();

            switch (opt) {
                case "1":
                    login();
                    break;
                case "2":
                    register();
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void login() {
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        try {
            User user = userController.login(name, password);
            if (user != null) {
                currentUser = user;
                System.out.println("Sesión iniciada como: " + user.getName());
            } else {
                System.out.println("Credenciales incorrectas.");
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
    }

    private void register() {
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        try {
            boolean success = userController.register(name, password);
            if (success) {
                System.out.println("Registro exitoso. Ahora puedes iniciar sesión.");
            } else {
                System.out.println("No se pudo registrar. Es posible que el nombre ya esté en uso.");
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
