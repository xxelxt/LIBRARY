package library.user;

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
}
