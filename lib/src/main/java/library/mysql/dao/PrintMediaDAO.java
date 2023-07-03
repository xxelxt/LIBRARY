package library.mysql.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.mysql.DatabaseLayer;
import library.publication.PrintMedia;

public class PrintMediaDAO {

	private PublicationDAO publicationDAO = new PublicationDAO();

	public List<PrintMedia> loadAllPrintMedias() throws SQLException {
        List<PrintMedia> printMediaList = new ArrayList<>();

        String sql = "SELECT P.PublicationID, P.Title, P.ReleaseDate, P.Country, P.Quantity, PM.ReleaseNumber, PM.PrintType "
                + "FROM Publications P "
                + "INNER JOIN PrintMedia PM ON P.PublicationID = PM.PrintMediaID "
                + "ORDER BY P.PublicationID DESC";

        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            PrintMedia PM = new PrintMedia(
            		rs.getInt(1),
            		rs.getString(2),
            		rs.getDate(3),
            		rs.getString(4),
            		rs.getInt(5),
            		rs.getInt(6),
            		rs.getString(7)
            );
            printMediaList.add(PM);
        }

        rs.close();
        pstmt.close();

        return printMediaList;
    }

	public void addPrintMedia(String title, Date releaseDate, String country, int quantity, int releaseNumber, String printType) throws SQLException {
        Integer publicationID = publicationDAO.addPublication(title, releaseDate, country, quantity);

        if (publicationID > 0) {
        	String sql = "INSERT INTO PrintMedia (PrintMediaID, ReleaseNumber, PrintType) VALUES (?, ?, ?)";
            PreparedStatement insertPMStmt = DatabaseLayer.prepareStatement(sql);
            insertPMStmt.setInt(1, publicationID);
            insertPMStmt.setInt(2, releaseNumber);
            insertPMStmt.setString(3, printType);

            insertPMStmt.executeUpdate();
            insertPMStmt.close();

            System.out.println("Added print media.");
        }
    }

	public void updatePrintMedia(PrintMedia pm) throws SQLException {
		String sql = "UPDATE Publications P "
        		+ "JOIN PrintMedia PM ON P.PublicationID = PM.PrintMediaID "
        		+ "SET P.Title = ?, "
        		+ "P.ReleaseDate = ?, "
        		+ "P.Country = ?, "
        		+ "PM.ReleaseNumber = ?, "
        		+ "P.Quantity = ?, "
        		+ "PM.PrintType = ? "
        		+ "WHERE P.PublicationID = ?";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setString(1, pm.getTitle());
        pstmt.setDate(2, pm.getReleaseDate());
        pstmt.setString(3, pm.getCountry());
        pstmt.setInt(4, pm.getReleaseNumber());
        pstmt.setInt(5, pm.getQuantity());
        pstmt.setString(6, pm.getPrintType());
        pstmt.setInt(7, pm.getPublicationID());

        pstmt.executeUpdate();
        pstmt.close();

        System.out.println("Updated print media.");
    }

    public void deletePrintMedia(Integer publicationID) throws SQLException {
        publicationDAO.deletePublication(publicationID);
    }
}
