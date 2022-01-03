package application;

import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class reportController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private AnchorPane pndailyreport;

    @FXML
    private TextField reportnormal;

    @FXML
    private TextField reportchilled;

    @FXML
    private TextField reportNamount;

    @FXML
    private TextField reportcamount;

    @FXML
    private TextField reporttotal;

    @FXML
    private JFXDatePicker reportdate;

    @FXML
    private AnchorPane pnreport;

    @FXML
    private TableView<report> table_report;

    @FXML
    private TableColumn<report, String> report_customer;

    @FXML
    private TableColumn<report, String> report_noofjar;

    @FXML
    private TableColumn<report, String> report_typeofjar;

    @FXML
    private TableColumn<report, String> report_amount;

    @FXML
    private TextField reporttotal1;

    @FXML
    private JFXDatePicker reportstartdate;

    @FXML
    private JFXDatePicker reportstartdate2;
    ObservableList<report> listR;

    @FXML
    void billreport(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("billreport.fxml"));
        Parent root2 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root2));
        stage.show();
    }

    @FXML
    void btndailyreport(ActionEvent event) {
        Connection con = null;
        PreparedStatement pre = null;
        String date= String.valueOf(reportdate.getValue());
        String st= "Normal Jar";
        String sp="Chilled Jar";
        try {
            //Register JDBC driver
           // Class.forName(dashboardController.JDBC_DRIVER);
            String query1 = "SELECT NO_OF_JAR, AMOUNT FROM water_plant.orders WHERE DATE=? AND TYPE_OF_JAR=?";
            String query2 = "SELECT NO_OF_JAR, AMOUNT FROM water_plant.orders WHERE DATE=? AND TYPE_OF_JAR=?";

            //Open a connection
            System.out.println("Connecting to selected database.....");
            con =utility.Utility();// DriverManager.getConnection(dashboardController.DB_URL, dashboardController.USER, dashboardController.PASS);
            System.out.println("Connection database Successfully");
            //Execute query
            pre = con.prepareStatement(query1);
            pre.setString(1, date);
            pre.setString(2, st);
            ResultSet rs = pre.executeQuery();
            int sum=0;
            int NAmount=0;
            while(rs.next()) {
                sum = Integer.parseInt(rs.getString(1))+ sum;
                NAmount = Integer.parseInt(rs.getString(2))+ NAmount;
            }
            reportnormal.setText(String.valueOf(sum));
            reportnormal.setEditable(false);
            reportNamount.setText(String.valueOf(NAmount));
            reportNamount.setEditable(false);
            pre.close();
            PreparedStatement pre1 = con.prepareStatement(query2);
            pre1.setString(1,date);
            pre1.setString(2,sp);
            ResultSet rs1 = pre1.executeQuery();
            int sum1=0;
            int CAmount=0;
            while(rs1.next()) {
                sum1 = Integer.parseInt(rs1.getString(1))+ sum1;
                CAmount = Integer.parseInt(rs1.getString(2))+ CAmount;
            }
            reportchilled.setText(String.valueOf(sum1));
            reportchilled.setEditable(false);
            reportcamount.setText(String.valueOf(CAmount));
            reportcamount.setEditable(false);
            reporttotal.setText(String.valueOf(NAmount+CAmount));
            reporttotal.setEditable(false);
            pre1.close();
            con.close();

        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void dailyreport(ActionEvent event) {
        pndailyreport.toFront();
    }

    @FXML
    void goback(ActionEvent event) {
        pnreport.toFront();
    }

    @FXML
    void searchreport(ActionEvent event) {
        String sd= String.valueOf(reportstartdate.getValue());
        String ed= String.valueOf(reportstartdate2.getValue());
        try {
            report_customer.setCellValueFactory(new PropertyValueFactory<report, String>("cname"));
            report_noofjar.setCellValueFactory(new PropertyValueFactory<report, String>("numOfJar"));
            report_typeofjar.setCellValueFactory(new PropertyValueFactory<report, String>("typeOfJar"));
            report_amount.setCellValueFactory(new PropertyValueFactory<report, String>("Amount"));


            listR = utility2.getDataReport(sd,ed);

            table_report.setItems(listR);

            int sum = 0;
            for (report item : table_report.getItems()) {
                sum = sum + Integer.parseInt(item.getAmount());
            }

            reporttotal1.setText(String.valueOf(sum));
            reporttotal1.setEditable(false);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
