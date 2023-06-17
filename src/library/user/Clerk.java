package library.user;

public class Clerk extends Person {
    private String StaffID;

	public Clerk(String name, boolean gender, String address, String email, String phone, User account,
			String staffID) {
		super(name, gender, address, email, phone, account);
		StaffID = staffID;
	}

	public String getStaffID() {
		return StaffID;
	}

	public void setStaffID(String staffID) {
		StaffID = staffID;
	}


}
