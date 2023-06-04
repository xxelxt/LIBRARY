package library.user;

public class Student extends Info {
	private String StudentID;
	private String ClassName;
	private boolean FineStatus;
	private double Fine;

	public Student() {

	}

	public Student(String StudentID, String Name, boolean Gender, String Username, String Password, String Address, String Email, String Phone,
				   String ClassName, double Fine, boolean FineStatus) {
		super(Name, Gender, Username, Password, Address, Email, Phone);
		this.StudentID = StudentID;
		this.ClassName = ClassName;
		this.Fine = Fine;
		this.FineStatus = FineStatus;
	}

	public Student(String StudentID, String Name, boolean Gender, String Address, String Email, String Phone,
				   String ClassName, double Fine, boolean FineStatus) {
		super(Name, Gender, Address, Email, Phone);
		this.StudentID = StudentID;
		this.ClassName = ClassName;
		this.Fine = Fine;
		this.FineStatus = FineStatus;
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

	public boolean getFineStatus() {
		return this.FineStatus;
	}

	public void setFineStatus(boolean FineStatus) {
		this.FineStatus = FineStatus;
	}
	
}
