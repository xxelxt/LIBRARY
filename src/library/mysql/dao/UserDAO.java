package library.mysql.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;

import library.mysql.DatabaseLayer;
import library.user.User;

public class UserDAO {
	public User getUserfromUsername(String username) {
		User user = null;
        try {
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

        } catch (Exception e) {
            System.out.println(e);
        }

        return user;
	}


	public String getPasswordfromUsername(String username) {
        String password = "";
        try {
            String sql = "SELECT Password FROM Users WHERE Username = ?";
            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                password = rs.getString(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return password;
	}
}
