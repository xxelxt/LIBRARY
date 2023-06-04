package library.user;

public class Librarian extends Clerk {
    
    public Librarian() {

    }

    public Librarian(String Name, String Gender, String Username, String Password, String Address, String Email, String Phone,
                     String StaffID) {
        super(Name, Gender, Username, Password, Address, Email, Phone, StaffID);
    }
}
