package library.mysql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import library.mysql.DatabaseLayer;
import library.user.User;

public class UserDAO {

	public User getUserfromUsername(String username) throws SQLException {
		User user = null;

		String sql = "SELECT * FROM Users WHERE Username = ?";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setString(1, username);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            user = new User(
            	rs.getString(1),
            	rs.getString(2),
            	rs.getInt(3)
            );
        }
        rs.close();
        pstmt.close();

        return user;
	}


	public String getPasswordfromUsername(String username) throws SQLException {
        String password = "";

        String sql = "SELECT Password FROM Users WHERE Username = ?";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setString(1, username);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            password = rs.getString(1);
        }
        rs.close();
        pstmt.close();

        return password;
	}

	public String addUser(String username, String password, int type) throws SQLException {
		String sql = "INSERT INTO Users (Username, Password, Type) VALUES (?, ?, ?)";
		PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		pstmt.setInt(3, type);
		pstmt.executeUpdate();
		pstmt.close();

		return username;
	}

	public void deleteUser(String username) throws SQLException {
		String sql = "DELETE FROM Users WHERE Username = ?";
		PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
		pstmt.setString(1, username);
		pstmt.executeUpdate();
		pstmt.close();
	}

	public void updatePassword(User user) throws SQLException {
		String sql = "UPDATE Users "
        		+ "SET Password = ? "
        		+ "WHERE Username = ?";

		PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
		pstmt.setString(1, user.getPassword());
		pstmt.setString(2, user.getUsername());

		pstmt.executeUpdate();
		pstmt.close();

		System.out.println("Updated password.");
	}
}
