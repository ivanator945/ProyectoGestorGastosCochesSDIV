package controllers;

import models.CarDAO;
import models.UserDAO;
import modelsentities.Car;
import modelsentities.User;
import utils.UUIDUtil;

import java.sql.SQLException;
import java.util.List;

public class CarController {
    private final CarDAO carDAO = new CarDAO();
    private final UserDAO userDAO = new UserDAO();
//crea un nuevo coche 
    public boolean createCar(String brand, String model, String licensePlate, int year, String ownerId) throws SQLException {
        if (year < 1900 || year > 2100) throw new IllegalArgumentException("Año fuera de rango válido");

       
        if (!userDAO.existsUserId(ownerId)) {
            throw new IllegalArgumentException("El usuario con ID " + ownerId + " no existe.");
        }

        String carId = UUIDUtil.generateUUID();
        Car car = new Car(carId, brand, model, licensePlate, year);
        return carDAO.createCar(car, ownerId);
    }
  //obtiene el id del usuario 
    public List<Car> getCarsByUserId(String userId) throws SQLException {
        return carDAO.getCarsByUserId(userId);
    }

    public boolean addOwner(String carId, String newOwnerUuid) throws SQLException {
    	// Busca al usuario en la base de datos a partir de su UUID (identificador universal único)
        User user = userDAO.findByUuid(newOwnerUuid);
        // Si no existe ningún usuario con ese UUID, lanza una excepción indicando que no se encontró el usuario
        if (user == null) {
            throw new IllegalArgumentException("El usuario con UUID " + newOwnerUuid + " no existe.");
        }
        // Si el usuario existe, añade al usuario (como nuevo propietario) al coche con el ID carId.
        // Para ello usa el ID interno del usuario (userId) que ha sido recuperado previamente.

        return carDAO.addOwnerToCar(carId, user.getUserId());
    }

    public boolean updateCar(Car car) throws SQLException {
        return carDAO.updateCar(car);
    }

    public boolean deleteCar(String carId) throws SQLException {
        return carDAO.deleteCar(carId);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

	public boolean ownershipExists(String newOwnerUuid, String carId) {
		
		return false;
	}
}
