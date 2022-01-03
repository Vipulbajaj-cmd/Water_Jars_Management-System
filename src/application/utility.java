package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class utility {
        public static Connection Utility() throws ClassNotFoundException, SQLException {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/water_plant", "root", "Decard@123");
            return con;

        }
    public static ObservableList<users> getDataUsers() throws SQLException, ClassNotFoundException {
        String qry = "SELECT CAUS_FULL_NAME,EMAIL,PHONE_NUM,ADDRESS,JOINING_DATE FROM water_plant.register";
        Connection con = Utility();
        ObservableList<users> list2 = FXCollections.observableArrayList();
        {
            PreparedStatement ps = con.prepareStatement(qry);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list2.add(new users(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            ps.close();
            con.close();
            return list2;
        }
    }

    public static ObservableList<settings> getDataSet() throws SQLException, ClassNotFoundException {
        String qry = "SELECT * FROM water_plant.settings";
        Connection con = Utility();
        ObservableList<settings> list3 = FXCollections.observableArrayList();
        {
            PreparedStatement ps = con.prepareStatement(qry);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list3.add(new settings(rs.getDate(1), rs.getString(2), rs.getString(3)));
            }
            ps.close();
            con.close();
        }

        return list3;
    }

    public static ObservableList<billreport> getDatabillreport(int id) throws SQLException, ClassNotFoundException {
        String qry = "SELECT starting_date,ending_date, amount FROM water_plant.billreport WHERE BILL_ID=?";
        Connection con = Utility();
        ObservableList<billreport> list4 = FXCollections.observableArrayList();
        {
            PreparedStatement ps = con.prepareStatement(qry);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list4.add(new billreport(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
            ps.close();
            con.close();
        }
        return list4;
    }
}


