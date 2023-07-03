package library.mysql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import library.mysql.DatabaseLayer;

public class AuthorDAO {

    public ArrayList<String> getBookAuthor(Integer BookID) throws SQLException {
        ArrayList<String> bookAuthorList = new ArrayList<>();

        String sql = "SELECT a.AuthorName FROM Authors a " +
                "INNER JOIN BookAuthors ba ON a.AuthorID = ba.AuthorID " +
                "INNER JOIN Books b ON ba.BookID = b.BookID " +
                "WHERE b.BookID = ?";

        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setInt(1, BookID);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String authorName = rs.getString(1);
            bookAuthorList.add(authorName);
        }

        rs.close();
        pstmt.close();

        return bookAuthorList;
    }

    public Integer addAuthor(String authorName) throws SQLException {
    	Integer authorID = -1;

    	String sql = "INSERT INTO Authors (AuthorName) VALUES (?)";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, authorName);

        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()){
        	authorID = rs.getInt(1);
        }

        rs.close();
        pstmt.close();

        return authorID;
    }

    public Integer addAuthorWithCheck(String authorName) throws SQLException {
    	Integer authorID = -1;

    	// Check if the author exists in the database or not
        String sql = "SELECT * FROM Authors WHERE AuthorName = ?";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setString(1, authorName);

        ResultSet rs = pstmt.executeQuery();
		if (!rs.next()) {
			// If not add first
			authorID = this.addAuthor(authorName);
		} else {
			authorID = rs.getInt(1);
		}
		rs.close();
		pstmt.close();

        return authorID;
    }

    public void addManyAuthorWithCheck(Integer publicationID, String authorManyNames) throws SQLException {
    	deleteAllBookAuthor(publicationID);

    	for (String authorName: authorManyNames.split(",")) {
        	authorName = authorName.strip();
        	if (authorName != "") {
        		Integer authorID = this.addAuthorWithCheck(authorName);
	            if (authorID != -1) {
	                String insertBookAuthorSql = "INSERT INTO BookAuthors (BookID, AuthorID) VALUES (?, ?)";
	                PreparedStatement insertBookAuthorStmt = DatabaseLayer.prepareStatement(insertBookAuthorSql);

					insertBookAuthorStmt.setInt(1, publicationID);
	                insertBookAuthorStmt.setInt(2, authorID);
	                insertBookAuthorStmt.executeUpdate();
	                insertBookAuthorStmt.close();
	            }
        	}
    	}
    }

    public ArrayList<String> getAuthors() throws SQLException {
        ArrayList<String> authorList = new ArrayList<>();

        String sql = "SELECT AuthorName FROM Authors";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
        	authorList.add(rs.getString(1));
        }

        rs.close();
        pstmt.close();

        return authorList;
    }

    public void deleteAllBookAuthor(int bookID) throws SQLException {
    	String sql = "DELETE FROM BookAuthors WHERE BookID = ?";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setInt(1, bookID);

        pstmt.executeUpdate();
        pstmt.close();
    }
}
