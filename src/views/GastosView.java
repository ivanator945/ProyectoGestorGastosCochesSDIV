package views;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import controllers.ExpenseController;
import modelsentities.Expense;
import modelsentities.Expense.ExpenseType;
import modelsentities.User;

public class GastosView {
    private final Scanner scanner;
    private final ExpenseController expenseController;
    private final User currentUser;

    public GastosView(User currentUser, ExpenseController expenseController, Scanner scanner) {
        this.currentUser = currentUser;
        this.expenseController = expenseController;
        this.scanner = scanner;
    }

    public void manageExpenses() {
        try {
            System.out.println("1. Añadir gasto");
            System.out.println("2. Listar gastos");
            System.out.println("3. Ver detalle de un gasto");
            System.out.println("4. Editar gasto");
            System.out.println("5. Eliminar gasto");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String opt = scanner.nextLine();
            switch (opt) {
                case "1": addExpense(); break;
                case "2": listExpenses(); break;
                case "3": viewExpense(); break;
                case "4": editExpense(); break;
                case "5": deleteExpense(); break;
                case "0": return;
                default: System.out.println("Opción no válida");
            }
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
        }
    }

    private void addExpense() {
        try {
            System.out.print("ID coche: ");
            String carId = scanner.nextLine();

            System.out.println("Tipos de gasto:");
            for (ExpenseType t : ExpenseType.values()) {
                System.out.println((t.ordinal() + 1) + ". " + t.name());
            }

            int typeIndex;
            while (true) {
                typeIndex = readInt("Selecciona tipo: ");
                if (typeIndex >= 1 && typeIndex <= ExpenseType.values().length) break;
                System.out.println("Selecciona un tipo válido.");
            }
            ExpenseType type = ExpenseType.values()[typeIndex - 1];

            int mileage = readInt("Kilometraje: ");

            LocalDate date;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            while (true) {
                try {
                    System.out.print("Fecha (YYYY-MM-DD): ");
                    String inputDate = scanner.nextLine();
                    date = LocalDate.parse(inputDate, formatter);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Formato inválido. Usa el formato exacto: YYYY-MM-DD");
                }
            }

            double amount = readDouble("Importe: ");
            System.out.print("Descripción (opcional): ");
            String desc = scanner.nextLine();

            if (expenseController.addExpense(carId, type, mileage, date, amount, desc)) {
                System.out.println("Gasto añadido.");
            } else {
                System.out.println("No se pudo añadir gasto.");
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar el gasto: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado al añadir el gasto: " + e.getMessage());
        }
    }

    private void listExpenses() {
        try {
            System.out.print("ID coche: ");
            String carId = scanner.nextLine();

            List<Expense> expenses = expenseController.getExpenses(carId, null, null, null);
            if (expenses.isEmpty()) {
                System.out.println("No hay gastos.");
                return;
            }

            for (Expense e : expenses) {
                System.out.println(e.getExpenseId() + " | " + e.getExpenseType() + " | " + e.getMileage() + "km | " + e.getDate() + " | " + e.getAmount() + " € | " + e.getDescription());
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los gastos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error inesperado al listar los gastos: " + e.getMessage());
        }
    }

   private void viewExpense() throws SQLException {
    System.out.print("Introduce el ID del gasto: ");
    String id = scanner.nextLine();  // UUID es String
    Expense e = expenseController.getExpenseById(id);
    if (e == null) {
        System.out.println("Gasto no encontrado.");
        return;
    }

    System.out.println("ID: " + e.getExpenseId());
    System.out.println("Tipo: " + e.getExpenseType());
    System.out.println("Kilometraje: " + e.getMileage());
    System.out.println("Fecha: " + e.getDate());
    System.out.println("Importe: " + e.getAmount());
    System.out.println("Descripción: " + e.getDescription());
}
private void editExpense() {
    try {
        System.out.print("Introduce el ID del gasto a editar: ");
        String id = scanner.nextLine();
        Expense existing = expenseController.getExpenseById(id);
        if (existing == null) {
            System.out.println("Gasto no encontrado.");
            return;
        }

        System.out.println("Deja en blanco para mantener el valor actual.");

        System.out.print("Nuevo tipo [" + existing.getExpenseType() + "]: ");
        String newTypeStr = scanner.nextLine();
        ExpenseType newType = newTypeStr.isEmpty() ? existing.getExpenseType() : ExpenseType.valueOf(newTypeStr.toUpperCase());

        System.out.print("Nuevo kilometraje [" + existing.getMileage() + "]: ");
        String newMileageStr = scanner.nextLine();
        int newMileage = newMileageStr.isEmpty() ? existing.getMileage() : Integer.parseInt(newMileageStr);

        System.out.print("Nueva fecha [" + existing.getDate() + "] (YYYY-MM-DD): ");
        String newDateStr = scanner.nextLine();
        LocalDate newDate = newDateStr.isEmpty() ? existing.getDate() : LocalDate.parse(newDateStr);

        System.out.print("Nuevo importe [" + existing.getAmount() + "]: ");
        String newAmountStr = scanner.nextLine();
        double newAmount = newAmountStr.isEmpty() ? existing.getAmount() : Double.parseDouble(newAmountStr);

        System.out.print("Nueva descripción [" + existing.getDescription() + "]: ");
        String newDesc = scanner.nextLine();
        if (newDesc.isEmpty()) newDesc = existing.getDescription();

        boolean updated = expenseController.updateExpense(id, newType, newMileage, newDate, newAmount, newDesc);
        System.out.println(updated ? "Gasto actualizado." : "No se pudo actualizar el gasto.");
    } catch (Exception e) {
        System.out.println("Error al editar el gasto: " + e.getMessage());
    }
}
private void deleteExpense() throws SQLException {
    System.out.print("Introduce el ID del gasto a eliminar: ");
    String id = scanner.nextLine();
    System.out.print("¿Seguro que quieres eliminar este gasto? (s/n): ");
    if (scanner.nextLine().equalsIgnoreCase("s")) {
        boolean deleted = expenseController.deleteExpense(id);
        System.out.println(deleted ? "Gasto eliminado." : "No se pudo eliminar el gasto.");
    } else {
        System.out.println("Eliminación cancelada.");
    }
}

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número entero válido.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número decimal válido.");
            }
        }
    }
}
