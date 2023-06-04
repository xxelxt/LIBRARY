package library.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import library.publication.Publication;
import library.user.Student;
import library.publication.Books;
import library.publication.PrintMedia;

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

    // Publications

    public String getPublicationTitle(String PublicationID) {
        String title = "";
        try {
            String sql = "SELECT p.Title " +
                    "FROM Publications p " +
                    "WHERE p.PublicationID = ?";
        
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, PublicationID);
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

    public Date getPublicationReleaseDate(String PublicationID) {
        Date releaseDate = null;
        try {
            String sql = "SELECT p.ReleaseDate " +
                    "FROM Publications p " +
                    "WHERE p.PublicationID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, PublicationID);
            
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

    public String getPublicationCountry(String PublicationID) {
        String country = "";
        try {
            String sql = "SELECT p.Country " +
                    "FROM Publications p " +
                    "WHERE p.PublicationID = ?";
        
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, PublicationID);
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

    public boolean setPublicationTitle(String publicationID, String newTitle) {
        try {
            String sql = "UPDATE Publications p " +
                    "SET p.Title = ? " +
                    "WHERE p.PublicationID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newTitle);
            pstmt.setString(2, publicationID);
    
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean setPublicationReleaseDate(String publicationID, Date newReleaseDate) {
        try {
            String sql = "UPDATE Publications p " +
                    "SET p.ReleaseDate = ? " +
                    "WHERE p.PublicationID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(newReleaseDate.getTime()));
            pstmt.setString(2, publicationID);
    
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean setPublicationCountry(String publicationID, String newCountry) {
        try {
            String sql = "UPDATE Publications p " +
                    "SET p.Country = ? " +
                    "WHERE p.PublicationID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newCountry);
            pstmt.setString(2, publicationID);
    
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean setPublicationQuantity(String publicationID, int newQuantity) {
        try {
            String sql = "UPDATE Publications p " +
                    "SET p.Quantity = ? " +
                    "WHERE p.PublicationID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newQuantity);
            pstmt.setString(2, publicationID);
            
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    
    public int getPublicationQuantity(String PublicationID) {
        int quantity = -1;
        try {
            String sql = "SELECT p.Quantity " +
                    "FROM Publications p " +
                    "WHERE p.Publication = ?";
        
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, PublicationID);
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

    // Books

    public ArrayList<String> getBookAuthor(String BookID) {
        ArrayList<String> authors = new ArrayList<>();
        
        try {
            String sql = "SELECT a.AuthorName FROM Authors a " +
                    "INNER JOIN BookAuthors ba ON a.AuthorID = ba.AuthorID " +
                    "INNER JOIN Books b ON ba.BookID = b.BookID " +
                    "WHERE b.BookID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, BookID);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String authorName = rs.getString(1);
                authors.add(authorName);
            }
            
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return authors;
    }

    public Books getBookbyID(String BookID) {
        Books currentBook = null;
        
        try {
            String sql = "SELECT p.PublicationID, p.Title, GROUP_CONCAT(a.AuthorName) as Authors, p.ReleaseDate, p.Country, p.Quantity, b.Category, b.Reissue, pb.PublisherName " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "INNER JOIN BookAuthors ba ON b.BookID = ba.BookID " +
                    "INNER JOIN Authors a ON ba.AuthorID = a.AuthorID " +
                    "INNER JOIN Publishers pb ON b.PublisherID = pb.PublisherID " +
                    "WHERE b.BookID = ? " + 
                    "GROUP BY p.PublicationID, p.Title, p.ReleaseDate, p.Country, p.Quantity, b.Category, b.Reissue, pb.PublisherName";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, BookID);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String publicationID = rs.getString(1);
                String title = rs.getString(2);
                ArrayList<String> authors = getBookAuthor(publicationID);

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

    public ArrayList<Books> loadAllBooks() {
        ArrayList<Books> bookList = new ArrayList<>();
        try {
            String sql = "SELECT p.PublicationID, p.Title, GROUP_CONCAT(a.AuthorName) as Authors, p.ReleaseDate, p.Country, p.Quantity, b.Category, b.Reissue, pb.PublisherName " +
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
                ArrayList<String> authors = getBookAuthor(publicationID);
    
                Date releaseDate = rs.getDate(4);
                String country = rs.getString(5);
                int quantity = rs.getInt(6);
    
                String category = rs.getString(7);
                int reissue = rs.getInt(8);
                String publisher = rs.getString(9);
    
                Books Book = new Books(publicationID, title, authors, releaseDate, country, quantity, category, reissue, publisher);
                bookList.add(Book);
            }
    
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return bookList;
    }
    
    public String getBookCategory(String BookID) {
        String category = "";
        try {
            String sql = "SELECT b.Category " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "WHERE b.BookID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, BookID);
            
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

    public int getBookReissue(String BookID) {
        int reissue = -1;
        try {
            String sql = "SELECT b.Reissue " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "WHERE b.BookID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, BookID);
            
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
    
    public String getBookPublisher(String BookID) {
        String publisher = "";
        try {
            String sql = "SELECT pb.PublisherName " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "INNER JOIN Publishers pb ON b.PublisherID = pb.PublisherID " +
                    "WHERE b.BookID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, BookID);
            
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

    public boolean setBookCategory(String bookID, String newCategory) {
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
            return false;
        }
        return true;
    }

    public boolean setBookReissue(String bookID, int newReissue) {
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
            return false;
        }
        return true;
    }

    public boolean setBookPublisher(String bookID, String publisherName) {
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
            return false;
        }
        return true;
    }
    
    // Print medias

    public PrintMedia getPrintMediabyID(String printMediaID) {
        PrintMedia currentPrintMedia = null;

        try {
            String sql = "SELECT p.PublicationID, p.Title, p.ReleaseDate, p.Country, p.Quantity, pm.ReleaseNumber, pm.PrintType " +
                    "FROM Publications p " +
                    "INNER JOIN PrintMedia pm ON p.PublicationID = pm.PrintMediaID " +
                    "WHERE pm.PrintMediaID = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, printMediaID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String publicationID = rs.getString(1);
                String title = rs.getString(2);
                Date releaseDate = rs.getDate(3);
                String country = rs.getString(4);
                int quantity = rs.getInt(5);
                int releaseNumber = rs.getInt(6);
                String printType = rs.getString(7);

                PrintMedia newPrintMedia = new PrintMedia(publicationID, title, releaseDate, country, quantity, releaseNumber, printType);

                currentPrintMedia = newPrintMedia;
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return currentPrintMedia;
    }

    public ArrayList<PrintMedia> loadAllPrintMedias() {
        ArrayList<PrintMedia> printMediaList = new ArrayList<>();
    
        try {
            String sql = "SELECT p.PublicationID, p.Title, p.ReleaseDate, p.Country, p.Quantity, pm.ReleaseNumber, pm.PrintType " +
                    "FROM Publications p " +
                    "INNER JOIN PrintMedia pm ON p.PublicationID = pm.PrintMediaID";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
    
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                String publicationID = rs.getString(1);
                String title = rs.getString(2);
                Date releaseDate = rs.getDate(3);

                String country = rs.getString(4);
                int quantity = rs.getInt(5);
                int releaseNumber = rs.getInt(6);
                String printType = rs.getString(7);
    
                PrintMedia printMedia = new PrintMedia(publicationID, title, releaseDate, country, quantity, releaseNumber, printType);
    
                printMediaList.add(printMedia);
            }
    
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return printMediaList;
    }

    public int getPrintMediaReleaseNumber(String printMediaID) {
        int releaseNumber = -1;
    
        try {
            String sql = "SELECT ReleaseNumber " +
                    "FROM PrintMedia " +
                    "WHERE PrintMediaID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, printMediaID);
    
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                releaseNumber = rs.getInt("ReleaseNumber");
            }
    
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return releaseNumber;
    }
    
    public String getPrintMediaPrintType(String printMediaID) {
        String printType = null;
    
        try {
            String sql = "SELECT PrintType " +
                    "FROM PrintMedia " +
                    "WHERE PrintMediaID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, printMediaID);
    
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                printType = rs.getString("PrintType");
            }
    
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return printType;
    }

    public boolean setPrintMediaReleaseNumber(String printMediaID, int newReleaseNumber) {
        try {
            String sql = "UPDATE PrintMedia " +
                    "SET ReleaseNumber = ? " +
                    "WHERE PrintMediaID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newReleaseNumber);
            pstmt.setString(2, printMediaID);
    
            pstmt.executeUpdate();
            
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    public boolean setPrintMediaType(String printMediaID, String newPrintType) {
        try {
            String sql = "UPDATE PrintMedia " +
                    "SET PrintType = ? " +
                    "WHERE PrintMediaID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPrintType);
            pstmt.setString(2, printMediaID);
    
            pstmt.executeUpdate();
            
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    // Students
    
    public Student getStudentbyID(String studentId) {
        Student student = null;
    
        try {
            String sql = "SELECT StudentID, Name, Gender, Address, Email, Phone, ClassName, Fine, FineStatus " +
                         "FROM Students " +
                         "WHERE StudentID = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
    
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                boolean gender = rs.getBoolean(3);

                String address = rs.getString(4);
                String email = rs.getString(5);
                String phone = rs.getString(6);

                String className = rs.getString(7);
                double fine = rs.getInt(8);
                boolean finestatus = rs.getBoolean(9);
    
                Student newStudent = new Student(id, name, gender, address, email, phone, className, fine, finestatus);
    
                student = newStudent;
            }
    
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return student;
    }
    
    public ArrayList<Student> loadAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
    
        try {
            String sql = "SELECT StudentID, Name, Gender, Address, Email, Phone, ClassName, Fine, FineStatus " +
                         "FROM Students";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                boolean gender = rs.getBoolean(3);

                String address = rs.getString(4);
                String email = rs.getString(5);
                String phone = rs.getString(6);

                String className = rs.getString(7);
                double fine = rs.getDouble(8);
                boolean finestatus = rs.getBoolean(9);
    
                Student student = new Student(id, name, gender, address, email, phone, className, fine, finestatus);
                students.add(student);
            }
    
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return students;
    }

    public boolean setStudentName(String studentId, String newName) {
        try {
            String sql = "UPDATE Students SET Name = ? WHERE StudentID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setString(2, studentId);
            
            pstmt.executeUpdate();
            
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }
    
    public boolean setStudentGender(String studentId, boolean newGender) {
        try {
            String sql = "UPDATE Students SET Gender = ? WHERE StudentID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, newGender);
            pstmt.setString(2, studentId);
            
            pstmt.executeUpdate();
            
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }    
    
    public boolean setStudentAddress(String studentId, String newAddress) {
        try {
            String sql = "UPDATE Students SET Address = ? WHERE StudentID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAddress);
            pstmt.setString(2, studentId);
            
            pstmt.executeUpdate();
            
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }
    
    public boolean setStudentEmail(String studentId, String newEmail) {
        try {
            String sql = "UPDATE Students SET Email = ? WHERE StudentID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newEmail);
            pstmt.setString(2, studentId);
            
            pstmt.executeUpdate();
            
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }

    public boolean setStudentPhone(String studentId, String newPhone) {
        try {
            String sql = "UPDATE Students SET Phone = ? WHERE StudentID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPhone);
            pstmt.setString(2, studentId);
            
            pstmt.executeUpdate();
            
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }
    
    public boolean setStudentClass(String studentId, String newClass) {
        try {
            String sql = "UPDATE Students SET ClassName = ? WHERE StudentID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newClass);
            pstmt.setString(2, studentId);
            
            pstmt.executeUpdate();
            
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }

    public boolean setStudentFine(String studentId, double newFine) {
        try {
            String sql = "UPDATE Students SET Fine = ? WHERE StudentID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, newFine);
            pstmt.setString(2, studentId);
            
            pstmt.executeUpdate();
            
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }
    
    public boolean setStudentFineStatus(String studentId, boolean fineStatus) {
        try {
            String sql = "UPDATE Students SET FineStatus = ? WHERE StudentID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, fineStatus);
            pstmt.setString(2, studentId);
            
            pstmt.executeUpdate();
            
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }

    public ArrayList<Books> searchBookbyTitle(String title) {
        ArrayList<Books> resultList = new ArrayList<>();
    
        try {
            String sql = "SELECT b.BookID, p.Title, GROUP_CONCAT(a.AuthorName) as Authors, p.ReleaseDate " +
                         "FROM Books b " +
                         "JOIN Publications p ON b.BookID = p.PublicationID " +
                         "JOIN BookAuthors ba ON b.BookID = ba.BookID " +
                         "JOIN Authors a ON ba.AuthorID = a.AuthorID " +
                         "WHERE p.Title LIKE ? " +
                         "GROUP BY b.BookID, p.Title, p.ReleaseDate";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + title + "%");
    
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                String bookId = rs.getString("BookID");
                String bookTitle = rs.getString("Title");
                ArrayList<String> authors = getBookAuthor(bookId);
                String releaseDate = rs.getString("ReleaseDate");
    
                Books result = new Books(bookId, bookTitle, authors, releaseDate);
                resultList.add(result);
            }
    
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return resultList;
    }

    public ArrayList<Books> searchBookbyCategory(String category) {
        ArrayList<Books> resultList = new ArrayList<>();
        
        try {
            String sql = "SELECT b.BookID, p.Title, GROUP_CONCAT(a.AuthorName) as Authors, p.ReleaseDate " +
                         "FROM Books b " +
                         "JOIN Publications p ON b.BookID = p.PublicationID " +
                         "JOIN BookAuthors ba ON b.BookID = ba.BookID " +
                         "JOIN Authors a ON ba.AuthorID = a.AuthorID " +
                         "WHERE b.Category LIKE ? " + 
                         "GROUP BY b.BookID, p.Title, p.ReleaseDate";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + category + "%");
    
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                String bookId = rs.getString("BookID");
                String bookTitle = rs.getString("Title");
                ArrayList<String> authors = getBookAuthor(bookId);
                String releaseDate = rs.getString("ReleaseDate");
    
                Books result = new Books(bookId, bookTitle, authors, releaseDate);
                resultList.add(result);
            }
    
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    
        return resultList;
    }

    public ArrayList<Books> searchBookbyAuthor(String authorName) {
        ArrayList<Books> resultList = new ArrayList<>();
        
        try {
            String sql = "SELECT b.BookID, p.Title, p.ReleaseDate " +
                         "FROM Books b " +
                         "JOIN Publications p ON b.BookID = p.PublicationID " +
                         "JOIN BookAuthors ba ON b.BookID = ba.BookID " +
                         "JOIN Authors a ON ba.AuthorID = a.AuthorID " +
                         "WHERE a.AuthorName LIKE ?";
                         
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + authorName + "%");
        
            ResultSet rs = pstmt.executeQuery();
        
            while (rs.next()) {
                String bookId = rs.getString("BookID");
                String bookTitle = rs.getString("Title");
                ArrayList<String> authors = getBookAuthor(bookId);
                String releaseDate = rs.getString("ReleaseDate");
    
                Books result = new Books(bookId, bookTitle, authors, releaseDate);
                resultList.add(result);
            }
        
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return resultList;
    }

    ////

    public ArrayList<Books> searchBookByCategories(List<String> categories) {
        ArrayList<Books> resultList = new ArrayList<>();
        
        try {
            String sql = "SELECT b.BookID, p.Title, GROUP_CONCAT(a.AuthorName) AS Authors, p.ReleaseDate " +
                         "FROM Books b " +
                         "JOIN Publications p ON b.BookID = p.PublicationID " +
                         "JOIN BookAuthors ba ON b.BookID = ba.BookID " +
                         "JOIN Authors a ON ba.AuthorID = a.AuthorID " +
                         "WHERE b.Category IN (";
            
            // Create a placeholder for each category
            StringBuilder categoryPlaceholders = new StringBuilder();
            for (int i = 0; i < categories.size(); i++) {
                categoryPlaceholders.append("?");
                if (i < categories.size() - 1) {
                    categoryPlaceholders.append(",");
                }
            }
            
            sql += categoryPlaceholders.toString() + ") " +
                   "GROUP BY b.BookID, p.Title, p.ReleaseDate";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // Set the category values in the prepared statement
            for (int i = 0; i < categories.size(); i++) {
                pstmt.setString(i + 1, categories.get(i));
            }
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String bookId = rs.getString("BookID");
                String bookTitle = rs.getString("Title");
                ArrayList<String> authors = getBookAuthor(bookId);
                String releaseDate = rs.getString("ReleaseDate");
        
                Books result = new Books(bookId, bookTitle, authors, releaseDate);
                resultList.add(result);
            }
        
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return resultList;
    }
    
    
    
}