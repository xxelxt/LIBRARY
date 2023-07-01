package library.mysql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import library.mysql.DatabaseLayer;

public class AuthorDAO {

    public ArrayList<String> getBookAuthor(Integer BookID) {
        ArrayList<String> authors = new ArrayList<>();

        try {
            String sql = "SELECT a.AuthorName FROM Authors a " +
                    "INNER JOIN BookAuthors ba ON a.AuthorID = ba.AuthorID " +
                    "INNER JOIN Books b ON ba.BookID = b.BookID " +
                    "WHERE b.BookID = ?";

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            pstmt.setInt(1, BookID);

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

    public Integer addAuthor(String authorName) throws SQLException {
    	Integer authorID = -1;
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return authorID;
    }

    public Integer addAuthorWithCheck(String authorName) {
    	Integer authorID = -1;
        try {
            // Kiểm tra xem đã có tác giả trong CSDL chưa
            String checkAuthorSql = "SELECT * FROM Authors WHERE AuthorName = ?";
            PreparedStatement pstmt = DatabaseLayer.prepareStatement(checkAuthorSql);
            pstmt.setString(1, authorName);

            ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				// Nếu không có thì thêm tác giả mới vào trước
				authorID = this.addAuthor(authorName);
			} else {
				authorID = rs.getInt(1);
			}
			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return authorID;
    }

    public void addManyAuthorWithCheck(Integer publicationID, String authorManyNames) {
    	deleteAllBookAuthor(publicationID);
    	
    	for (String authorName: authorManyNames.split(",")) {
        	authorName = authorName.strip();
        	if (authorName != "") {
        		try {
		            Integer authorID = this.addAuthorWithCheck(authorName);
		            if (authorID != -1) {
		                // Thêm tác giả của sách
		                String insertBookAuthorSql = "INSERT INTO BookAuthors (BookID, AuthorID) VALUES (?, ?)";
		                PreparedStatement insertBookAuthorStmt = DatabaseLayer.prepareStatement(insertBookAuthorSql);

						insertBookAuthorStmt.setInt(1, publicationID);
		                insertBookAuthorStmt.setInt(2, authorID);
		                insertBookAuthorStmt.executeUpdate();
		                insertBookAuthorStmt.close();
		            }
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        	}
    	}
    }

    public ArrayList<String> getAuthors() {
        ArrayList<String> authors = new ArrayList<>();

        try {
        	String sql = "SELECT AuthorName FROM Authors";
            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                authors.add(rs.getString(1));
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return authors;
    }
    
    public void deleteAllBookAuthor(int bookID) {
    	try {
            String sql = "DELETE FROM BookAuthors WHERE BookID = ?";
            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            pstmt.setInt(1, bookID);

            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}