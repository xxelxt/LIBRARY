package library.publication;

import java.util.Date;

import library.mysql.Database;

public abstract class Publication {
	private String PublicationID;
	private String Title;
	private Date ReleaseDate;
	private String Country;
	private int Quantity;

	public Publication() {
		
	}

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
