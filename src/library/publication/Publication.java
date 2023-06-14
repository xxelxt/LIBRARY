package library.publication;

import java.util.Date;

import library.mysql.Database;

public class Publication {
	private Integer PublicationID;
	private String Title;
	private Date ReleaseDate;
	private String Country;
	private int Quantity;

	public Publication() {
		this.PublicationID = 0;
		this.Title = " ";
		this.ReleaseDate = null;
		this.Country = " ";
		this.Quantity = -1;
	}

	public Publication(Integer PublicationID, String Title, Date ReleaseDate, String Country, int Quantity) {
		this.PublicationID = PublicationID;
		this.Title = Title;
		this.ReleaseDate = ReleaseDate;
		this.Country = Country;
		this.Quantity = Quantity;
	}

	public Publication(String Title, Date ReleaseDate) {
		this.Title = Title;
		this.ReleaseDate = ReleaseDate;
	}

	public Publication(String Title) {
		this.Title = Title;
	}

	public Integer getPublicationID() {
		return this.PublicationID;
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

	public boolean CheckAvailable(String PublicationID) {
        Database db = new Database();
        int available = db.getPublicationQuantity(PublicationID);
        if (available <= 0) return false;
			else return true;
    }

	public void Decrease() {
        if (Quantity > 0) {
            this.Quantity = this.Quantity - 1;
            Database db = new Database();
            db.setPublicationQuantity(PublicationID, this.Quantity);
        }
    }

    public void Increase() {
        this.Quantity = this.Quantity - 1;
        Database db = new Database();
        db.setPublicationQuantity(PublicationID, this.Quantity);
    }
}
