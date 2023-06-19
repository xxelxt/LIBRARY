package library.user;

public class Staff extends Person {
    private int StaffID;
    private String Position;

	public Staff(int staffID, String name, boolean gender, String email, String phone, String address,
			String position, User account) {
		super(name, gender, address, email, phone, account);
		StaffID = staffID;
		Position = position;
	}

	public String getUsername() {
	    return this.getAccount().getUsername();
	}

	public String getPassword() {
	    return this.getAccount().getPassword();
	}

	public int getStaffID() {
		return StaffID;
	}

	public void setStaffID(int staffID) {
		StaffID = staffID;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}


}
