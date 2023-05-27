package library.user;

public class Student extends Person {
	private String StudentID;
	private String ClassName;
	private double Fine;

	public Student(String Username, String Password, String Name, String Gender, String Address, String Phone, String StudentID, String ClassName, double Fine) {
		super(Username, Password, Name, Gender, Address, Phone);
		this.StudentID = StudentID;
		this.ClassName = ClassName;
		this.Fine = Fine;
	}

	public String getStudentID() {
		return this.StudentID;
	}

	public void setStudentID(String StudentID) {
		this.StudentID = StudentID;
	}
	public String getClassName() {
		return this.ClassName;
	}

	public void setClassName(String ClassName) {
		this.ClassName = ClassName;
	}

	public double getFine() {
		return this.Fine;
	}

	public void setFine(double Fine) {
		this.Fine = Fine;
	}
	
	
	
	
	/* commented on line 42 27/5 */
	
}
