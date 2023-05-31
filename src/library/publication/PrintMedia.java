package library.publication;

import java.util.Date;

public class PrintMedia extends Publication {
	
	private int ReleaseNumber;

	public PrintMedia(String PublicationID, String Title, Date ReleaseDate, String Country, int Quantity, int ReleaseNumber) {
		super(PublicationID, Title, ReleaseDate, Country, Quantity);
		this.ReleaseNumber = ReleaseNumber;
	}
	
	public int getReleaseNumber() {
		return this.ReleaseNumber;
	}

	public void setReleaseNumber(int ReleaseNumber) {
		this.ReleaseNumber = ReleaseNumber;
	}
	
}
