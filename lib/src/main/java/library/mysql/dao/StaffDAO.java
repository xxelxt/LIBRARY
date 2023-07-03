package library.mysql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.mysql.DatabaseLayer;
import library.user.Staff;
import library.user.User;

public class StaffDAO {

	private UserDAO userDAO = new UserDAO();

	public List<Staff> loadAllStaff() throws SQLException {
        List<Staff> staffList = new ArrayList<>();

        String sql = "SELECT StaffID, Name, Gender, Email, Phone, Address, Position, S.Username, U.Password "
        		+ "FROM Staff S "
        		+ "INNER JOIN Users U ON S.Username = U.Username";

        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
        	User user = userDAO.getUserfromUsername(rs.getString(8));

        	Staff staff = new Staff(
        			rs.getInt(1),
        			rs.getString(2),
        			rs.getBoolean(3),
        			rs.getString(4),
        			rs.getString(5),
        			rs.getString(6),
        			rs.getString(7),
        			user
        	);

            staffList.add(staff);
        }

        rs.close();
        pstmt.close();

        return staffList;
    }

	public Staff loadStaff(User user) throws SQLException {
		Staff staff = null;

		String sql = "SELECT StaffID, Name, Gender, Email, Phone, Address, Position "
        		+ "FROM Staff S "
        		+ "INNER JOIN Users U ON S.Username = U.Username "
        		+ "WHERE S.Username = ?";

        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setString(1, user.getUsername());
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
        	staff = new Staff(
        			rs.getInt(1),
        			rs.getString(2),
        			rs.getBoolean(3),
        			rs.getString(4),
        			rs.getString(5),
        			rs.getString(6),
        			rs.getString(7),
        			user
        	);
        }

        rs.close();
        pstmt.close();

        return staff;
    }

	public void addStaff(String Name, String Username, String Password, boolean Gender,
            String Email, String Phone, String Address, String Position) throws SQLException {
		String userstf = "";

		if (Position.equals("Thủ thư")) {
			userstf = userDAO.addUser(Username, Password, 1);
		} else {
			userstf = userDAO.addUser(Username, Password, 2);
		}

		String sql = "INSERT INTO Staff (Name, Username, Gender, Email, Phone, Address, Position) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);

		pstmt.setString(1, Name);
		pstmt.setString(2, userstf);
		pstmt.setBoolean(3, Gender);

		pstmt.setString(4, Email);
		pstmt.setString(5, Phone);
		pstmt.setString(6, Address);
		pstmt.setString(7, Position);

		pstmt.executeUpdate();
		pstmt.close();

		System.out.println("Added staff.");
	}

	public String getUsernamebyStaffID(Integer StaffID) throws SQLException {
    	String username = "";

    	String sql = "SELECT Username FROM Staff WHERE StaffID = ?";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setInt(1, StaffID);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            username = rs.getString(1);
        }
        rs.close();
        pstmt.close();

    	return username;
    }

    public void deleteStaff(Integer StaffID) throws SQLException  {
        userDAO.deleteUser(getUsernamebyStaffID(StaffID));
    }

	public void updateStaff(Staff stf) throws Exception {
		String sql = "UPDATE Staff "
        		+ "SET "
        		+ "Name = ?, "
        		+ "Gender = ?, "
        		+ "Address = ?, "
        		+ "Email = ?, "
        		+ "Phone = ? "
        		+ "WHERE StaffID = ?";

        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setString(1, stf.getName());
        pstmt.setBoolean(2, stf.getGender());
        pstmt.setString(3, stf.getAddress());
        pstmt.setString(4, stf.getEmail());
        pstmt.setString(5, stf.getPhone());
        pstmt.setInt(6, stf.getStaffID());

        pstmt.executeUpdate();
        pstmt.close();

        System.out.println("Updated staff.");
	}

}
