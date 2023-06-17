package library.mysql.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import library.mysql.DatabaseLayer;
import library.publication.PrintMedia;

public class PrintMediaDAO {

	private PublicationDAO publicationDAO = new PublicationDAO();

	public List<PrintMedia> loadAllPrintMedias() {
        List<PrintMedia> printMediaList = new ArrayList<>();

        try {
            String sql = "SELECT p.PublicationID, p.Title, p.ReleaseDate, p.Country, p.Quantity, pm.ReleaseNumber, pm.PrintType " +
                    "FROM Publications p " +
                    "INNER JOIN PrintMedia pm ON p.PublicationID = pm.PrintMediaID";

            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	Integer publicationID = rs.getInt("PublicationID");

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
        } catch (Exception e) {
            System.out.println(e);
        }
        return printMediaList;
    }

	public boolean addPrintMedia(String title, Date releaseDate, String country, int quantity, int releaseNumber, String printType) {
        Integer publicationID = publicationDAO.addPublication(title, releaseDate, country, quantity);

        if (publicationID > 0) {
        	try {
                String sql = "INSERT INTO PrintMedia (PrintMediaID, ReleaseNumber, PrintType) VALUES (?, ?, ?)";
                PreparedStatement insertPMStmt = DatabaseLayer.prepareStatement(sql);
                insertPMStmt.setInt(1, publicationID);
                insertPMStmt.setInt(2, releaseNumber);
                insertPMStmt.setString(3, printType);

                insertPMStmt.executeUpdate();
                insertPMStmt.close();

                System.out.println("Added print media");
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }
        return true;
    }

    public boolean deletePrintMedia(Integer publicationID) {
        return publicationDAO.deletePublication(publicationID);
    }
}
