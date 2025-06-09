package models;

import utils.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelsentities.Expense;
import modelsentities.Expense.ExpenseType;

public class ExpenseDAO {

    public boolean addExpense(Expense expense) throws SQLException {
        String sql = "INSERT INTO expenses(expense_id, car_id, expense_type, mileage, date, amount, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, expense.getExpenseId());
            ps.setString(2, expense.getCarId());
            ps.setString(3, expense.getExpenseType().name());
            ps.setInt(4, expense.getMileage());
            ps.setDate(5, Date.valueOf(expense.getDate()));
            ps.setDouble(6, expense.getAmount());
            ps.setString(7, expense.getDescription());
            return ps.executeUpdate() == 1;
        }
    }

    public List<Expense> getExpensesByCarId(String carId, Integer yearFilter, LocalDate dateFilter, Integer mileageFilter) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM expenses WHERE car_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(carId);

        if (yearFilter != null) {
            sql.append(" AND YEAR(date) = ?");
            params.add(yearFilter);
        }
        if (dateFilter != null) {
            sql.append(" AND date = ?");
            params.add(Date.valueOf(dateFilter));
        }
        if (mileageFilter != null) {
            sql.append(" AND mileage = ?");
            params.add(mileageFilter);
        }

        List<Expense> expenses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof Integer) ps.setInt(i + 1, (Integer) p);
                else if (p instanceof String) ps.setString(i + 1, (String) p);
                else if (p instanceof Date) ps.setDate(i + 1, (Date) p);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                expenses.add(new Expense(
                        rs.getString("expense_id"),
                        rs.getString("car_id"),
                        ExpenseType.valueOf(rs.getString("expense_type")),
                        rs.getInt("mileage"),
                        rs.getDate("date").toLocalDate(),
                        rs.getDouble("amount"),
                        rs.getString("description")
                ));
            }
        }
        return expenses;
    }

    public Expense getExpenseById(String id) {
        String sql = "SELECT * FROM expenses WHERE expense_id = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Expense(
                        rs.getString("expense_id"),
                        rs.getString("car_id"),
                        ExpenseType.valueOf(rs.getString("expense_type")),
                        rs.getInt("mileage"),
                        rs.getDate("date").toLocalDate(),
                        rs.getDouble("amount"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateExpense(Expense expense) {
        String sql = "UPDATE expenses SET expense_type = ?, mileage = ?, date = ?, amount = ?, description = ? WHERE expense_id = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, expense.getExpenseType().name());
            ps.setInt(2, expense.getMileage());
            ps.setDate(3, Date.valueOf(expense.getDate()));
            ps.setDouble(4, expense.getAmount());
            ps.setString(5, expense.getDescription());
            ps.setString(6, expense.getExpenseId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteExpense(String expenseId) {
        String sql = "DELETE FROM expenses WHERE expense_id = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, expenseId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
