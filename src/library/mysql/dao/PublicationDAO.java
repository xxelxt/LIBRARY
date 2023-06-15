package library.mysql.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import library.mysql.DatabaseLayer;

public class PublicationDAO {

    public Integer addPublication(String title, Date releaseDate, String country, int quantity) {
    	Integer newpubID = -1;
    	
        try {
            String sql = "INSERT INTO Publications (Title, ReleaseDate, Country, Quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, title);
            pstmt.setDate(2, new Date(releaseDate.getTime()));
            pstmt.setString(3, country);
            pstmt.setInt(4, quantity);

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()){
                newpubID = rs.getInt(1);
            } 
            
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return newpubID;
    }
    
    public boolean deletePublication(Integer publicationID) {
        try {
            String deletePublicationSql = "DELETE FROM Publications WHERE PublicationID = ?";
            PreparedStatement stmt = DatabaseLayer.prepareStatement(deletePublicationSql);
            stmt.setInt(1, publicationID);

            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            if (rowsAffected == 0) {
                System.out.println("No publication found with the provided ID.");
                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
