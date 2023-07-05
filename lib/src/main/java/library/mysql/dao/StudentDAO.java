package library.mysql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.mysql.DatabaseLayer;
import library.user.Student;
import library.user.User;

public class StudentDAO {

	private UserDAO userDAO = new UserDAO();

	public List<Student> loadAllStudents() throws SQLException {
        List<Student> studentList = new ArrayList<>();

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

            studentList.add(student);
        }

        rs.close();
        pstmt.close();

        return studentList;
    }

	public Student loadStudent(User user) throws SQLException {
		Student student = null;

        String sql = "SELECT StudentID, Name, ClassName, Gender, Email, Phone, Address, FineStatus, Fine "
        		+ "FROM Students S "
        		+ "INNER JOIN Users U ON S.Username = U.Username "
        		+ "WHERE S.Username = ?";

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

        return student;
    }

	public void addStudent(String StudentID, String Name, String ClassName, String Username, String Password, boolean Gender,
            String Email, String Phone, String Address, boolean FineStatus, int Fine) throws SQLException {
		String userstd = userDAO.addUser(Username, Password, 3);

		String sql = "INSERT INTO Students (StudentID, Name, ClassName, Username, Gender, Email, Phone, Address, FineStatus, Fine) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);

		pstmt.setString(1, StudentID);
		pstmt.setString(2, Name);
		pstmt.setString(3, ClassName);

		pstmt.setString(4, userstd);

		pstmt.setBoolean(5, Gender);
		pstmt.setString(6, Email);
		pstmt.setString(7, Phone);

		pstmt.setString(8, Address);
		pstmt.setBoolean(9, FineStatus);
		pstmt.setInt(10, Fine);

		pstmt.executeUpdate();
		pstmt.close();

		System.out.println("Added student.");
	}

	public String getUsernamebyStudentID(String StudentID) throws SQLException {
    	String username = "";

		String sql = "SELECT Username FROM Students WHERE StudentID = ?";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setString(1, StudentID);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            username = rs.getString(1);
        }
        rs.close();
        pstmt.close();

    	return username;
    }

    public void deleteStudent(String StudentID) throws SQLException {
        userDAO.deleteUser(getUsernamebyStudentID(StudentID));
    }

	public void updateStudent(Student std) throws SQLException {
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
	}

	public void addToStudentFine(String studentID, long daysDue) throws SQLException {
        String sql = "UPDATE Students S "
        		+ "SET S.Fine = S.Fine + 25000 * ?, "
        		+ "S.FineStatus = TRUE "
        		+ "WHERE S.StudentID = ?";

        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setLong(1, daysDue);
        pstmt.setString(2, studentID);
        pstmt.executeUpdate();
        pstmt.close();

        System.out.println("Updated student fine: " + studentID);
	}

	public String getStudentNamebyStudentID(String studentID) throws SQLException {
    	String name = "";

    	String sql = "SELECT Name FROM Students WHERE StudentID = ?";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setString(1, studentID);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            name = rs.getString(1);
        }
        rs.close();
        pstmt.close();

    	return name;
    }

}
