package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.DBConnection;

public class OwnershipDAO {

	public static boolean existsOwnership(String userId, String carId) throws SQLException {
	    String sql = "SELECT COUNT(*) FROM ownerships WHERE user_id = ? AND car_id = ?";
	    try (Connection conn = DBConnection.getConnection1();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, userId);
	        stmt.setString(2, carId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    }
	    return false;
	}

	
}
