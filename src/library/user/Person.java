package library.user;

public abstract class Person {
	private String Name;
	private boolean Gender;
	
	public Person() {

	} 

	public Person(String Name, boolean Gender) {
		this.Name = Name;
		this.Gender = Gender;
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
}
