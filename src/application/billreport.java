package application;

public class billreport {
    private String sd;
    private String ed;
    private String amount;
    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount=amount;
    }

    public billreport(String sd, String ed, String amount) {
        this.sd = sd;
        this.ed = ed;
        this.amount = amount;
    }
}
