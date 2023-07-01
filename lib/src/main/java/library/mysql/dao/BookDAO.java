package library.mysql.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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


    public boolean addBook(String title, Date releaseDate, String country, int quantity, String category, int reissue,
    		String authorManyNames, String publisherName) {
        Integer publicationID = publicationDAO.addPublication(title, releaseDate, country, quantity);

        if (publicationID > 0) {
            try {
                // Thêm sách
            	Integer publisherID = this.addPublisherWithCheck(publisherName);

                String insertBookSql = "INSERT INTO Books (BookID, Category, Reissue, PublisherID) VALUES (?, ?, ?, ?)";
                PreparedStatement insertBookStmt = DatabaseLayer.prepareStatement(insertBookSql);
                insertBookStmt.setInt(1, publicationID);
                insertBookStmt.setString(2, category);
                insertBookStmt.setInt(3, reissue);
                insertBookStmt.setInt(4, publisherID);
                insertBookStmt.executeUpdate();
                insertBookStmt.close();

                authorDAO.addManyAuthorWithCheck(publicationID, authorManyNames);

                System.out.println("Added book.");
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }
        return true;
    }

    public boolean deleteBook(Integer publicationID) {
        return publicationDAO.deletePublication(publicationID);
    }

    public boolean updateBook(Books book) {
    	try {
            String sql = "UPDATE Publications P "
            		+ "JOIN Books B ON P.PublicationID = B.BookID "
            		+ "SET P.Title = ? ,"
            		+ "    P.ReleaseDate = ? ,"
            		+ "    P.Country = ? ,"
            		+ "    B.Category = ? ,"
            		+ "    P.Quantity = ? ,"
            		+ "    B.Reissue = ? "
            		+ "WHERE P.PublicationID = ?";
            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            pstmt.setString(1, book.getTitle());
            pstmt.setDate(2, book.getReleaseDate());
            pstmt.setString(3, book.getCountry());
            pstmt.setString(4, book.getCategory());
            pstmt.setInt(5, book.getQuantity());
            pstmt.setInt(6, book.getReissue());
            pstmt.setInt(7, book.getPublicationID());

            pstmt.executeUpdate();
            pstmt.close();

            System.out.println("Updated book.");
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    	return true;
    }
    
    public void updateBookPublisherID(Books book, int publisherID) throws Exception {
    	try {
            String sql = "UPDATE Books B "
            		+ "JOIN Publications P ON P.PublicationID = B.BookID "
            		+ "SET B.PublisherID = ? "
            		+ "WHERE P.PublicationID = ?";
            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            pstmt.setInt(1, publisherID);
            pstmt.setInt(2, book.getPublicationID());

            pstmt.executeUpdate();
            pstmt.close();

            System.out.println("Updated book publisher.");
        } catch (SQLException e) {
            throw e;
        }
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

            System.out.println("Added publisher.");
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            throw e;
        }
        return publisherID;
    }

    public Integer addPublisherWithCheck(String publisherName) throws SQLException {
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
			throw e;
		}

        return publisherID;
    }
}
