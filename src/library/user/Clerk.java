package library.user;

public class Clerk extends Info {
    private String StaffID;
    private String Position;

    public Clerk() {
        super();
        this.StaffID = " ";
    }
    
    public Clerk(String StaffID, String Name, boolean Gender, String Username, String Password, String Address, String Email, String Phone,
                 String Position) {
        super(Name, Gender, Username, Password, Address, Email, Phone);
        this.StaffID = StaffID;
        this.Position = Position;
    }

    public Clerk(String StaffID, String Name, boolean Gender, String Address, String Email, String Phone,
                 String Position) {
        super(Name, Gender, Address, Email, Phone);
        this.StaffID = StaffID;
        this.Position = Position;
    }

    public String getStaffID() {
        return this.StaffID;
    }

    public void setStaffID(String StaffID) {
        this.StaffID = StaffID;
    }

}
