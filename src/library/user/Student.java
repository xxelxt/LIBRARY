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

	public Student(String studentID, String name, String className, boolean gender, String email, String phone, String address, 
			 boolean fineStatus, int fine, User account) {
		super(name, gender, address, email, phone, account);
		StudentID = studentID;
		ClassName = className;
		FineStatus = fineStatus;
		Fine = fine;
	}

	public String getStudentID() {
		return StudentID;
	}

	public void setStudentID(String studentID) {
		StudentID = studentID;
	}

	public String getClassName() {
		return ClassName;
	}

	public void setClassName(String className) {
		ClassName = className;
	}

	public boolean isFineStatus() {
		return this.FineStatus;
	}

	public void setFineStatus(boolean fineStatus) {
		FineStatus = fineStatus;
	}

	public int getFine() {
		return Fine;
	}

	public void setFine(int fine) {
		Fine = fine;
	}
}
