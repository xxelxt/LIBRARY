package library.publication;

import java.util.Date;

public class PrintMedia extends Publication {
	
	private int ReleaseNumber;
	private String PrintType;

	public PrintMedia() {
		super();
		this.ReleaseNumber = -1;
		this.PrintType = " ";
	}

	public PrintMedia(String PublicationID, String Title, Date ReleaseDate, String Country, int Quantity, int ReleaseNumber, String PrintType) {
		super(PublicationID, Title, ReleaseDate, Country, Quantity);
		this.ReleaseNumber = ReleaseNumber;
		this.PrintType = PrintType;
	}

	public PrintMedia(String PublicationID, String Title, Date ReleaseDate, int ReleaseNumber, String PrintType) {
		super(PublicationID, Title, ReleaseDate);
		this.ReleaseNumber = ReleaseNumber;
		this.PrintType = PrintType;
	}
	
	public int getReleaseNumber() {
		return this.ReleaseNumber;
	}

	public void setReleaseNumber(int ReleaseNumber) {
		this.ReleaseNumber = ReleaseNumber;
	}

	public String getPrintType() {
		return this.PrintType;
	}

	public void setPrintType(String PrintType) {
		this.PrintType = PrintType;
	}
	
}
