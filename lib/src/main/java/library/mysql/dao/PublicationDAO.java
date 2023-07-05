package library.mysql.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import library.mysql.DatabaseLayer;

public class PublicationDAO {

	public Integer addPublication(String title, Date releaseDate, String country, int quantity) throws SQLException {
	    Integer newPublicationID = -1;

	    String sql = "INSERT INTO Publications (Title, ReleaseDate, Country, Quantity) VALUES (?, ?, ?, ?)";
	    PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	    pstmt.setString(1, title);
	    if (releaseDate != null) {
	        pstmt.setDate(2, new java.sql.Date(releaseDate.getTime()));
	    } else {
	        pstmt.setNull(2, java.sql.Types.DATE);
	    }
	    pstmt.setString(3, country);
	    pstmt.setInt(4, quantity);

	    pstmt.executeUpdate();
	    ResultSet rs = pstmt.getGeneratedKeys();

	    if (rs.next()) {
	        newPublicationID = rs.getInt(1);
	    }

	    rs.close();
	    pstmt.close();

	    return newPublicationID;
	}


    public void deletePublication(Integer publicationID) throws SQLException {
    	String sql = "DELETE FROM Publications WHERE PublicationID = ?";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setInt(1, publicationID);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public String getTitlebyPublicationID(Integer publicationID) throws SQLException {
    	String title = "";

    	String sql = "SELECT Title FROM Publications WHERE PublicationID = ?";
        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        pstmt.setInt(1, publicationID);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            title = rs.getString(1);
        }
        rs.close();
        pstmt.close();

    	return title;
    }

    public ArrayList<String> loadAllCountries() throws SQLException {
        ArrayList<String> countries = new ArrayList<>();

        String sql = "SELECT DISTINCT Country "
        		+ "FROM Publications";

        PreparedStatement pstmt = DatabaseLayer.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
        	String country = rs.getString(1);
        	countries.add(country);
        }

        rs.close();
        pstmt.close();

        return countries;
    }
}
