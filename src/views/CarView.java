package views;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import controllers.CarController;
import modelsentities.Car;
import modelsentities.User;

public class CarView {
    private final Scanner scanner = new Scanner(System.in);
    private final CarController carController = new CarController();
    private final User currentUser;

    public CarView(User user) {
        this.currentUser = user;
    }

    public void manageCars() {
        while (true) {
            try {
                System.out.println("\n--- Gestión de Coches ---");
                System.out.println("1. Añadir coche");
                System.out.println("2. Listar coches");
                System.out.println("3. Añadir propietario a coche");
                System.out.println("0. Volver");
                System.out.print("Opción: ");
                String opt = scanner.nextLine().trim();

                switch (opt) {
                    case "1":
                        addCar();
                        break;
                    case "2":
                        listCars();
                        break;
                    case "3":
                        addOwnerToCar();
                        break;
                    case "0":
                        return; // salir del menú
                    default:
                        System.out.println("❗ Opción no válida. Intenta de nuevo.");
                }
            } catch (SQLException e) {
                System.out.println("❌ Error en base de datos: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠️ Error inesperado: " + e.getMessage());
                e.printStackTrace(); // Útil para depurar
            }
        }
    }

    private void addCar() throws SQLException {
        System.out.print("Marca: ");
        String brand = scanner.nextLine().trim();
        System.out.print("Modelo: ");
        String model = scanner.nextLine().trim();
        System.out.print("Matrícula: ");
        String licensePlate = scanner.nextLine().trim();

        int year;
        while (true) {
            year = readInt("Año (entre 1900 y 2100): ");
            if (year >= 1900 && year <= 2100) break;
            System.out.println("🔁 Por favor, introduce un año válido entre 1900 y 2100.");
        }

        if (carController.createCar(brand, model, licensePlate, year, currentUser.getUserId())) {
            System.out.println("✅ Coche añadido correctamente.");
        } else {
            System.out.println("❌ No se pudo añadir el coche.");
        }
    }

    private void listCars() throws SQLException {
        List<Car> cars = carController.getCarsByUserId(currentUser.getUserId());
        if (cars.isEmpty()) {
            System.out.println("🚘 No tienes coches registrados.");
            return;
        }

        System.out.println("--- Lista de tus coches ---");
        for (Car car : cars) {
            System.out.println(car.getCarId() + " | " +
                car.getBrand() + " " + car.getModel() + " - " +
                car.getLicensePlate() + " (" + car.getYear() + ")");
        }
    }

    private void addOwnerToCar() throws SQLException {
        // Opcional: mostrar todos los usuarios
        System.out.println("📋 Lista de usuarios disponibles:");
        List<User> users = carController.getAllUsers(); // Este método debe existir en el controlador
        for (User user : users) {
            System.out.println(user.getUserId() + " | " + user.getUserName());
        }

        System.out.print("🔑 ID del coche: ");
        String carId = scanner.nextLine().trim();
        System.out.print("🆔 UUID del nuevo propietario: ");
        String newOwnerUuid = scanner.nextLine().trim();

        // Validación para evitar duplicados
        if (carController.ownershipExists(newOwnerUuid, carId)) {
            System.out.println("⚠️ Este usuario ya es propietario de este coche.");
            return;
        }

        if (carController.addOwner(carId, newOwnerUuid)) {
            System.out.println("✅ Propietario añadido con éxito.");
        } else {
            System.out.println("❌ No se pudo añadir el propietario. Verifica los datos.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("🔢 Por favor, introduce un número válido.");
            }
        }
    }
}
