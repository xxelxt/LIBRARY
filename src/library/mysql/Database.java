package library.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import library.publication.Books;

public class Database {

    private static String URL = "jdbc:mysql://localhost/library";
    private static String USER_NAME = "root";
    private static String PASSWORD = "b0rderless";

    private Connection conn;
    private Statement stmt;

    public Connection getConnection(String dbURL, String userName, String password) {
        Connection conn = null;
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

    void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    Books GetBookByID(int bookID) {
        Books currentBook = null;
        try {
            ResultSet rs = stmt.executeQuery(
                    "SELECT p.PublicationID, p.Title, GROUP_CONCAT(a.AuthorName) AS Authors, p.ReleaseDate, p.Country, p.Quantity, b.Category, b.Reissue, pb.PublisherName " +
                            "FROM Publications p " +
                            "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                            "INNER JOIN BookAuthors ba ON b.BookID = ba.BookID " +
                            "INNER JOIN Authors a ON ba.AuthorID = a.AuthorID " +
                            "INNER JOIN Publishers pb ON b.PublisherID = pb.PublisherID " +
                            "WHERE b.BookID = " + bookID + " " +
                            "GROUP BY p.PublicationID, p.Title, p.ReleaseDate, p.Country, p.Quantity, b.Category, b.Reissue, pb.PublisherName");

            while (rs.next()) {
                String publicationID = rs.getString("PublicationID");
                String title = rs.getString("Title");
                String authorsString = rs.getString("Authors");
                ArrayList<String> authors = new ArrayList<>(Arrays.asList(authorsString.split(",")));
                Date releaseDate = rs.getDate("ReleaseDate");
                String country = rs.getString("Country");
                int quantity = rs.getInt("Quantity");
                String category = rs.getString("Category");
                int reissue = rs.getInt("Reissue");
                String publisher = rs.getString("PublisherName");

                currentBook = new Books(publicationID, title, releaseDate, country, quantity, authors, category, reissue, publisher);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return currentBook;
    }
}
