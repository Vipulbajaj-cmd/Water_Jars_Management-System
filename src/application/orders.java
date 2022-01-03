package application;

public class orders {
    String cname,phone,Date,Address,NumOfJar,TypeOfJar,TypeOfCus,Amount;
    int Id;

    public int getId() {
        return Id;
    }

    public void setId(String cname) {
        this.Id = Id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getNumOfJar() {
        return NumOfJar;
    }

    public void setNumOfJar(String numOfJar) {
        NumOfJar = numOfJar;
    }

    public String getTypeOfJar() {
        return TypeOfJar;
    }

    public void setTypeOfJar(String typeOfJar) {
        TypeOfJar = typeOfJar;
    }


    public String getTypeOfCus() {
        return TypeOfCus;
    }

    public void setTypeOfCus(String typeOfCus) {
        TypeOfCus = typeOfCus;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
    public orders(int Id,String cname, String NumOfJar, String TypeOfJar, String Address, String phone, String TypeOfCus, String Amount, String Date) {
        this.Id=Id;
        this.cname = cname;
        this.phone = phone;
        this.Address=Address;
        this.NumOfJar= NumOfJar;
        this.TypeOfJar = TypeOfJar;
        this.TypeOfCus = TypeOfCus;
        this.Amount = Amount;
        this.Date=Date;


    }
}