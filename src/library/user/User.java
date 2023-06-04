package library.user;

import java.util.ArrayList;
import java.util.List;

import library.mysql.Database;
import library.publication.Books;

public class User extends Person {
	private String Username;
	private String Password;

    public User() {
        super();
        this.Username = " ";
        this.Password = " ";
    }

    public User(String Username, String Password) {
        this.Username = Username;
        this.Password = Password;
    }

    public User(String Name, boolean Gender) {
        super(Name, Gender);
    }

    public User(String Name, boolean Gender, String Username, String Password) {
        super(Name, Gender);
        this.Username = Username;
        this.Password = Password;
    }

    public String getUsername() {
        return this.Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public ArrayList<Books> searchBookbyTitle(String title) {
        ArrayList<Books> booksList = new ArrayList<>();
        Database db = new Database();
        booksList = db.searchBookbyTitle(title);
        return booksList;
    }

    public ArrayList<Books> searchBookbyCategory(String category) {
        ArrayList<Books> booksList = new ArrayList<>();
        Database db = new Database();
        booksList = db.searchBookbyCategory(category);
        return booksList;
    }

    public ArrayList<Books> searchBookbyAuthor(String author) {
        ArrayList<Books> booksList = new ArrayList<>();
        Database db = new Database();
        booksList = db.searchBookbyAuthor(author);
        return booksList;
    }

    ////

    public ArrayList<Books> searchBookByCategories(List<String> categories) {
        ArrayList<Books> booksList = new ArrayList<>();
        Database db = new Database();
        booksList = db.searchBookByCategories(categories);
        return booksList;
    }
    
}
