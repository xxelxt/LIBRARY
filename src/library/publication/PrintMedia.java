package library.publication;

import java.sql.Date;

public class PrintMedia extends Publication {

	private int ReleaseNumber;
	private String PrintType;

	public PrintMedia(Integer MediaID, String Title, Date ReleaseDate, String Country, int Quantity, int ReleaseNumber, String PrintType) {
		super(MediaID, Title, ReleaseDate, Country, Quantity);
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
