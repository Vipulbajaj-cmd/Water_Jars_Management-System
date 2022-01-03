package application;

public class report {
    String cname, Amount, NumOfJar, TypeOfJar;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getNumOfJar() {
        return NumOfJar;
    }

    public void setNumOfJar(String numOfJar) {
        this.NumOfJar = numOfJar;
    }

    public String getTypeOfJar() {
        return TypeOfJar;
    }

    public void setTypeOfJar(String typeOfJar) {
        this.TypeOfJar = typeOfJar;
    }

    public report(String cname, String NumOfJar, String TypeOfJar, String Amount) {
        this.cname = cname;
        this.NumOfJar = NumOfJar;
        this.TypeOfJar = TypeOfJar;
        this.Amount = Amount;
    }
}