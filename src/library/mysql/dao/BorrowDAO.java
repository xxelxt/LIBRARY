package library.mysql.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import library.central.Borrow;
import library.mysql.DatabaseLayer;

public class BorrowDAO {

	public ArrayList<Borrow> loadAllBorrow() {
        ArrayList<Borrow> borrowList = new ArrayList<>();
        try {
            String sql = "SELECT b.BorrowID, b.StudentID, b.PublicationID, p.Title, b.BorrowQuantity, b.StartDate, b.DueDate, b.ReturnedDate, b.FineStatus, b.ReturnedStatus "
            		+ "FROM Borrow AS b "
            		+ "JOIN Publications AS p ON b.PublicationID = p.PublicationID";

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Borrow borrow = new Borrow(
                		rs.getInt(1),
                		rs.getString(2),
                		rs.getInt(3),
                		rs.getString(4),
                		rs.getInt(5),
                		rs.getDate(6),
                		rs.getDate(7),
                		rs.getDate(8),
                		rs.getBoolean(9),
                		rs.getBoolean(10)
                );
                borrowList.add(borrow);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return borrowList;
    }

	public boolean addBorrow(String studentID, Integer publicationID, Integer borrowQuantity, Date startDate, Date dueDate, boolean fineStatus, boolean returnedStatus) {
		try {
			 String sql = "INSERT INTO Borrow (StudentID, PublicationID, BorrowQuantity, StartDate, DueDate, ReturnedDate, FineStatus, ReturnedStatus) "
			 		+ "VALUES (?, ?, ?, ?, ?, NULL, ?, ?)";
			 PreparedStatement borrow_pstmt = DatabaseLayer.prepareStatement(sql);

			 borrow_pstmt.setString(1, studentID);
			 borrow_pstmt.setInt(2, publicationID);
			 borrow_pstmt.setInt(3, borrowQuantity);

			 borrow_pstmt.setDate(4, startDate);
			 borrow_pstmt.setDate(5, dueDate);

			 borrow_pstmt.setBoolean(6, fineStatus);
			 borrow_pstmt.setBoolean(7, returnedStatus);

			 borrow_pstmt.executeUpdate();
			 borrow_pstmt.close();

			 System.out.println("Added borrow.");

		} catch (Exception e) {
			 System.out.println(e);
			 return false;
		}
		return true;
    }

	public boolean deleteBorrow(int borrowID) {
		try {
	        String deleteBorrowSql = "DELETE FROM Borrow WHERE BorrowID = ?";
	        PreparedStatement deleteStmt = DatabaseLayer.prepareStatement(deleteBorrowSql);
	        deleteStmt.setInt(1, borrowID);

	        int rowsAffected = deleteStmt.executeUpdate();
	        deleteStmt.close();

	        if (rowsAffected == 0) {
	            System.out.println("No borrow found with the provided ID.");
	            return false;
	        }

	        System.out.println("Deleted borrow.");

	    } catch (Exception e) {
	        System.out.println(e);
	        return false;
	    }
	    return true;
	}

	public boolean updateBorrow(Borrow borrow) {
		try {
            String sql = "UPDATE Borrow "
            		+ "SET "
            		+ "StudentID = ?, "
            		+ "PublicationID = ?, "
            		+ "BorrowQuantity = ?, "
            		+ "StartDate = ?, "
            		+ "DueDate = ?, "
            		+ "ReturnedDate = ?, "
            		+ "FineStatus = ?, "
            		+ "ReturnedStatus = ? "
            		+ "WHERE BorrowID = ?";

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
            pstmt.setString(1, borrow.getStudentID());
            pstmt.setInt(2, borrow.getPublicationID());
            pstmt.setInt(3, borrow.getBorrowQuantity());

            pstmt.setDate(4, borrow.getStartDate());
            pstmt.setDate(5, borrow.getDueDate());
            pstmt.setDate(6, borrow.getReturnedDate());

            pstmt.setBoolean(7, borrow.isFineStatus());
            pstmt.setBoolean(8, borrow.isReturnedStatus());
            pstmt.setInt(9, borrow.getBorrowID());

            pstmt.executeUpdate();
            pstmt.close();

            System.out.println("Updated borrow.");
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

		return true;
	}


}
