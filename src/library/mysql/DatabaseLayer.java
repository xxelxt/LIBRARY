package library.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import library.central.Borrow;
import library.publication.Books;
import library.publication.PrintMedia;
import library.publication.Publication;
import library.user.Clerk;
import library.user.Librarian;
import library.user.Student;

public class DatabaseLayer {

    private static final String URL = "jdbc:mysql://localhost/library";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "b0rderless";

    private static Connection conn;

    public static void getConnection() throws Exception {
        try {
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (Exception ex) {
            System.out.println("Can't connect to DB");
            ex.printStackTrace();
            throw ex;
        }   
    }

    public static void closeConnection() throws Exception {
        try {
        	if (conn != null && !conn.isClosed()) {
        		conn.close();
        	}
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static PreparedStatement prepareStatement(String sql) {
    	PreparedStatement pstmt = null;
    	try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return pstmt;
    }
    
    public static PreparedStatement prepareStatement(String sql, Integer i) {
    	PreparedStatement pstmt = null;
    	try {
			pstmt = conn.prepareStatement(sql, i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return pstmt;
    }
}