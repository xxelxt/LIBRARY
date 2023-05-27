package library.user;

public class Person extends Users {
	private String Name;
	private String Gender;
	private String Address;
	private String Phone;

	public Person(String Username, String Password, String Name, String Gender, String Address, String Phone) {
		super(Username, Password);
		this.Name = Name;
		this.Gender = Gender;
		this.Address = Address;
		this.Phone = Phone;
	}

	public String getName() {
		return this.Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getGender() {
		return this.Gender;
	}

	public void setGender(String Gender) {
		this.Gender = Gender;
	}

	public String getAddress() {
		return this.Address;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public String getPhone() {
		return this.Phone;
	}

	public void setPhone(String Phone) {
		this.Phone = Phone;
	}

}
