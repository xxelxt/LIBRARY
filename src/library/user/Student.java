package library.user;

public class Student extends Person {
	private String StudentID;
	private String ClassName;
	private boolean FineStatus = false;
	private double Fine = 0;

	public Student(String name, boolean gender, String address, String email, String phone, User account,
			String studentID, String className, boolean fineStatus, double fine) {
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
		return FineStatus;
	}

	public void setFineStatus(boolean fineStatus) {
		FineStatus = fineStatus;
	}

	public double getFine() {
		return Fine;
	}

	public void setFine(double fine) {
		Fine = fine;
	}
}
