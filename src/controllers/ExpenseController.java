package controllers;

import models.CarDAO;
import models.ExpenseDAO;
import modelsentities.Car;
import modelsentities.Expense;
import modelsentities.Expense.ExpenseType;
import utils.UUIDUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ExpenseController {
    private final ExpenseDAO expenseDAO = new ExpenseDAO();
    private final CarDAO carDAO = new CarDAO();

    public boolean addExpense(String carId, ExpenseType type, int mileage, LocalDate date, double amount, String description) throws SQLException {
    	// Busca el coche en la base de datos con el ID proporcionado (carId)
        Car car = carDAO.getCarById(carId);
        // Si no se encuentra el coche (es decir, car es null), muestra un mensaje de error en la consola
        // y devuelve 'false' para indicar que no se pudo añadir el gasto porque el coche no existe
        if (car == null) {
            System.err.println("Error: el coche con ID " + carId + " no existe.");
            return false;
        }
        // Genera un identificador único para el nuevo gasto (UUID)
        String expenseId = UUIDUtil.generateUUID();
        // Crea un nuevo objeto Expense con los datos proporcionados
        Expense expense = new Expense(expenseId, carId, type, mileage, date, amount, description);
        
        // Llama al método 'addExpense' del ExpenseDAO para insertar el gasto en la base de datos
        // Devuelve 'true' si la operación fue exitosa, o 'false' si falló
        return expenseDAO.addExpense(expense);
    }

    public List<Expense> getExpenses(String carId, Integer year, LocalDate date, Integer mileage) throws SQLException {
        return expenseDAO.getExpensesByCarId(carId, year, date, mileage);
    }

    public Expense getExpenseById(String id) throws SQLException {
        return expenseDAO.getExpenseById(id);
    }

    public boolean updateExpense(String id, ExpenseType newType, int newMileage, LocalDate newDate, double newAmount, String newDesc) throws SQLException {
    	// Busca en la base de datos el gasto con el ID proporcionado
        Expense expense = expenseDAO.getExpenseById(id);
        // Si no se encuentra el gasto (es decir, es null), muestra un mensaje de error en la consola
        // y devuelve 'false' indicando que la actualización no se puede hacer

        if (expense == null) {
            System.err.println("No se encontró el gasto con ID " + id);
            return false;
        }
        // Si el gasto existe, actualiza sus atributos con los nuevos valores recibidos como parámetros
        expense.setExpenseType(newType);
        expense.setMileage(newMileage);
        expense.setDate(newDate);
        expense.setAmount(newAmount);
        expense.setDescription(newDesc);
        // Llama al método 'updateExpense' del ExpenseDAO para guardar los cambios en la base de datos
        // Devuelve 'true' si la actualización fue exitosa o 'false' si falló
        return expenseDAO.updateExpense(expense);
    }

    public boolean deleteExpense(String id) throws SQLException {
        return expenseDAO.deleteExpense(id);
    }
}
