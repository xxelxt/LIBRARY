package library.publication;

import java.sql.Date;

public class Books extends Publication {
	
	private String Author;
	private String Genre;
	private int ReIssue;
	private String Publisher;

	public Books(int PublicationID, String Title, Date ReleaseDate, String Country, int Quantity, String Author, String Genre, int ReIssue, String Publisher) {
		super(PublicationID, Title, ReleaseDate, Country, Quantity);
		this.Author = Author;
		this.Genre = Genre;
		this.ReIssue = ReIssue;
		this.Publisher = Publisher;
	}


	public String getAuthor() {
		return this.Author;
	}

	public void setAuthor(String Author) {
		this.Author = Author;
	}

	public String getGenre() {
		return this.Genre;
	}

	public void setGenre(String Genre) {
		this.Genre = Genre;
	}

	public int getReIssue() {
		return this.ReIssue;
	}

	public void setReIssue(int ReIssue) {
		this.ReIssue = ReIssue;
	}

	public String getPublisher() {
		return this.Publisher;
	}

	public void setPublisher(String Publisher) {
		this.Publisher = Publisher;
	}
	

}
