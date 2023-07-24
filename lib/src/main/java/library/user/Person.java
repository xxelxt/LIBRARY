package library.user;

public abstract class Person {
	private String Name;
	private boolean Gender;
    private String Address;
    private String Email;
    private String Phone;
    private User account;
    private String ImageURL;

	public User getAccount() {
		return account;
	}

	public void setAccount(User account) {
		this.account = account;
	}

	public Person() {

	}

	public Person(String Name, boolean Gender, String Address, String Email, String Phone, User account) {
		super();
		this.Name = Name;
		this.Gender = Gender;
		this.Address = Address;
		this.Email = Email;
		this.Phone = Phone;
		this.account = account;
	}

	public String getName() {
		return this.Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public boolean getGender() {
		return this.Gender;
	}

	public void setGender(boolean Gender) {
		this.Gender = Gender;
	}

	public String getAddress() {
		return this.Address;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public String getEmail() {
		return this.Email;
	}

	public void setEmail(String Email) {
		this.Email = Email;
	}

	public String getPhone() {
		return this.Phone;
	}

	public void setPhone(String Phone) {
		this.Phone = Phone;
	}

	public String getGenderText() {
		return (Gender ? "Nữ" : "Nam");
	}

	public String getImageURL() {
		return this.ImageURL;
	}

	public void setImageURL(String ImageURL) {
		this.ImageURL = ImageURL;
	}
}
