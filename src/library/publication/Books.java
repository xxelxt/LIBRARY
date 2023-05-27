package library.publication;

import java.sql.Date;

public class Books extends Publication {
	
	private String Author;
	private String Category;
	private int Reissue;
	private String Publisher;

	public Books(int PublicationID, String Title, Date ReleaseDate, String Country, int Quantity, String Author, String Category, int Reissue, String Publisher) {
		super(PublicationID, Title, ReleaseDate, Country, Quantity);
		this.Author = Author;
		this.Category = Category;
		this.Reissue = Reissue;
		this.Publisher = Publisher;
	}


	public String getAuthor() {
		return this.Author;
	}

	public void setAuthor(String Author) {
		this.Author = Author;
	}

	public String getCategory() {
		return this.Category;
	}

	public void setCategory(String Category) {
		this.Category = Category;
	}

	public int getReissue() {
		return this.Reissue;
	}

	public void setReIssue(int Reissue) {
		this.Reissue = Reissue;
	}

	public String getPublisher() {
		return this.Publisher;
	}

	public void setPublisher(String Publisher) {
		this.Publisher = Publisher;
	}
	

}
