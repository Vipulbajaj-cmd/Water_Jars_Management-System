package application;

import java.sql.Date;

public class settings {
    String  normal_price,chilled_price;
    Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNormal_price() {
        return normal_price;
    }

    public void setNormal_price(String normal_price) {
        this.normal_price = normal_price;
    }

    public String getChilled_price() {
        return chilled_price;
    }

    public void setChilled_price(String chilled_price) {
        this.chilled_price = chilled_price;
    }

    public settings(Date Date, String Normal_price, String Chilled_price)
    {
        this.date=Date;
        this.normal_price= Normal_price;
        this.chilled_price=Chilled_price;
    }
}
