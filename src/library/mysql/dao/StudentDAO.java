package library.mysql.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import library.mysql.DatabaseLayer;
import library.user.Student;
import library.user.User;

public class StudentDAO {
	
	private UserDAO userDAO = new UserDAO();
	
	public List<Student> loadAllStudents() {
        List<Student> students = new ArrayList<>();

        try {
            String sql = "SELECT Name, Gender, Address, Email, Phone, Username, StudentID, ClassName, FineStatus, Fine " +
                         "FROM Students";

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	User user = userDAO.getUserfromUsername(rs.getString(6));
            	
            	Student student = new Student(
            			rs.getString(1),
            			rs.getBoolean(2),
            			rs.getString(3),
            			rs.getString(4),
            			rs.getString(5),
            			user,
            			rs.getString(7),
            			rs.getString(8),
            			rs.getBoolean(9),
            			rs.getDouble(10)
            	);
                students.add(student);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return students;
    }
	
	public boolean addStudent(String Username, String Password, String StudentID, String Name, boolean Gender,
            String Address, String Email, String Phone, String ClassName, double Fine, boolean FineStatus) {
		try {
			 String usersSql = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
			 PreparedStatement users_pstmt = DatabaseLayer.prepareStatement(usersSql);
			 users_pstmt.setString(1, Username);
			 users_pstmt.setString(2, Password);
			 users_pstmt.executeUpdate();
			 users_pstmt.close();
			
			 String studentSql = "INSERT INTO Students VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 PreparedStatement student_pstmt = DatabaseLayer.prepareStatement(studentSql);
			
			 student_pstmt.setString(1, StudentID);
			 student_pstmt.setString(2, Username);
			 student_pstmt.setString(3, Name);
			
			 student_pstmt.setBoolean(4, Gender);
			 student_pstmt.setString(5, Address);
			 student_pstmt.setString(6, Email);
			 student_pstmt.setString(7, Phone);
			
			 student_pstmt.setString(8, ClassName);
			 student_pstmt.setDouble(9, Fine);
			 student_pstmt.setBoolean(10, FineStatus);
			
			 student_pstmt.executeUpdate();
			 student_pstmt.close();
			
		} catch (Exception e) {
			 System.out.println(e);
			 return false;
		}
		return true;
	}

	public boolean deleteStudent(String sID) {
	    try {
	        String usernameStudentSql = "SELECT Username FROM Students WHERE StudentID = ?";
	        PreparedStatement usernameStmt = DatabaseLayer.prepareStatement(usernameStudentSql);
	        usernameStmt.setString(1, sID);
	        ResultSet rs = usernameStmt.executeQuery();
	        
	        if (!rs.next()) {
	            System.out.println("Can't find student with ID: " + sID);
	            rs.close();
	            usernameStmt.close();
	            return false;
	        }
	        
	        String username = rs.getString("Username");
	        rs.close();
	        usernameStmt.close();

	        String deleteStudentSql = "DELETE FROM Students WHERE StudentID = ?";
	        PreparedStatement deleteStmt = DatabaseLayer.prepareStatement(deleteStudentSql);
	        deleteStmt.setString(1, sID);

	        int rowsAffected = deleteStmt.executeUpdate();
	        deleteStmt.close();

	        if (rowsAffected == 0) {
	            System.out.println("No student found with the provided ID.");
	            return false;
	        }

	        String deleteUserSql = "DELETE FROM Users WHERE Username = ?";
	        PreparedStatement deleteUsersStmt = DatabaseLayer.prepareStatement(deleteUserSql);
	        deleteUsersStmt.setString(1, username);
	        deleteUsersStmt.executeUpdate();
	        deleteUsersStmt.close();

	    } catch (Exception e) {
	        System.out.println(e);
	        return false;
	    }
	    return true;
	}

}
