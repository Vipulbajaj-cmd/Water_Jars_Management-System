package application;

public class users
{
    private String caust_name;
    private String Email;
    private String Phone_Num;
    private String Address;
    private String date;
    private String Deposit_Amt;

    public String getCaust_name() {
        return caust_name;
    }

    public void setCaust_name(String caust_name) {
        this.caust_name = caust_name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone_Num() {
        return Phone_Num;
    }

    public void setPhone_Num(String Phone_Num) {
        this.Phone_Num = Phone_Num;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeposit_Amt() {
        return Deposit_Amt;
    }

    public void setDeposit_Amt(String deposit_Amt) {
        this.Deposit_Amt = deposit_Amt;
    }

    public users(String caust_name, String email, String phone_Num, String address, String date) {
        this.caust_name = caust_name;
        this.Email = email;
        this.Phone_Num = phone_Num;
        this.Address = address;
        this.date = date;
    }
}
