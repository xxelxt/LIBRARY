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

	public ArrayList<Books> loadAllBooks() throws SQLException {
        ArrayList<Books> bookList = new ArrayList<>();

        String sql = "SELECT P.PublicationID, P.Title, GROUP_CONCAT(A.AuthorName) as Authors, P.ReleaseDate, P.Country, P.Quantity, B.Category, B.Reissue, PB.PublisherName "
        		+ "FROM Publications P "
        		+ "INNER JOIN Books B ON P.PublicationID = B.BookID "
        		+ "INNER JOIN BookAuthors BA ON B.BookID = BA.BookID "
        		+ "INNER JOIN Authors A ON BA.AuthorID = A.AuthorID "
        		+ "INNER JOIN Publishers PB ON B.PublisherID = PB.PublisherID "
        		+ "GROUP BY P.PublicationID, P.Title, P.ReleaseDate, P.Country, P.Quantity, B.Category, B.Reissue, PB.PublisherName "
                + "ORDER BY P.PublicationID DESC";

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

        return bookList;
    }


    public void addBook(String title, Date releaseDate, String country, int quantity, String category, int reissue,
    		String authorManyNames, String publisherName) throws SQLException {
        Integer publicationID = publicationDAO.addPublication(title, releaseDate, country, quantity);

        if (publicationID > 0) {
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
        }
    }

    public void deleteBook(Integer publicationID) throws SQLException {
        publicationDAO.deletePublication(publicationID);
    }

    public void updateBook(Books book) throws SQLException {
    	String sql = "UPDATE Publications P "
        		+ "JOIN Books B ON P.PublicationID = B.BookID "
        		+ "SET P.Title = ?, "
        		+ "P.ReleaseDate = ?, "
        		+ "P.Country = ?, "
        		+ "B.Category = ?, "
        		+ "P.Quantity = ?, "
        		+ "B.Reissue = ? "
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
    }

    public void updateBookPublisherID(Books book, int publisherID) throws SQLException {
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
    }

    public Integer addPublisher(String publisherName) throws SQLException {
    	Integer publisherID = -1;

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

        return publisherID;
    }

    public Integer addPublisherWithCheck(String publisherName) throws SQLException {
    	Integer publisherID = -1;

    	// Check if the publisher exists in the database or not
        String sql = "SELECT * FROM Publishers WHERE PublisherName = ?";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setString(1, publisherName);

        ResultSet rs = pstmt.executeQuery();
		if (!rs.next()) {
			// If not add first
			publisherID = this.addPublisher(publisherName);
		} else {
			publisherID = rs.getInt(1);
		}

		rs.close();
		pstmt.close();

        return publisherID;
    }
}
