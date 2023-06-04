package library.user;

public class Librarian extends Clerk {
    
    public Librarian() {
        super();
    }

    public Librarian(String Name, boolean Gender, String Username, String Password, String Address, String Email, String Phone,
                     String StaffID) {
        super(Name, Gender, Username, Password, Address, Email, Phone, StaffID);
    }

    public Librarian(String Name, boolean Gender, String Address, String Email, String Phone,
                     String StaffID) {
        super(Name, Gender, Address, Email, Phone, StaffID);
    }
}
