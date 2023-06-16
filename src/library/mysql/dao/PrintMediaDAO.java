package library.mysql.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import library.mysql.DatabaseLayer;
import library.publication.Books;
import library.publication.PrintMedia;

public class PrintMediaDAO {
	
	private PublicationDAO publicationDAO = new PublicationDAO();
	
	public ArrayList<PrintMedia> loadAllPrintMedias() {
        ArrayList<PrintMedia> printMediaList = new ArrayList<>();

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
                String sql = "INSERT INTO PrintMedia (ReleaseNumber, PrintType) VALUES (?, ?)";
                PreparedStatement insertPMStmt = DatabaseLayer.prepareStatement(sql);
                insertPMStmt.setInt(1, releaseNumber);
                insertPMStmt.setString(2, printType);

                insertPMStmt.executeUpdate();
                insertPMStmt.close();
                
                System.out.println("Added PM");
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
}
