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
    private static final String PASSWORD = "q12Q!@";

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return pstmt;
    }
    
    public static PreparedStatement prepareStatement(String sql, Integer i) {
    	PreparedStatement pstmt = null;
    	try {
			pstmt = conn.prepareStatement(sql, i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return pstmt;
    }

/*
    // Publications
    public Publication getPublicationbyID(String PublicationID) {
        Publication currentPub = null;

        try {
            String sql = "SELECT * " +
                    "FROM Publications " +
                    "WHERE PublicationID = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, PublicationID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer publicationID = rs.getInt("PublicationID");
                String title = rs.getString(2);

                Date releaseDate = rs.getDate(3);
                String country = rs.getString(4);
                int quantity = rs.getInt(5);

                Publication newPub = new Publication(publicationID, title, releaseDate, country, quantity);

                currentPub = newPub;
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return currentPub;
    }

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
            pstmt.setDate(1, new Date(newReleaseDate.getTime()));
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

    public boolean setPublicationQuantity(Integer publicationID, int newQuantity) {
        try {
            String sql = "UPDATE Publications p " +
                    "SET p.Quantity = ? " +
                    "WHERE p.PublicationID = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, publicationID);

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

    public ArrayList<Publication> loadAllPublication() {
        ArrayList<Publication> arrList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Publications";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer publicationID = rs.getInt(1);
                String title = rs.getString(2);
                Date releaseDate = rs.getDate(3);
                String country = rs.getString(4);

                int quantity = rs.getInt(5);


                arrList.add(new Publication(publicationID, title, releaseDate, country, quantity));
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return arrList;
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
                Integer publicationID = rs.getInt(1);
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
                Integer publicationID = rs.getInt(1);
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
                Integer bookId = rs.getInt(1);
                String bookTitle = rs.getString(2);
                ArrayList<String> authors = getBookAuthor(bookId);
                Date releaseDate = rs.getDate(4);

                Books result = new Books(bookTitle, authors, releaseDate);
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
                Integer bookId = rs.getInt(1);
                String bookTitle = rs.getString(2);
                ArrayList<String> authors = getBookAuthor(bookId);
                Date releaseDate = rs.getDate(4);

                Books result = new Books(bookTitle, authors, releaseDate);
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
                Integer bookId = rs.getInt(1);
                String bookTitle = rs.getString(2);
                ArrayList<String> authors = getBookAuthor(bookId);
                Date releaseDate = rs.getDate(4);

                Books result = new Books(bookTitle, authors, releaseDate);
                resultList.add(result);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return resultList;
    }

    public ArrayList<PrintMedia> searchPrintMediabyTitle(String title) {
        ArrayList<PrintMedia> resultList = new ArrayList<>();

        try {
            String sql = "SELECT p.PublicationID, p.Title, p.ReleaseDate, pm.ReleaseNumber, pm.PrintType " +
                         "FROM Publications p " +
                         "JOIN PrintMedia pm ON p.PublicationID = pm.PrintMediaID " +
                         "WHERE p.Title LIKE ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + title + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String publicationId = rs.getString(1);
                String publicationTitle = rs.getString(2);
                Date releaseDate = rs.getDate(3);
                int releaseNumber = rs.getInt(4);
                String printType = rs.getString(5);

                PrintMedia printMedia = new PrintMedia(publicationId, publicationTitle, releaseDate, releaseNumber, printType);
                resultList.add(printMedia);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return resultList;
    }

    public ArrayList<PrintMedia> searchPrintMediabyPrintType(String type) {
        ArrayList<PrintMedia> resultList = new ArrayList<>();

        try {
            String sql = "SELECT p.PublicationID, p.Title, p.ReleaseDate, pm.ReleaseNumber, pm.PrintType " +
                         "FROM Publications p " +
                         "JOIN PrintMedia pm ON p.PublicationID = pm.PrintMediaID " +
                         "WHERE pm.PrintType LIKE ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + type + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String publicationId = rs.getString(1);
                String publicationTitle = rs.getString(2);
                Date releaseDate = rs.getDate(3);
                int releaseNumber = rs.getInt(4);
                String printType = rs.getString(5);

                PrintMedia printMedia = new PrintMedia(publicationId, publicationTitle, releaseDate, releaseNumber, printType);
                resultList.add(printMedia);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return resultList;
    }

    /* public ArrayList<Books> searchBookbyCategories(List<String> categories) {
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

    // Librarians

    public ArrayList<Librarian> loadAllLibrarians() {
        ArrayList<Librarian> librarians = new ArrayList<>();

        try {
            String sql = "SELECT StaffID, Name, Gender, Address, Email, Phone, Position FROM Staff WHERE Position = N'Thủ thư'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String staffId = rs.getString(1);
                String name = rs.getString(2);

                boolean gender = rs.getBoolean(3);
                String address = rs.getString(4);

                String email = rs.getString(5);
                String phone = rs.getString(6);
                String position = rs.getString(7);

                Librarian librarian = new Librarian(staffId, name, gender, address, email, phone, position);
                librarians.add(librarian);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return librarians;
    }

    // Clerks

    public ArrayList<Clerk> loadAllClerks() {
        ArrayList<Clerk> clerks = new ArrayList<>();

        try {
            String sql = "SELECT StaffID, Name, Gender, Address, Email, Phone, Position FROM Staff WHERE Position = N'Nhân viên'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String staffId = rs.getString(1);
                String name = rs.getString(2);

                boolean gender = rs.getBoolean(3);
                String address = rs.getString(4);

                String email = rs.getString(5);
                String phone = rs.getString(6);
                String position = rs.getString(7);

                Clerk clerk = new Clerk(staffId, name, gender, address, email, phone, position);
                clerks.add(clerk);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clerks;
    }

    // Borrow

    boolean addStudent(String Username, String Password, String StudentID, String Name, boolean Gender,
                       String Address, String Email, String Phone, String ClassName, double Fine, boolean FineStatus) {
        try {
            String usersSql = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
            PreparedStatement users_pstmt = conn.prepareStatement(usersSql);
            users_pstmt.setString(1, Username);
            users_pstmt.setString(2, Password);
            users_pstmt.executeUpdate();
            users_pstmt.close();

            String studentSql = "INSERT INTO Students VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement student_pstmt = conn.prepareStatement(studentSql);

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

    public ArrayList<Borrow> loadBorrowList() {
        ArrayList<Borrow> borrowList = new ArrayList<>();

        try {
            String sql = "SELECT b.BorrowID, s.StudentID, s.Name, b.StartDate, b.DueDate, b.ReturnedDate, p.PublicationID, p.Title, b.BorrowQuantity, b.FineStatus, b.ReturnedStatus " +
                    "FROM Borrow b " +
                    "JOIN Students s ON b.StudentID = s.StudentID " +
                    "JOIN Publications p ON b.PublicationID = p.PublicationID";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String borrowID = rs.getString(1);
                String studentID = rs.getString(2);
                String studentName = rs.getString(3);

                Date startDate = rs.getDate(4);
                Date dueDate = rs.getDate(5);
                Date returnedDate = rs.getDate(6);

                String publicationID = rs.getString(7);
                String publicationTitle = rs.getString(8);
                int borrowQuantity = rs.getInt(9);

                boolean fineStatus = rs.getBoolean(10);
                boolean returnedStatus = rs.getBoolean(11);

                Student borrower = new Student(studentID, studentName);
                Publication pub = new Publication(publicationTitle);
                Borrow currentBorrow = new Borrow(borrowID, startDate, dueDate, returnedDate, borrower, pub, borrowQuantity, fineStatus, returnedStatus);
                borrowList.add(currentBorrow);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return borrowList;
    }

    public ArrayList<Borrow> loadBorrowListbyID(String student_ID) {
        ArrayList<Borrow> borrowList = new ArrayList<>();

        try {
            String sql = "SELECT b.BorrowID, s.StudentID, s.Name, b.StartDate, b.DueDate, b.ReturnedDate, p.PublicationID, p.Title, b.BorrowQuantity, b.FineStatus, b.ReturnedStatus " +
                    "FROM Borrow b " +
                    "JOIN Students s ON b.StudentID = s.StudentID " +
                    "JOIN Publications p ON b.PublicationID = p.PublicationID " +
                    "WHERE s.StudentID = " + student_ID + "'";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String borrowID = rs.getString(1);
                String studentID = rs.getString(2);
                String studentName = rs.getString(3);

                Date startDate = rs.getDate(4);
                Date dueDate = rs.getDate(5);
                Date returnedDate = rs.getDate(6);

                String publicationID = rs.getString(7);
                String publicationTitle = rs.getString(8);
                int borrowQuantity = rs.getInt(9);

                boolean fineStatus = rs.getBoolean(10);
                boolean returnedStatus = rs.getBoolean(11);

                Student borrower = new Student(studentID, studentName);
                Publication pub = new Publication(publicationTitle);
                Borrow currentBorrow = new Borrow(borrowID, startDate, dueDate, returnedDate, borrower, pub, borrowQuantity, fineStatus, returnedStatus);
                borrowList.add(currentBorrow);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return borrowList;
    }

    public boolean addNewBorrow(Borrow update) {
        try {
            Date startDate = update.getStartDate();
            Date dueDate = update.getDueDate();
            Date returnedDate = update.getReturnedDate();

            String studentID = update.getBorrower().getStudentID();
            Integer publicationID = update.getBorrowedPub().getPublicationID();
            int borrowQuantity = update.getBorrowQuantity();

            boolean fineStatus = update.getFineStatus();
            boolean returnedStatus = update.getReturnedStatus();

            String sql = "INSERT INTO Borrow (StudentID, StartDate, DueDate, ReturnedDate, PublicationID, BorrowQuantity, FineStatus, ReturnedStatus) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentID);

            pstmt.setDate(3, new Date(startDate.getTime()));
            pstmt.setDate(4, new Date(dueDate.getTime()));
            pstmt.setDate(5, new Date(returnedDate != null ? returnedDate.getTime() : 0));

            pstmt.setInt(6, publicationID);
            pstmt.setInt(7, borrowQuantity);
            pstmt.setBoolean(8, fineStatus);
            pstmt.setBoolean(9, returnedStatus);

            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }


    public String getBorrowPublicationbyID(String borrowID) {
        String publicationID = " ";
        try {
            String sql = "SELECT PublicationID FROM Borrow WHERE BorrowID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, borrowID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                publicationID = rs.getString(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return publicationID;
    }

    public Publication getDetailBorrowPublicationbyID(String borrowID) {
        Publication pub = null;
        try {
            String sql = "SELECT PublicationID FROM Borrow WHERE BorrowID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, borrowID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String publicationID = rs.getString(1);
                pub = getPublicationbyID(publicationID);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return pub;
    }

    public boolean getBorrowFineStatusbyID(String borrowID) {
        boolean finestt = false;
        try {
            String sql = "SELECT FineStatus FROM Borrow WHERE BorrowID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, borrowID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                finestt = rs.getBoolean(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return finestt;
    }

    public boolean getBorrowReturnedStatusbyID(String borrowID) {
        boolean returnedstt = false;
        try {
            String sql = "SELECT ReturnedStatus FROM Borrow WHERE BorrowID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, borrowID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                returnedstt = rs.getBoolean(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return returnedstt;
    }

    public int getBorrowQuantitybyID(String borrowID) {
        int quantity = -1;
        try {
            String sql = "SELECT ReturnedStatus FROM Borrow WHERE BorrowID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, borrowID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                quantity = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return quantity;
    }

    public Date getBorrowStartDatebyID(String borrowID) {
        Date startDate = null;
        try {
            String sql = "SELECT StartDate FROM Borrow WHERE BorrowID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, borrowID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                startDate = rs.getDate(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return startDate;
    }

    public Date getBorrowDueDatebyID(String borrowID) {
        Date dueDate = null;
        try {
            String sql = "SELECT DueDate FROM Borrow WHERE BorrowID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, borrowID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                dueDate = rs.getDate(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return dueDate;
    }

    public Date getBorrowReturnedDatebyID(String borrowID) {
        Date returnedDate = null;
        try {
            String sql = "SELECT ReturnedDate FROM Borrow WHERE BorrowID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, borrowID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                returnedDate = rs.getDate(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return returnedDate;
    }

    public String getStudentIDbyBorrowID(String borrowID) {
        String studentID = " ";
        try {
            String sql = "SELECT StudentID FROM Borrow WHERE BorrowID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, borrowID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                studentID = rs.getString(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return studentID;
    }

    public boolean setBorrow(String borrowID, Borrow newBorrow) {
        try {
            String sql = "UPDATE Borrow SET BorrowID = ?, StudentID = ?, StartDate = ?, DueDate = ?, ReturnedDate = ?, " +
                "PublicationID = ?, BorrowQuantity = ?, FineStatus = ?, ReturnedStatus = ? WHERE BorrowID = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, borrowID);
            pstmt.setString(2, newBorrow.getBorrower().getStudentID());

            pstmt.setDate(3, new Date(newBorrow.getStartDate().getTime()));
            pstmt.setDate(4, new Date(newBorrow.getDueDate().getTime()));
            pstmt.setDate(5, new Date(newBorrow.getReturnedDate().getTime()));

            pstmt.setInt(6, newBorrow.getBorrowedPub().getPublicationID());
            pstmt.setInt(7, newBorrow.getBorrowQuantity());
            pstmt.setBoolean(8, newBorrow.getFineStatus());
            pstmt.setBoolean(9, newBorrow.getReturnedStatus());

            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean setBorrowStudentID(String borrowID, String studentID) {
        try {
            String sql = "UPDATE Borrow SET StudentID = ? WHERE BorrowID = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentID);
            pstmt.setString(2, borrowID);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean setBorrowReturnedDate(String borrowID, Date returnedDate) {
        try {
            String sql = "UPDATE Borrow SET ReturnedDate = ? WHERE BorrowID = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, new Date(returnedDate.getTime()));
            pstmt.setString(2, borrowID);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean setBorrowPublicationID(String borrowID, String publicationID) {
        try {
            String sql = "UPDATE Borrow SET PublicationID = ? WHERE BorrowID = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, publicationID);
            pstmt.setString(2, borrowID);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean setBorrowQuantity(String borrowID, int borrowQuantity) {
        try {
            String sql = "UPDATE Borrow SET BorrowQuantity = ? WHERE BorrowID = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, borrowQuantity);
            pstmt.setString(2, borrowID);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean setBorrowFineStatus(String borrowID, boolean fineStatus) {
        try {
            String sql = "UPDATE Borrow SET FineStatus = ? WHERE BorrowID = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, fineStatus);
            pstmt.setString(2, borrowID);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean setBorrowReturnedStatus(Integer borrowID, boolean returnedStatus) {
        try {
            String sql = "UPDATE Borrow SET ReturnedStatus = ? WHERE BorrowID = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, returnedStatus);
            pstmt.setInt(2, borrowID);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean checkStudentID(String studentID) {
        boolean exists = false;

        try {
            String sql = "SELECT COUNT(*) FROM Students WHERE StudentID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentID);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                exists = count > 0;
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return exists;
    }

    // Add & delete Publications

    public boolean isPublicationBorrowed(Integer publicationID) {
        boolean borrowed = false;

        try {
            String sql = "SELECT COUNT(*) FROM Borrow WHERE PublicationID = ? AND ReturnedStatus = false";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, publicationID);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                borrowed = count > 0;
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return borrowed;
    }

    boolean addPublisher(String publisherName) {
        try {
            String sql = "INSERT INTO Publishers (PublisherName) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, publisherName);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    boolean addPrintMedia(String title, Date releaseDate, String country, int quantity, int ReleaseNumber, String printType) {
        boolean success = addPublication(title, releaseDate, country, quantity);

        if (success) {
            try {
                String sql = "INSERT INTO PrintMedia (ReleaseNumber, PrintType) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, ReleaseNumber);
                pstmt.setString(2, printType);

                pstmt.executeUpdate();
                pstmt.close();
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }
        return true;
    }

    public boolean deletePublication(Integer publicationID) {
        try {
            String deletePublicationSql = "DELETE FROM Publications WHERE PublicationID = ?";
            PreparedStatement deletePublicationStmt = conn.prepareStatement(deletePublicationSql);
            deletePublicationStmt.setInt(1, publicationID);

            int rowsAffected = deletePublicationStmt.executeUpdate();
            deletePublicationStmt.close();

            if (rowsAffected == 0) {
                System.out.println("No publication found with the provided ID.");
                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean deletePrintMedia(Integer publicationID) {
        boolean success = deletePublication(publicationID);

        if (success) {
            try {
                String deletePrintMediaSql = "DELETE FROM PrintMedia WHERE PublicationID = ?";
                PreparedStatement deletePrintMediaStmt = conn.prepareStatement(deletePrintMediaSql);
                deletePrintMediaStmt.setInt(1, publicationID);
                deletePrintMediaStmt.executeUpdate();
                deletePrintMediaStmt.close();

                return true;

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return false;
    }

    public boolean deleteBook(Integer publicationID) {
        boolean success = deletePublication(publicationID);

        if (success) {
            try {
                String deleteBookSql = "DELETE FROM Books WHERE BookID = ?";
                PreparedStatement deleteBookStmt = conn.prepareStatement(deleteBookSql);
                deleteBookStmt.setInt(1, publicationID);
                deleteBookStmt.executeUpdate();
                deleteBookStmt.close();

                String deleteBookAuthorsSql = "DELETE FROM BookAuthors WHERE BookID = ?";
                PreparedStatement deleteBookAuthorsStmt = conn.prepareStatement(deleteBookAuthorsSql);
                deleteBookAuthorsStmt.setInt(1, publicationID);
                deleteBookAuthorsStmt.executeUpdate();
                deleteBookAuthorsStmt.close();

                return true;

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return false;
    }

    public String getPasswordfromUsername(String username) {
        String password = " ";
        try {
            String sql = "SELECT Password FROM Users WHERE Username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                password = rs.getString(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return password;
    }

    public boolean setPasswordfromUsername(String username, String password) {
        try {
            String sql = "UPDATE Users " +
                    "SET Password = ? " +
                    "WHERE Username = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setString(2, username);

            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

*/
}