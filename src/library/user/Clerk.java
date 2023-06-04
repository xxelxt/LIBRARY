package library.user;

public class Clerk extends Info {
    private String StaffID;

    public Clerk() {

    }
    
    public Clerk(String Name, boolean Gender, String Username, String Password, String Address, String Email, String Phone,
                 String StaffID) {
        super(Name, Gender, Username, Password, Address, Email, Phone);
        this.StaffID = StaffID;
    }

    public String getStaffID() {
        return this.StaffID;
    }

    public void setStaffID(String StaffID) {
        this.StaffID = StaffID;
    }

}
