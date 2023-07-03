package library.publication;

import java.sql.Date;

public class Publication {
	private Integer PublicationID;
	private String Title;
	private Date ReleaseDate;
	private String Country;
	private int Quantity;

	public Publication(Integer PublicationID, String Title, Date ReleaseDate, String Country, int Quantity) {
		this.PublicationID = PublicationID;
		this.Title = Title;
		this.ReleaseDate = ReleaseDate;
		this.Country = Country;
		this.Quantity = Quantity;
	}

	public Integer getPublicationID() {
		return this.PublicationID;
	}

	public void setPublicationID(Integer PublicationID) {
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
