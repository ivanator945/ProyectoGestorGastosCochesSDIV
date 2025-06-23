package models;

import utils.DBConnection;
import modelsentities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	 // se registra el usuario para poder acceder y se guarda en base de datos se controla mediante el user controler que viene aqui para ser registrado 
    public boolean registerUser1(User user) throws SQLException {
        String sql = "INSERT INTO users(user_id, user_name, password, UUid) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getUuid()); 
            return ps.executeUpdate() == 1;
        }
    }
    // accede a la base de datos para ver si estan correcta la contraseña y si lo exta accede 
    public User login1(String userName, String passwordHash) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_name = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, passwordHash);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getString("user_id"),
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("UUid")
                );
            }
        }
        return null;
    }
    // obtiene el id de los usuarios
    public User getUserById(String userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getString("user_id"),
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("UUid")
                );
            }
        }
        return null;
    }
    // selecciona los usuarios con el nombre ?
    public User findByUserName1(String userName) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_name = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getString("user_id"),
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("UUid")
                );
            }
        }
        return null;
    }
    // selecciona todos los usuarios
    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT user_id, user_name, UUid FROM users";
        List<User> users = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                    rs.getString("user_id"),
                    rs.getString("user_name"),
                    null, 
                    rs.getString("UUid")
                ));
            }
        }

        return users;
    }
    // verifica si existen los usuarios de id 
    public boolean existsUserId(String userId) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next(); 
        }
    }
    // mira lo usuarios de uuid
    public User findByUuid(String uuid) throws SQLException {
        String sql = "SELECT * FROM users WHERE UUid = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getString("user_id"),
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("UUid")
                );
            }
        }
        return null;
    }

}
