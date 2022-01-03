package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class settingController implements Initializable {

    static  int normalprice;
    static  int chilledprice;
    static {
        normalprice=0;
        chilledprice=0;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        price_table();
        try {
            productprice();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private TextField getnormalprice;

    @FXML
    private TextField getchilledprice;

    @FXML
    private TableView<settings> settings_table;

    @FXML
    private TableColumn<settings, Date> settings_date;

    @FXML
    private TableColumn<settings, String> settings_normal;

    @FXML
    private TableColumn<settings, String> settings_chilled;

    ObservableList<settings> listp;
    public void price_table(){
        try {
            settings_date.setCellValueFactory(new PropertyValueFactory<settings, Date>("date"));
            settings_normal.setCellValueFactory(new PropertyValueFactory<settings, String>("normal_price"));
            settings_chilled.setCellValueFactory(new PropertyValueFactory<settings, String>("chilled_price"));

            listp = utility.getDataSet();

            settings_table.setItems(listp);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void productprice() throws ClassNotFoundException, SQLException {
       // Class.forName(dashboardController.JDBC_DRIVER);
        String qry = "select *from settings order by DATE DESC LIMIT 1";
        Connection con = utility.Utility();//DriverManager.getConnection(dashboardController.DB_URL,dashboardController.USER, dashboardController.PASS);
        PreparedStatement pstmt = con.prepareStatement(qry);
        ResultSet rs= pstmt.executeQuery();
        while(rs.next()){
            normalprice=Integer.parseInt(rs.getString(2));
            chilledprice=Integer.parseInt(rs.getString(3));
        }
        pstmt.close();
        con.close();
    }
    @FXML
    void saveprice(ActionEvent event) {
        LocalDate date = java.time.LocalDate.now();
        String normal=getnormalprice.getText();
        String chilled=getchilledprice.getText();
        try {
            //Class.forName(dashboardController.JDBC_DRIVER);
            String qry = "INSERT INTO water_plant.settings"+"(Date,normal_jar,chilled_jar)VALUES"+" (?,?,?)";
            Connection con = utility.Utility();//DriverManager.getConnection(dashboardController.DB_URL, dashboardController.USER, dashboardController.PASS);
            PreparedStatement pstmt = con.prepareStatement(qry);
            pstmt.setString(1, String.valueOf(date));
            pstmt.setString(2, normal);
            pstmt.setString(3, chilled);
            int row = pstmt.executeUpdate();
            pstmt.close();
            con.close();
            //JOptionPane.showMessageDialog(null,"Price Saved!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Granti Aqua");
            alert.setHeaderText(null);
            alert.setContentText("Price Saved Successfully");
            alert.showAndWait();
            getnormalprice.clear();
            getchilledprice.clear();
            price_table();
            productprice();

        } catch (ClassNotFoundException | SQLException e) {
            //JOptionPane.showMessageDialog(null,"Price already register on date please click on Change price");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Granti Aqua");
            alert.setHeaderText(null);
            alert.setContentText("Price Already Registered on that Date Please Click on Change price ");
            alert.showAndWait();
        }
    }
    @FXML
    void changeprice(ActionEvent event) {
        LocalDate date = java.time.LocalDate.now();
        String normal=getnormalprice.getText();
        String chilled=getchilledprice.getText();
        try {
           // Class.forName(dashboardController.JDBC_DRIVER);
            String qry = "UPDATE water_plant.settings SET normal_jar=?,chilled_jar=? WHERE Date=?";
            Connection con = utility.Utility();//DriverManager.getConnection(dashboardController.DB_URL, dashboardController.USER, dashboardController.PASS);
            PreparedStatement pstmt = con.prepareStatement(qry);
            pstmt.setString(1, normal);
            pstmt.setString(2, chilled);
            pstmt.setString(3, String.valueOf(date));
            int row = pstmt.executeUpdate();
            pstmt.close();
            con.close();
            //JOptionPane.showMessageDialog(null,"Price Changed!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Granti Aqua");
            alert.setHeaderText(null);
            alert.setContentText("Price Changed Successfully");
            alert.showAndWait();
            getnormalprice.clear();
            getchilledprice.clear();
            price_table();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
