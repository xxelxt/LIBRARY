package library.mysql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import library.mysql.DatabaseLayer;
import library.user.Staff;
import library.user.User;

public class StaffDAO {

	private UserDAO userDAO = new UserDAO();

	public List<Staff> loadAllStaff() {
        List<Staff> staffList = new ArrayList<>();

        try {
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
        } catch (Exception e) {
            System.out.println(e);
        }

        return staffList;
    }

	public boolean addStaff(String Name, String Username, String Password, boolean Gender,
            String Email, String Phone, String Address, String Position) {
		String userstf = "";
		if (Position.equals("Thủ thư")) {
			userstf = userDAO.addUser(Username, Password, 1);
		} else userstf = userDAO.addUser(Username, Password, 2);

		try {
			 String studentSql = "INSERT INTO Staff (Name, Username, Gender, Email, Phone, Address, Position) VALUES (?, ?, ?, ?, ?, ?, ?)";
			 PreparedStatement student_pstmt = DatabaseLayer.prepareStatement(studentSql);

			 student_pstmt.setString(1, Name);
			 student_pstmt.setString(2, userstf);
			 student_pstmt.setBoolean(3, Gender);

			 student_pstmt.setString(4, Email);
			 student_pstmt.setString(5, Phone);
			 student_pstmt.setString(6, Address);
			 student_pstmt.setString(7, Position);

			 student_pstmt.executeUpdate();
			 student_pstmt.close();

			 System.out.println("Added staff.");

		} catch (Exception e) {
			 System.out.println(e);
			 return false;
		}
		return true;
	}

	public boolean deleteStaff(Integer sID) {
	    try {
	        String usernameStaffSql = "SELECT Username FROM Staff WHERE StaffID = ?";
	        PreparedStatement usernameStmt = DatabaseLayer.prepareStatement(usernameStaffSql);
	        usernameStmt.setInt(1, sID);
	        ResultSet rs = usernameStmt.executeQuery();

	        if (!rs.next()) {
	            System.out.println("Can't find staff with ID: " + sID);
	            rs.close();
	            usernameStmt.close();
	            return false;
	        }

	        String username = rs.getString("Username");
	        rs.close();
	        usernameStmt.close();

	        String deleteStaffSql = "DELETE FROM Staff WHERE StaffID = ?";
	        PreparedStatement deleteStmt = DatabaseLayer.prepareStatement(deleteStaffSql);
	        deleteStmt.setInt(1, sID);

	        int rowsAffected = deleteStmt.executeUpdate();
	        deleteStmt.close();

	        if (rowsAffected == 0) {
	            System.out.println("No staff found with the provided ID.");
	            return false;
	        }

	        String deleteUserSql = "DELETE FROM Users WHERE Username = ?";
	        PreparedStatement deleteUsersStmt = DatabaseLayer.prepareStatement(deleteUserSql);
	        deleteUsersStmt.setString(1, username);
	        deleteUsersStmt.executeUpdate();
	        deleteUsersStmt.close();

	        System.out.println("Deleted staff and user account.");

	    } catch (Exception e) {
	        System.out.println(e);
	        return false;
	    }
	    return true;
	}

	public boolean updateStaff(Staff stf) {
		try {
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
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

		return true;
	}

}
