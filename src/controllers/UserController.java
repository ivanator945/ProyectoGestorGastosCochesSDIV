package controllers;

import models.UserDAO;
import modelsentities.User;
import utils.PasswordUtil;
import utils.UUIDUtil;
import java.sql.SQLException;
import java.util.UUID;

public class UserController {

    private UserDAO userDAO = new UserDAO();


    public boolean register(String userName, String password) throws SQLException {
       
        if (userDAO.findByUserName1(userName) != null) {
            return false; 
        }

        String hashedPassword = PasswordUtil.hashPassword1(password);
        String userId = UUID.randomUUID().toString();
        String uuid = UUIDUtil.generateUUID();     
        User newUser = new User(userId, userName, hashedPassword, uuid);

        return userDAO.registerUser1(newUser);
    }



    public User login(String userName, String password) throws SQLException {
        String hashedPassword = PasswordUtil.hashPassword1(password);
        return userDAO.login1(userName, hashedPassword);
        
    }
    }
    
