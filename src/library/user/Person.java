package library.user;

public abstract class Person {
	private String Name;
	private String Gender;
	
	public Person() {

	} 

	public Person(String Name, String Gender) {
		this.Name = Name;
		this.Gender = Gender;
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
}
