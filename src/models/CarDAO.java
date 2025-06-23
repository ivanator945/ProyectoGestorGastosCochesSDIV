package models;




import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import modelsentities.Car;

public class CarDAO {

    public boolean createCar(Car car, String ownerId) throws SQLException {
        String sqlCar = "INSERT INTO cars(car_id, brand, model, license_plate, year) VALUES (?, ?, ?, ?, ?)";
        String sqlOwnership = "INSERT INTO ownerships(user_id, car_id) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement psCar = null;
        PreparedStatement psOwnership = null;
        try {
            conn = DBConnection.getConnection1();
            // Desactiva el auto-commit para controlar manualmente la transacción
            conn.setAutoCommit(false);
            
            // Prepara la sentencia para insertar el coche
            psCar = conn.prepareStatement(sqlCar);
            psCar.setString(1, car.getCarId());
            psCar.setString(2, car.getBrand());
            psCar.setString(3, car.getModel());
            psCar.setString(4, car.getLicensePlate());
            psCar.setInt(5, car.getYear());
            psCar.executeUpdate();
            // Prepara la sentencia para insertar la relación de propiedad
            psOwnership = conn.prepareStatement(sqlOwnership);
            psOwnership.setString(1, ownerId);
            psOwnership.setString(2, car.getCarId());
            psOwnership.executeUpdate();
            // Si ambas inserciones fueron exitosas, confirma la transacción
            conn.commit();
            
            return true;
        } catch (SQLException e) {
        	 // Si ocurre un error, revierte la transacción para no dejar datos a medias
            if (conn != null) conn.rollback();
            throw e;
        } finally {
        	// Cierra los recursos abiertos en orden inverso
            if (psCar != null) psCar.close();
            if (psOwnership != null) psOwnership.close();
            if (conn != null) conn.setAutoCommit(true);// Restaura el auto-commit
            if (conn != null) conn.close();	// Cierra la conexión
        }
    }

    // Obtener coches del usuario (todos donde sea propietario)
    public List<Car> getCarsByUserId(String userId) throws SQLException {
        String sql = "SELECT c.* FROM cars c JOIN ownerships o ON c.car_id = o.car_id WHERE o.user_id = ?";
        // Crea una lista vacía donde se guardarán los coches encontrados
        List<Car> cars = new ArrayList<>();
        
        // Intenta ejecutar la consulta usando una conexión a la base de datos
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);  // Sustituye el '?' de la consulta por el userId recibido como parámetro
            ResultSet rs = ps.executeQuery(); // Ejecuta la consulta y obtiene el resultado
            // Recorre el resultado fila a fila
            while (rs.next()) {
            	 // Por cada fila, crea un objeto Car con los datos obtenidos de la consulta
                cars.add(new Car(rs.getString("car_id"), rs.getString("brand"), rs.getString("model"),
                        rs.getString("license_plate"), rs.getInt("year")));
            }
        }
        return cars;
    }

    // Añadir otro propietario a un coche
    public boolean addOwnerToCar(String carId, String userId) throws SQLException {
        String sql = "INSERT INTO ownerships(user_id, car_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, carId);
            return ps.executeUpdate() == 1;
        }
    }

    // Editar coche
    public boolean updateCar(Car car) throws SQLException {
        String sql = "UPDATE cars SET brand = ?, model = ?, license_plate = ?, year = ? WHERE car_id = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getLicensePlate());
            ps.setInt(4, car.getYear());
            ps.setString(5, car.getCarId());
            return ps.executeUpdate() == 1;
        }
    }

    // Eliminar coche
    public boolean deleteCar(String carId) throws SQLException {
        String sql = "DELETE FROM cars WHERE car_id = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, carId);
            return ps.executeUpdate() == 1;
        }
    }

    public Car getCarById(String carId) throws SQLException {
        String sql = "SELECT * FROM cars WHERE car_id = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, carId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Car(
                    rs.getString("car_id"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("license_plate"),
                    rs.getInt("year")
                );
            }
        }
        return null;
    }

}
