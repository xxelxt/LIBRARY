package library.publication;

import java.sql.Date;
import java.util.ArrayList;

public class Books extends Publication {

	private ArrayList<String> Authors;
	private String Category;
	private int Reissue;
	private String Publisher;

	public Books() {
		super();
		this.Category = " ";
		this.Reissue = -1;
		this.Publisher = " ";
	}

	public Books(String PublicationID, String Title, ArrayList<String> Authors, Date ReleaseDate, String Country, int Quantity, String Category, int Reissue, String Publisher) {
		super(PublicationID, Title, ReleaseDate, Country, Quantity);
		this.Authors = Authors;
		this.Category = Category;
		this.Reissue = Reissue;
		this.Publisher = Publisher;
	}

    public ArrayList<String> getAuthors() {
		return this.Authors;
	}

	public void setAuthors(ArrayList<String> authors) {
		this.Authors = authors;
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
