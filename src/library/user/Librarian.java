package library.user;

public class Librarian extends Clerk {

    public Librarian() {
        super();
    }

    public Librarian(String StaffID, String Name, boolean Gender, String Username, String Password, String Address, String Email, String Phone, String Position) {
        super(StaffID, Name, Gender, Username, Password, Address, Email, Phone, Position);
    }

    public Librarian(String StaffID, String Name, boolean Gender, String Address, String Email, String Phone, String Position) {
        super(StaffID, Name, Gender, Address, Email, Phone, Position);
    }
}
