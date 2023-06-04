package library.user;

public class Info extends User {
    private String Address;
    private String Email;
    private String Phone;

    public Info() {

    }

    public Info(String Name, String Gender, String Username, String Password, String Address, String Email, String Phone) {
        super(Name, Gender, Username, Password);
        this.Address = Address;
        this.Email = Email;
        this.Phone = Phone;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return this.Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
    
}