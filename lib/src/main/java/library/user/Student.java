package library.user;

public class Student extends Person {
	private String StudentID;
	private String ClassName;
	private boolean FineStatus;
	private int Fine;

	public String getUsername() {
	    return this.getAccount().getUsername();
	}

	public String getPassword() {
	    return this.getAccount().getPassword();
	}

	public Student(String StudentID, String Name, String ClassName, boolean Gender, String Email, String Phone, String Address,
			 boolean FineStatus, int Fine, User account) {
		super(Name, Gender, Address, Email, Phone, account);
		this.StudentID = StudentID;
		this.ClassName = ClassName;
		this.FineStatus = FineStatus;
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

	public boolean isFineStatus() {
		return this.FineStatus;
	}

	public void setFineStatus(boolean FineStatus) {
		this.FineStatus = FineStatus;
	}

	public int getFine() {
		return this.Fine;
	}

	public void setFine(int Fine) {
		this.Fine = Fine;
	}
	
	// For table setColFactory (automatic map string "fineStatusText" with the method below)
	public String getFineStatusText() {
		return (FineStatus ? "Bị phạt" : "Không bị phạt");
	}
}
