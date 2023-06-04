package library.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import library.publication.Books;

public class Database {

	private static String URL = "jdbc:mysql://localhost/library";
	private static String USER_NAME = "root";
	private static String PASSWORD = "b0rderless";
	
	private static Connection conn;
    private static Statement stmt;
    
    public static Connection getConnection(String dbURL, String userName, String password) {
		conn = null;
		try {
			conn = DriverManager.getConnection(dbURL, userName, password);
		} catch (Exception ex) {
			System.out.println("Can't connect");
			ex.printStackTrace();
		}
		return conn;
	}

    public Database() {
    	try {
    		conn = getConnection(URL, USER_NAME, PASSWORD);
    		stmt = conn.createStatement();
    	} catch (Exception ex) {
    			ex.printStackTrace();
    	}
    }

    void closeConnection()
    {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}

	Books GetBookbyID(int BookID) {

        Books CurrentBook = new Books();
        try {
            ResultSet rs = stmt.executeQuery("select * from Books where book_id='" + book_id + "'");
            while (rs.next()) {
                int quantity;
                String title, author, subject;
                book_id = rs.getInt(1);
                title = rs.getString(2);
                author = rs.getString(3);
                subject = rs.getString(4);
                quantity = rs.getInt(5);
                Books NewBook = new Books(book_id, title, author, subject, quantity);
                CurrentBook = NewBook;

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return CurrentBook;

    }
}