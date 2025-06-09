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
        Car car = carDAO.getCarById(carId);
        if (car == null) {
            System.err.println("Error: el coche con ID " + carId + " no existe.");
            return false;
        }

        String expenseId = UUIDUtil.generateUUID();
        Expense expense = new Expense(expenseId, carId, type, mileage, date, amount, description);
        return expenseDAO.addExpense(expense);
    }

    public List<Expense> getExpenses(String carId, Integer year, LocalDate date, Integer mileage) throws SQLException {
        return expenseDAO.getExpensesByCarId(carId, year, date, mileage);
    }

    public Expense getExpenseById(String id) throws SQLException {
        return expenseDAO.getExpenseById(id);
    }

    public boolean updateExpense(String id, ExpenseType newType, int newMileage, LocalDate newDate, double newAmount, String newDesc) throws SQLException {
        Expense expense = expenseDAO.getExpenseById(id);
        if (expense == null) {
            System.err.println("No se encontró el gasto con ID " + id);
            return false;
        }

        expense.setExpenseType(newType);
        expense.setMileage(newMileage);
        expense.setDate(newDate);
        expense.setAmount(newAmount);
        expense.setDescription(newDesc);

        return expenseDAO.updateExpense(expense);
    }

    public boolean deleteExpense(String id) throws SQLException {
        return expenseDAO.deleteExpense(id);
    }
}
