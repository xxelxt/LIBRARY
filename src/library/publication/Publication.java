package library.publication;

import library.mysql.Database;
import java.util.Date;

public abstract class Publication {
	private String PublicationID;
	private String Title;
	private Date ReleaseDate;
	private String Country;
	private int Quantity;

	public Publication(String PublicationID, String Title, Date ReleaseDate, String Country, int Quantity) {
		this.PublicationID = PublicationID;
		this.Title = Title;
		this.ReleaseDate = ReleaseDate;
		this.Country = Country;
		this.Quantity = Quantity;
	}

	public String getPublicationID() {
		return this.PublicationID;
	}

	public void setPublicationID(String PublicationID) {
		this.PublicationID = PublicationID;
	}

	public String getTitle() {
		return this.Title;
	}

	public void setTitle(String Title) {
		this.Title = Title;
	}

	public Date getReleaseDate() {
		return this.ReleaseDate;
	}

	public void setReleaseDate(Date ReleaseDate) {
		this.ReleaseDate = ReleaseDate;
	}

	public String getCountry() {
		return this.Country;
	}

	public void setCountry(String Country) {
		this.Country = Country;
	}

	public int getQuantity() {
		return this.Quantity;
	}

	public void setQuantity(int Quantity) {
		this.Quantity = Quantity;
	}
	
	public boolean checkAvailable(String PublicationID) {
        Database db = new Database();
        int Available = db.GetQuantityofBook(this.PublicationID);
        if (Available > 0) return true;
			else return false;
    }
}
