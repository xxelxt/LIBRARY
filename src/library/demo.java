package library;

import java.sql.*;

public class demo {
	private static String URL = "jdbc:mysql://localhost/library";
	private static String USER_NAME = "root";
	private static String PASSWORD = "b0rderless";
	
	public static void main(String args[]) {
		try {
			Connection conn = getConnection(URL, USER_NAME, PASSWORD);
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from book");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2));
			}
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public demo() {
		
	}
	
	public static Connection getConnection(String dbURL, String userName, String password) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dbURL, userName, password);
		} catch (Exception ex) {
			System.out.println("Can't connect");
			ex.printStackTrace();
		}
		return conn;
	}
}
