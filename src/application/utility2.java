package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class utility2 {
    public static Connection utility() throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/water_plant", "root", "Decard@123");
        {
            return con;
        }

    }

   public static ObservableList<orders> getDataUsers() throws SQLException, ClassNotFoundException {
        String qry="SELECT orders.ORDER_ID,register.CAUS_FULL_NAME,orders.NO_OF_JAR,orders.TYPE_OF_JAR,register.ADDRESS,register.PHONE_NUM,orders.TYPE_OF_CUS,orders.AMOUNT,orders.DATE FROM register INNER JOIN orders ON register.ID = orders.ID order by orders.ORDER_ID DESC";
        Connection con = utility();
        ObservableList<orders> list = FXCollections.observableArrayList();
        {
            PreparedStatement ps = con.prepareStatement(qry);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new orders(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)));
            }
            ps.close();
            con.close();
            return list;
        }
    }

    public static ObservableList<report> getDataReport(String sd, String ed) throws SQLException, ClassNotFoundException {
        String qry="SELECT register.CAUS_FULL_NAME, orders.NO_OF_JAR, orders.TYPE_OF_JAR,orders.AMOUNT FROM register INNER JOIN orders ON register.ID = orders.ID where orders.DATE BETWEEN ? AND ?";
        Connection con = utility();
        ObservableList<report> list = FXCollections.observableArrayList();
        {
            PreparedStatement ps = con.prepareStatement(qry);
            ps.setString(1,sd);
            ps.setString(2,ed);


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
              list.add(new report(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            ps.close();
            con.close();
            return list;
        }

    }


    public static ObservableList<report> getDataReport1(String cusname, String sd, String ed) throws SQLException, ClassNotFoundException {
        String qry="SELECT register.CAUS_FULL_NAME,orders.NO_OF_JAR,orders.TYPE_OF_JAR,orders.AMOUNT FROM register INNER JOIN orders ON register.ID = orders.ID WHERE register.CAUS_FULL_NAME= ? AND orders.DATE BETWEEN ? AND ?";
        Connection con = utility();
        ObservableList<report> list = FXCollections.observableArrayList();
        {
            PreparedStatement ps = con.prepareStatement(qry);
            ps.setString(1,cusname);
            ps.setString(2,sd);
            ps.setString(3,ed);


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new report(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            ps.close();
            con.close();
            return list;
        }
    }
}
