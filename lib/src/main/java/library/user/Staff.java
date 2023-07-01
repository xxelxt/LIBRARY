package library.user;

public class Staff extends Person {
    private int StaffID;
    private String Position;

	public String getUsername() {
	    return this.getAccount().getUsername();
	}

	public String getPassword() {
	    return this.getAccount().getPassword();
	}

	public Staff(int StaffID, String Name, boolean Gender, String Email, String Phone, String Address,
			String Position, User account) {
		super(Name, Gender, Address, Email, Phone, account);
		this.StaffID = StaffID;
		this.Position = Position;
	}

	public int getStaffID() {
		return this.StaffID;
	}

	public void setStaffID(int StaffID) {
		this.StaffID = StaffID;
	}

	public String getPosition() {
		return this.Position;
	}

	public void setPosition(String Position) {
		this.Position = Position;
	}
}
