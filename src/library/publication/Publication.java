package library.publication;

import java.util.Date;

public abstract class Publication {
	private int PublicationID;
	private String Title;
	private Date ReleaseDate;
	private String Country;
	private int Quantity;

	public Publication(int PublicationID, String Title, Date ReleaseDate, String Country, int Quantity) {
		this.PublicationID = PublicationID;
		this.Title = Title;
		this.ReleaseDate = ReleaseDate;
		this.Country = Country;
		this.Quantity = Quantity;
	}

	public int getPublicationID() {
		return this.PublicationID;
	}

	public void setPublicationID(int PublicationID) {
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
	
}
