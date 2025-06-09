package controllers;

import models.UserDAO;
import modelsentities.User;
import utils.PasswordUtil;
import utils.UUIDUtil;
import java.sql.SQLException;
import java.util.UUID;

public class UserController {

    private UserDAO userDAO = new UserDAO();

<<<<<<< HEAD
   
=======
    // Registro de usuario
>>>>>>> eb9f5c15b45930b3971a7e2b1bcbd3fc19efb3ac
    public boolean register(String userName, String password) throws SQLException {
       
        if (userDAO.findByUserName1(userName) != null) {
            return false; 
        }

<<<<<<< HEAD
      
=======
        // Generar hash de contraseña y UUIDs
>>>>>>> eb9f5c15b45930b3971a7e2b1bcbd3fc19efb3ac
        String hashedPassword = PasswordUtil.hashPassword1(password);
        String userId = UUID.randomUUID().toString();
        String uuid = UUIDUtil.generateUUID();

<<<<<<< HEAD
       
        User newUser = new User(userId, userName, hashedPassword, uuid);

      
        return userDAO.registerUser1(newUser);
    }

 
=======
        // Crear objeto usuario con todos los campos
        User newUser = new User(userId, userName, hashedPassword, uuid);

        // Insertar en BD
        return userDAO.registerUser1(newUser);
    }

    // Inicio de sesión
>>>>>>> eb9f5c15b45930b3971a7e2b1bcbd3fc19efb3ac
    public User login(String userName, String password) throws SQLException {
        String hashedPassword = PasswordUtil.hashPassword1(password);
        return userDAO.login1(userName, hashedPassword);
        
    }
}
