package library.user;

public class User {
	private String Username;
	private String Password;
	private Integer Type;

	public User(String username, String password, Integer type) {
		super();
		Username = username;
		Password = password;
		Type = type;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public Integer getType() {
		return Type;
	}
}
