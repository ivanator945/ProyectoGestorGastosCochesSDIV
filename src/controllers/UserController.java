package controllers;

import models.UserDAO;
import modelsentities.User;
import utils.PasswordUtil;
import utils.UUIDUtil;
import java.sql.SQLException;
import java.util.UUID;

public class UserController {

    private UserDAO userDAO = new UserDAO();

    // Registro de usuario
    public boolean register(String userName, String password) throws SQLException {
       
        if (userDAO.findByUserName1(userName) != null) {
            return false; 
        }

        // Generar hash de contraseña y UUIDs
        String hashedPassword = PasswordUtil.hashPassword1(password);
        String userId = UUID.randomUUID().toString();
        String uuid = UUIDUtil.generateUUID();

        // Crear objeto usuario con todos los campos
        User newUser = new User(userId, userName, hashedPassword, uuid);

        // Insertar en BD
        return userDAO.registerUser1(newUser);
    }

    // Inicio de sesión
    public User login(String userName, String password) throws SQLException {
        String hashedPassword = PasswordUtil.hashPassword1(password);
        return userDAO.login1(userName, hashedPassword);
        
    }
}
