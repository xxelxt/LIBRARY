package library.user;

public abstract class Person {
	private String Name;
	private boolean Gender = false;
    private String Address = "No address";
    private String Email = "No email";
    private String Phone = "No phone";
    private User account;

	public User getAccount() {
		return account;
	}

	public void setAccount(User account) {
		this.account = account;
	}

	public Person(String name, boolean gender, String address, String email, String phone, User account) {
		super();
		Name = name;
		Gender = gender;
		Address = address;
		Email = email;
		Phone = phone;
		this.account = account;
	}

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public boolean isGender() {
		return Gender;
	}
	public void setGender(boolean gender) {
		Gender = gender;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}


}
