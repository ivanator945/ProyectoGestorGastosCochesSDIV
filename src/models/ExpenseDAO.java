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
     // Abre una conexión a la base de datos y prepara la sentencia
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
        	// Asigna los valores de cada campo del objeto Expense a los parámetros de la consulta
            ps.setString(1, expense.getExpenseId());
            ps.setString(2, expense.getCarId());
            ps.setString(3, expense.getExpenseType().name());
            ps.setInt(4, expense.getMileage());
            ps.setDate(5, Date.valueOf(expense.getDate()));
            ps.setDouble(6, expense.getAmount());
            ps.setString(7, expense.getDescription());
            // Ejecuta la consulta y devuelve true si se insertó exactamente una fila
            return ps.executeUpdate() == 1;
        }
    }

    public List<Expense> getExpensesByCarId(String carId, Integer yearFilter, LocalDate dateFilter, Integer mileageFilter) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM expenses WHERE car_id = ?");
        // Lista para almacenar los parámetros que se pasarán al PreparedStatement
        List<Object> params = new ArrayList<>();
        params.add(carId); // Agrega el ID del coche como primer parámetro (obligatorio)

        // Si el filtro de año no es nulo, agrega una condición para filtrar por año en la fecha
        if (yearFilter != null) {
            sql.append(" AND YEAR(date) = ?");
            params.add(yearFilter);  // Agrega el año como parámetro
        }
        // Si el filtro de fecha exacta no es nulo, agrega una condición para filtrar por fecha completa
        if (dateFilter != null) {
            sql.append(" AND date = ?");
            params.add(Date.valueOf(dateFilter)); // Convierte LocalDate a java.sql.Date y lo agrega como parámetro
        }
     // Si el filtro de kilometraje no es nulo, agrega una condición para filtrar por kilometraje
        if (mileageFilter != null) {
            sql.append(" AND mileage = ?");
            params.add(mileageFilter);// Agrega el kilometraje como parámetro
        }

        List<Expense> expenses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        	// Asigna los parámetros de forma dinámica a la consulta preparada
            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof Integer) ps.setInt(i + 1, (Integer) p);
                else if (p instanceof String) ps.setString(i + 1, (String) p);
                else if (p instanceof Date) ps.setDate(i + 1, (Date) p);
            }
            ResultSet rs = ps.executeQuery();// Ejecuta la consulta en la base de datos
            // Recorre cada fila del resultado
            while (rs.next()) {
            	 // Crea un nuevo objeto Expense con los datos de la fila y lo añade a la lista
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
        return expenses; // Devuelve la lista con todos los gastos encontrados
    }

    public Expense getExpenseById(String id) {
        String sql = "SELECT * FROM expenses WHERE expense_id = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id); // Asigna el ID del gasto al parámetro de la consulta
            ResultSet rs = ps.executeQuery(); // Ejecuta la consulta
            // Si existe un gasto con ese ID, crea y devuelve un objeto Expense con los datos
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
            e.printStackTrace(); // Si hay error SQL, lo imprime por consola
        }
        return null;
    }

    public boolean updateExpense(Expense expense) {
    	// Consulta SQL para actualizar los datos de un gasto existente según su ID
        String sql = "UPDATE expenses SET expense_type = ?, mileage = ?, date = ?, amount = ?, description = ? WHERE expense_id = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, expense.getExpenseType().name());
            ps.setInt(2, expense.getMileage());
            ps.setDate(3, Date.valueOf(expense.getDate()));
            ps.setDouble(4, expense.getAmount());
            ps.setString(5, expense.getDescription());
            ps.setString(6, expense.getExpenseId());
            // Ejecuta la actualización y devuelve true si se actualizó exactamente una fila
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();// En caso de error SQL, imprime la traza del error
            return false;     // Devuelve false si hubo algún problem
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
