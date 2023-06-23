package library.mysql.dao;

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
            String sql = "SELECT StudentID, Name, ClassName, Gender, Email, Phone, Address, FineStatus, Fine, S.Username, U.Password "
            		+ "FROM Students S "
            		+ "INNER JOIN Users U ON S.Username = U.Username";

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	User user = userDAO.getUserfromUsername(rs.getString(10));

            	Student student = new Student(
            			rs.getString(1),
            			rs.getString(2),
            			rs.getString(3),
            			rs.getBoolean(4),
            			rs.getString(5),
            			rs.getString(6),
            			rs.getString(7),
            			rs.getBoolean(8),
            			rs.getInt(9),
            			user
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
	
	public Student loadStudent(User user) {
		Student student = null;

        try {
            String sql = "SELECT StudentID, Name, ClassName, Gender, Email, Phone, Address, FineStatus, Fine "
            		+ "FROM Students S "
            		+ "INNER JOIN Users U ON S.Username = U.Username "
            		+ "WHERE S.Username = ?;";

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	student = new Student(
            			rs.getString(1),
            			rs.getString(2),
            			rs.getString(3),
            			rs.getBoolean(4),
            			rs.getString(5),
            			rs.getString(6),
            			rs.getString(7),
            			rs.getBoolean(8),
            			rs.getInt(9),
            			user
            	);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return student;
    }

	public boolean addStudent(String StudentID, String Name, String ClassName, String Username, String Password, boolean Gender,
            String Email, String Phone, String Address, boolean FineStatus, int Fine) {
		String userstd = userDAO.addUser(Username, Password, 3);

		try {
			 String studentSql = "INSERT INTO Students (StudentID, Name, ClassName, Username, Gender, Email, Phone, Address, FineStatus, Fine) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 PreparedStatement student_pstmt = DatabaseLayer.prepareStatement(studentSql);

			 student_pstmt.setString(1, StudentID);
			 student_pstmt.setString(2, Name);
			 student_pstmt.setString(3, ClassName);

			 student_pstmt.setString(4, userstd);

			 student_pstmt.setBoolean(5, Gender);
			 student_pstmt.setString(6, Email);
			 student_pstmt.setString(7, Phone);

			 student_pstmt.setString(8, Address);
			 student_pstmt.setBoolean(9, FineStatus);
			 student_pstmt.setInt(10, Fine);

			 student_pstmt.executeUpdate();
			 student_pstmt.close();

			 System.out.println("Added student.");

		} catch (Exception e) {
			 System.out.println(e);
			 return false;
		}
		return true;
	}
	
	public String getUsernamebyStudentID(String StudentID) {
    	String username = "";
    	try {
    		String sql = "SELECT Username FROM Students WHERE StudentID = ?";
            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            pstmt.setString(1, StudentID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                username = rs.getString(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
    	}

    	return username;
    }
	

    public boolean deleteStudent(String StudentID) {
        return userDAO.deleteUser(getUsernamebyStudentID(StudentID));
    }
	
	public boolean deleteStudent2(String sID) {
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

	        System.out.println("Deleted student and user account.");

	    } catch (Exception e) {
	        System.out.println(e);
	        return false;
	    }
	    return true;
	}

	public void updateStudent(Student std) throws Exception {
		try {
			
            String sql = "UPDATE Students "
            		+ "SET "
            		+ "Name = ?, "
            		+ "Gender = ?, "
            		+ "Address = ?, "
            		+ "Email = ?, "
            		+ "Phone = ?, "
            		+ "ClassName = ?, "
            		+ "Fine = ?, "
            		+ "FineStatus = ? "
            		+ "WHERE StudentID = ?";

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            pstmt.setString(1, std.getName());
            pstmt.setBoolean(2, std.getGender());
            pstmt.setString(3, std.getAddress());
            pstmt.setString(4, std.getEmail());
            pstmt.setString(5, std.getPhone());
            pstmt.setString(6, std.getClassName());
            pstmt.setInt(7, std.getFine());
            pstmt.setBoolean(8, std.isFineStatus());
            pstmt.setString(9, std.getStudentID());

            pstmt.executeUpdate();
            pstmt.close();

            System.out.println("Updated student.");
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
	}

}
