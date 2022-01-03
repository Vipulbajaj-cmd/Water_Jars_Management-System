package application;

import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static application.utility.Utility;

public class billController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            autocomplete();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(reportsearch1,complete);
    }

    @FXML
    private TextField reportsearch1;

    @FXML
    private TableView<report> table_report1;

    @FXML
    private TableColumn<report, String> report_customer1;

    @FXML
    private TableColumn<report, String> report_noofjar1;

    @FXML
    private TableColumn<report, String> report_typeofjar1;

    @FXML
    private TableColumn<report, String> report_amount1;

    @FXML
    private TextField pending;

    @FXML
    private JFXDatePicker reportstartdate1;

    @FXML
    private JFXDatePicker reportenddate1;

    @FXML
    private TextField billamount1;

    @FXML
    private TextField paidamount;

    @FXML
    void printbill(ActionEvent event) throws SQLException, ClassNotFoundException, JRException {
        String search= reportsearch1.getText();
        int pendingamount=0;
        pendingamount=Integer.parseInt(pending.getText());
        int paidamu=Integer.parseInt(paidamount.getText());
        String Normal="Normal Jar";
        String Chilled="Chilled Jar";
        String qry="SELECT register.ID,orders.NO_OF_JAR,orders.AMOUNT ,orders.TYPE_OF_CUS FROM register INNER JOIN orders ON register.ID = orders.ID WHERE register.CAUS_FULL_NAME= ? AND orders.TYPE_OF_JAR=? AND orders.DATE BETWEEN ? AND ?";
        String qry1="SELECT orders.NO_OF_JAR,orders.AMOUNT  FROM register INNER JOIN orders ON register.ID = orders.ID WHERE register.CAUS_FULL_NAME= ? AND orders.TYPE_OF_JAR=? AND orders.DATE BETWEEN ? AND ?";
        Connection con = utility.Utility();//DriverManager.getConnection(dashboardController.DB_URL, dashboardController.USER, dashboardController.PASS);
        PreparedStatement pstmt1 = con.prepareStatement(qry);
        pstmt1.setString(1, reportsearch1.getText());
        pstmt1.setString(2, Normal);
        pstmt1.setString(3, String.valueOf(reportstartdate1.getValue()));
        pstmt1.setString(4, String.valueOf(reportenddate1.getValue()));
        ResultSet row = pstmt1.executeQuery();
        int no_of_jar=0 , amount=0;
        String type_of_cus=" ";
        int ID = 0;
        while(row.next()){
            ID=row.getInt(1);
            no_of_jar= Integer.parseInt(row.getString(2))+ no_of_jar;
            amount= Integer.parseInt(row.getString(3))+ amount;
            type_of_cus=row.getString(4);
        }
        System.out.println(ID+" "+ no_of_jar+ " "+amount+" "+ type_of_cus);
        pstmt1.close();
        PreparedStatement pstmt = con.prepareStatement(qry1);
        pstmt.setString(1, reportsearch1.getText());
        pstmt.setString(2, Chilled);
        pstmt.setString(3, String.valueOf(reportstartdate1.getValue()));
        pstmt.setString(4, String.valueOf(reportenddate1.getValue()));
        ResultSet row1 = pstmt.executeQuery();
        int no_of_jar1=0 , amount1=0;
        while(row1.next()){
            no_of_jar1= Integer.parseInt(row1.getString(1))+ no_of_jar1;
            amount1= Integer.parseInt(row1.getString(2))+ amount1;
        }
        pstmt.close();

        int actualamount=0;
        actualamount= pendingamount + amount + amount1 - paidamu;
        //System.out.println(String.valueOf(getClass().getResource("Blank_A45.jrxml")));
       // String reportSrcFile = "application/Blank_A45.jrxml";
        InputStream inputStream =getClass().getResourceAsStream("/application/Blank_A45.jrxml");
        System.out.println(inputStream);
       // String absolutePath = file.getAbsolutePath();
       // System.out.println(absolutePath);
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        System.out.println("!!!!!");
       JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
        System.out.println("@@@@@@");
        String documents=new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        System.out.println(documents);
        JasperCompileManager.compileReportToFile(jasperDesign,documents+"\\bill1.jasper");
        System.out.println("success");
     //  JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
        JRDataSource jrDataSource=new JREmptyDataSource();

        Map<String, Object> parameters= new HashMap<String, Object>();
        parameters.put("customername",search);
        parameters.put("particular",Normal);
        parameters.put("quantity",String.valueOf(no_of_jar));
        parameters.put("amount",String.valueOf(amount));
        parameters.put("particular1",Chilled);
        parameters.put("quality1",String.valueOf(no_of_jar1));
        parameters.put("amount1",String.valueOf(amount1));
        parameters.put("totalamount", String.valueOf(billamount1.getText()));
        parameters.put("Balance",String.valueOf(paidamu));
        parameters.put("customertype",type_of_cus);
        parameters.put("previousB",String.valueOf(pendingamount));
        parameters.put("CurrentB",String.valueOf(actualamount));
       /* ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add((parameters));
        System.out.println("Opening file preview...");
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);*/
        JasperPrint jasperPrint=JasperFillManager.fillReport(documents+"\\bill1.jasper", parameters,jrDataSource);
        JasperViewer.viewReport(jasperPrint,false);
       // JasperExportManager.exportReportToPdfFile(jasperPrint,"E:\\Library\\Debugged\\Java\\Granti_Aqua\\Blank_A45.pdf");

        String qry2="UPDATE water_plant.register SET BALANCE=? WHERE CAUS_FULL_NAME= ?";
        PreparedStatement pstmt2 = con.prepareStatement(qry2);
        pstmt2.setString(1, String.valueOf(actualamount));
        pstmt2.setString(2, reportsearch1.getText());
        int row2 = pstmt2.executeUpdate();
        pstmt2.close();

        String qry3="INSERT INTO water_plant.billreport(bill_id,starting_date,ending_date,amount)VALUES (?,?,?,?)";
        PreparedStatement pstmt3 = con.prepareStatement(qry3);
        pstmt3.setInt(1, ID);
        pstmt3.setString(2, String.valueOf(reportstartdate1.getValue()));
        pstmt3.setString(3, String.valueOf(reportenddate1.getValue()));
        pstmt3.setString(4, String.valueOf(paidamu));
        int row3 = pstmt3.executeUpdate();
        pstmt3.close();
        con.close();
    }

    @FXML
    void searchbill(ActionEvent event) {
        paidamount.clear();
        String cusname = reportsearch1.getText();
        String sd = String.valueOf(reportstartdate1.getValue());
        String ed = String.valueOf(reportenddate1.getValue());
        if (cusname.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Billing");
            alert.setHeaderText(null);
            alert.setContentText("Please enter customer name");
            alert.showAndWait();
        } else if ((sd.equals("null") || ed.equals("null"))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Billing");
            alert.setHeaderText(null);
            alert.setContentText("Please select dates");
            alert.showAndWait();
        } else {

            try {
                report_customer1.setCellValueFactory(new PropertyValueFactory<report, String>("cname"));
                report_noofjar1.setCellValueFactory(new PropertyValueFactory<report, String>("numOfJar"));
                report_typeofjar1.setCellValueFactory(new PropertyValueFactory<report, String>("typeOfJar"));
                report_amount1.setCellValueFactory(new PropertyValueFactory<report, String>("Amount"));


                ObservableList<report> listB = utility2.getDataReport1(cusname, sd, ed);

                table_report1.setItems(listB);
                int sum = 0;
                for (report item : table_report1.getItems()) {
                    sum = sum + Integer.parseInt(item.Amount);
                }

                billamount1.setText(String.valueOf(sum));
                billamount1.setEditable(false);
                Connection con = utility.Utility();
                String qry = "SELECT BALANCE FROM water_plant.register WHERE CAUS_FULL_NAME=?";
                //con = DriverManager.getConnection(dashboardController.DB_URL,dashboardController.USER,dashboardController.PASS);
                PreparedStatement pstmt1 = con.prepareStatement(qry);
                pstmt1.setString(1, cusname);
                ResultSet row = pstmt1.executeQuery();
                String pendingamount = "null";
                while (row.next()) {
                    pendingamount = row.getString(1);
                }
                if (pendingamount == null) {
                    pending.setText("0");
                } else {
                    pending.setText(pendingamount);
                }
                pending.setEditable(false);
                pstmt1.close();
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public ObservableList<String> complete= FXCollections.observableArrayList();
    void autocomplete() throws SQLException, ClassNotFoundException {
        Connection con = Utility();
        String query = "SELECT CAUS_FULL_NAME FROM water_plant.register";
        PreparedStatement pst1 = con.prepareStatement(query);
        ResultSet row = pst1.executeQuery();
        while (row.next()){
            String input= row.getString(1);
            System.out.println(input);
            complete.add(input);
        }
    }
}
