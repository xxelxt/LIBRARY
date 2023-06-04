package library.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import library.publication.Publication;
import library.publication.Books;

public class Database {

    private static String URL = "jdbc:mysql://localhost/library";
    private static String USER_NAME = "root";
    private static String PASSWORD = "b0rderless";

    private Connection conn;

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

    Books GetBookByID(String BookID) {
        Books currentBook = null;
        try {
            String sql = "SELECT p.PublicationID, p.Title, GROUP_CONCAT(a.AuthorName) AS Authors, p.ReleaseDate, p.Country, p.Quantity, b.Category, b.Reissue, pb.PublisherName " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "INNER JOIN BookAuthors ba ON b.BookID = ba.BookID " +
                    "INNER JOIN Authors a ON ba.AuthorID = a.AuthorID " +
                    "INNER JOIN Publishers pb ON b.PublisherID = pb.PublisherID " +
                    "WHERE b.BookID LIKE ? " + 
                    "GROUP BY p.PublicationID, p.Title, p.ReleaseDate, p.Country, p.Quantity, b.Category, b.Reissue, pb.PublisherName";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + BookID + "%");
            
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                String publicationID = rs.getString(1);
                String title = rs.getString(2);
                String authorsString = rs.getString(3);
                ArrayList<String> authors = new ArrayList<>(Arrays.asList(authorsString.split(", ")));
    
                Date releaseDate = rs.getDate(4);
                String country = rs.getString(5);
                int quantity = rs.getInt(6);
    
                String category = rs.getString(7);
                int reissue = rs.getInt(8);
                String publisher = rs.getString(9);
    
                Books newBook = new Books(publicationID, title, authors, releaseDate, country, quantity, category, reissue, publisher);
    
                currentBook = newBook;
            }
    
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return currentBook;
    }

    ArrayList<Books> loadAllBooks() {
        ArrayList<Books> currentBooks = new ArrayList<>();
        try {
            String sql = "SELECT p.PublicationID, p.Title, GROUP_CONCAT(a.AuthorName) AS Authors, p.ReleaseDate, p.Country, p.Quantity, b.Category, b.Reissue, pb.PublisherName " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "INNER JOIN BookAuthors ba ON b.BookID = ba.BookID " +
                    "INNER JOIN Authors a ON ba.AuthorID = a.AuthorID " +
                    "INNER JOIN Publishers pb ON b.PublisherID = pb.PublisherID " +
                    "GROUP BY p.PublicationID, p.Title, p.ReleaseDate, p.Country, p.Quantity, b.Category, b.Reissue, pb.PublisherName";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                String publicationID = rs.getString(1);
                String title = rs.getString(2);
                String authorsString = rs.getString(3);
                ArrayList<String> authors = new ArrayList<>(Arrays.asList(authorsString.split(", ")));
    
                Date releaseDate = rs.getDate(4);
                String country = rs.getString(5);
                int quantity = rs.getInt(6);
    
                String category = rs.getString(7);
                int reissue = rs.getInt(8);
                String publisher = rs.getString(9);
    
                Books newBook = new Books(publicationID, title, authors, releaseDate, country, quantity, category, reissue, publisher);
                currentBooks.add(newBook);
            }
    
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return currentBooks;
    }
    
    String GetTitleofBook(String BookID) {
        String title = "";
        try {
            String sql = "SELECT p.Title " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "WHERE b.BookID LIKE ?";
        
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + BookID + "%");
            ResultSet rs = pstmt.executeQuery();
        
            while (rs.next()) {
                title = rs.getString(1);
            }
        
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return title;
    }

    Date GetReleaseDateOfBook(String BookID) {
        Date releaseDate = null;
        try {
            String sql = "SELECT p.ReleaseDate " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "WHERE b.BookID LIKE ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + BookID + "%");
            
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                releaseDate = rs.getDate(1);
            }
    
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return releaseDate;
    }

    String GetCountryofBook(String BookID) {
        String country = "";
        try {
            String sql = "SELECT p.Country " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "WHERE b.BookID LIKE ?";
        
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + BookID + "%");
            ResultSet rs = pstmt.executeQuery();
        
            while (rs.next()) {
                country = rs.getString(1);
            }
        
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return country;
    }
    
    int GetQuantityofBook(String BookID) {
        int quantity = -1;
        try {
            String sql = "SELECT p.Quantity " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "WHERE b.BookID LIKE ?";
        
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + BookID + "%");
            ResultSet rs = pstmt.executeQuery();
        
            while (rs.next()) {
                quantity = rs.getInt(1);
            }
        
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return quantity;
    }
    
    String GetCategoryofBook(String BookID) {
        String category = "";
        try {
            String sql = "SELECT b.Category " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "WHERE b.BookID LIKE ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + BookID + "%");
            
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                category = rs.getString(1);
            }
    
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return category;
    }

    int GetReissueofBook(String BookID) {
        int reissue = -1;
        try {
            String sql = "SELECT b.Reissue " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "WHERE b.BookID LIKE ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + BookID + "%");
            
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                reissue = rs.getInt(1);
            }
    
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return reissue;
    }
    
    String GetPublisherofBook(String BookID) {
        String publisher = "";
        try {
            String sql = "SELECT pb.PublisherName " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "INNER JOIN Publishers pb ON b.PublisherID = pb.PublisherID " +
                    "WHERE b.BookID LIKE ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + BookID + "%");
            
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                publisher = rs.getString(1);
            }
    
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return publisher;
    }

    void updateBookTitle(String bookID, String newTitle) {
        try {
            String sql = "UPDATE Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "SET p.Title = ? " +
                    "WHERE b.BookID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newTitle);
            pstmt.setString(2, bookID);
    
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void updateBookReleaseDate(String bookID, Date newReleaseDate) {
        try {
            String sql = "UPDATE Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "SET p.ReleaseDate = ? " +
                    "WHERE b.BookID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(newReleaseDate.getTime()));
            pstmt.setString(2, bookID);
    
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void updateBookCountry(String bookID, String newCountry) {
        try {
            String sql = "UPDATE Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "SET p.Country = ? " +
                    "WHERE b.BookID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newCountry);
            pstmt.setString(2, bookID);
    
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void updateBookQuantity(String BookID, int newQuantity) {
        try {
            String sql = "UPDATE Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "SET p.Quantity = ? " +
                    "WHERE b.BookID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newQuantity);
            pstmt.setString(2, BookID);
            
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    void updateBookCategory(String bookID, String newCategory) {
        try {
            String sql = "UPDATE Books " +
                    "SET Category = ? " +
                    "WHERE BookID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newCategory);
            pstmt.setString(2, bookID);
    
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void updateBookReissue(String bookID, int newReissue) {
        try {
            String sql = "UPDATE Books " +
                    "SET Reissue = ? " +
                    "WHERE BookID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newReissue);
            pstmt.setString(2, bookID);
    
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void updateBookPublisher(String bookID, String publisherName) {
        try {
            String sql = "UPDATE Books b " +
                         "INNER JOIN Publishers pb ON b.PublisherID = pb.PublisherID " +
                         "SET b.PublisherID = pb.PublisherID " +
                         "WHERE b.BookID = ? AND pb.PublisherName = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookID);
            pstmt.setString(2, publisherName);
    
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}