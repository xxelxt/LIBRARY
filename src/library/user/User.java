package library.user;

public class User extends Person {
	private String Username;
	private String Password;

    public User() {

    }

    public User(String Username, String Password) {
        this.Username = Username;
        this.Password = Password;
    }

    public User(String Name, String Gender, String Username, String Password) {
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
