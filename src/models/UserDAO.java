package models;

import utils.DBConnection;
import modelsentities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

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
    
    public boolean existsUserId(String userId) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next(); 
        }
    }
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
