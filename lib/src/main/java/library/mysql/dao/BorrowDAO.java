package library.mysql.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import library.central.Borrow;
import library.mysql.DatabaseLayer;

public class BorrowDAO {

	public ArrayList<Borrow> loadAllBorrow() throws SQLException {
        ArrayList<Borrow> borrowList = new ArrayList<>();

        String sql = "SELECT B.BorrowID, B.StudentID, B.PublicationID, P.Title, B.BorrowQuantity, B.StartDate, B.DueDate, B.ReturnedDate, B.FineStatus, B.ReturnedStatus "
        		+ "FROM Borrow AS B "
        		+ "JOIN Publications AS P ON B.PublicationID = P.PublicationID";

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

        return borrowList;
    }

	public void addBorrow(String studentID, Integer publicationID, Integer borrowQuantity, Date startDate, Date dueDate, Date returnedDate, boolean fineStatus, boolean returnedStatus) throws SQLException {
		String sql = "INSERT INTO Borrow (StudentID, PublicationID, BorrowQuantity, StartDate, DueDate, ReturnedDate, FineStatus, ReturnedStatus) "
		 		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement borrow_pstmt = DatabaseLayer.prepareStatement(sql);

		borrow_pstmt.setString(1, studentID);
		borrow_pstmt.setInt(2, publicationID);
		borrow_pstmt.setInt(3, borrowQuantity);

		borrow_pstmt.setDate(4, startDate);
		borrow_pstmt.setDate(5, dueDate);
		borrow_pstmt.setDate(6, returnedDate);

		borrow_pstmt.setBoolean(7, fineStatus);
		borrow_pstmt.setBoolean(8, returnedStatus);

		borrow_pstmt.executeUpdate();
		borrow_pstmt.close();

		System.out.println("Added borrow.");
    }

	public void deleteBorrow(int borrowID) throws SQLException {
		String sql = "DELETE FROM Borrow WHERE BorrowID = ?";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setInt(1, borrowID);

        System.out.println("Deleted borrow.");
	}

	public void updateBorrow(Borrow borrow) throws SQLException {
		String sql = "UPDATE Borrow "
        		+ "SET StudentID = ?, "
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
	}

	public void updateBorrowFineStatus(int borrowID) throws SQLException {
		String sql = "UPDATE Borrow "
        		+ "SET FineStatus = TRUE "
        		+ "WHERE BorrowID = ?";

        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setInt(1, borrowID);

        pstmt.executeUpdate();
        pstmt.close();

        System.out.println("Updated borrow fine status: " + borrowID);
	}

	public void updateAllStudentBorrowFineStatusToFalse(String studentID) throws SQLException {
		String sql = "UPDATE Borrow "
        		+ "SET ReturnedStatus = TRUE "
        		+ "WHERE StudentID = ?";

        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setString(1, studentID);

        pstmt.executeUpdate();
        pstmt.close();

        System.out.println("Updated all borrow fine status for student: " + studentID);
	}
}
