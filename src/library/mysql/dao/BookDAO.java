package library.mysql.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import library.mysql.DatabaseLayer;
import library.publication.Books;

public class BookDAO {
	
	private AuthorDAO authorDAO = new AuthorDAO();
	private PublicationDAO publicationDAO = new PublicationDAO();
	
	public ArrayList<Books> loadAllBooks() {
        ArrayList<Books> bookList = new ArrayList<>();
        try {
            String sql = "SELECT p.PublicationID, p.Title, GROUP_CONCAT(a.AuthorName) as Authors, p.ReleaseDate, p.Country, p.Quantity, b.Category, b.Reissue, pb.PublisherName "
            		+ "FROM Publications p "
            		+ "INNER JOIN Books b ON p.PublicationID = b.BookID "
            		+ "INNER JOIN BookAuthors ba ON b.BookID = ba.BookID "
            		+ "INNER JOIN Authors a ON ba.AuthorID = a.AuthorID "
            		+ "INNER JOIN Publishers pb ON b.PublisherID = pb.PublisherID "
            		+ "GROUP BY p.PublicationID, p.Title, p.ReleaseDate, p.Country, p.Quantity, b.Category, b.Reissue, pb.PublisherName";

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	Integer publicationID = rs.getInt("PublicationID");

                ArrayList<String> authors = authorDAO.getBookAuthor(publicationID);

                Books Book = new Books(
                		rs.getInt(1), 
                		rs.getString(2), 
                		authors, 
                		rs.getDate(4), 
                		rs.getString(5), 
                		rs.getInt(6), 
                		rs.getString(7), 
                		rs.getInt(8), 
                		rs.getString(9)
                );
                bookList.add(Book);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return bookList;
    }
	

    public boolean addBook(String title, Date releaseDate, String country, int quantity, String category, boolean reissue, 
    		String publisherName, String authorName) {
        Integer publicationID = publicationDAO.addPublication(title, releaseDate, country, quantity);

        if (publicationID > 0) {
            try {
                // Thêm sách
            	Integer publisherID = this.addPublisherWithCheck(publisherName); 
            	
                String insertBookSql = "INSERT INTO Books (BookID, Category, Reissue, PublisherID) VALUES (?, ?, ?, ?)";
                PreparedStatement insertBookStmt = DatabaseLayer.prepareStatement(insertBookSql);
                insertBookStmt.setInt(1, publicationID);
                insertBookStmt.setString(2, category);
                insertBookStmt.setBoolean(3, reissue);
                insertBookStmt.setInt(4, publisherID);
                insertBookStmt.executeUpdate();
                insertBookStmt.close();
                
                Integer authorID = authorDAO.addAuthorWithCheck(authorName);
                if (authorID != -1) {
	                // Thêm tác giả của sách
	                String insertBookAuthorSql = "INSERT INTO BookAuthors (BookID, AuthorID) VALUES (?, ?)";
	                PreparedStatement insertBookAuthorStmt = DatabaseLayer.prepareStatement(insertBookAuthorSql);
	                insertBookAuthorStmt.setInt(1, publicationID);
	                insertBookAuthorStmt.setInt(2, authorID);
	                insertBookAuthorStmt.executeUpdate();
	                insertBookAuthorStmt.close();
                }
                              
                System.out.println("Added Book");
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }
        return true;
    }
    
    public boolean deleteBook(Integer publicationID) {
        return publicationDAO.deletePublication(publicationID);
        // FOREIGN KEY (`BookID`) REFERENCES `publications` (`PublicationID`) ON DELETE CASCADE
    }
    
    public Integer addPublisher(String publisherName) throws SQLException {
    	Integer publisherID = -1;
        try {
            String sql = "INSERT INTO Publishers (PublisherName) VALUES (?)";
            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, publisherName);
            
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()){ 
            	publisherID = rs.getInt(1); 
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return publisherID;
    }
    
    public Integer addPublisherWithCheck(String publisherName) {
    	Integer publisherID = -1;
        try {
            // Kiểm tra xem đã có tác giả trong CSDL chưa
            String sql = "SELECT * FROM Publishers WHERE PublisherName = ?";
            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            pstmt.setString(1, publisherName);
            
            ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				// Nếu không có thì thêm tác giả mới vào trước
				publisherID = this.addPublisher(publisherName);
			} else {
				publisherID = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return publisherID;
    }
    
	/* UNTESTED REGION */
	/* UNTESTED REGION */
	/* UNTESTED REGION */
	/* UNTESTED REGION */
	/* UNTESTED REGION */
	/* UNTESTED REGION */
	/* UNTESTED REGION */
	/* UNTESTED REGION */
	/* UNTESTED REGION */
	/* UNTESTED REGION */
    
    public String getBookCategory(String BookID) {
        String category = "";
        try {
            String sql = "SELECT b.Category " +
                    "FROM Publications p " +
                    "INNER JOIN Books b ON p.PublicationID = b.BookID " +
                    "WHERE b.BookID = ?";

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
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

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
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

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
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

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
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

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
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

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            pstmt.setString(1, bookID);
            pstmt.setString(2, publisherName);

            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
