package library.user;

public class User {
	private String Username;
	private String Password;
	private Integer Type;

	public User(String Username, String Password, Integer Type) {
		super();
		this.Username = Username;
		this.Password = Password;
		this.Type = Type;
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

	public Integer getType() {
		return this.Type;
	}

	public void setType(Integer Type) {
		this.Type = Type;
	}
}
