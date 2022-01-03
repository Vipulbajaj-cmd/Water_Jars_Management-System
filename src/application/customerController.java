package application;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class customerController implements Initializable {

    @FXML
    private AnchorPane paneregister;

    @FXML
    private TextField EMAIL;

    @FXML
    private TextField CNAME;

    @FXML
    private TextField PHONE_NUM;

    @FXML
    private TextField ADDRESS;

    @FXML
    private TextField DAMOUNT;

    @FXML
    private JFXButton btnregister;

    @FXML
    private JFXButton registercancel;

    @FXML
    private JFXDatePicker date11;

    @FXML
    private JFXButton btnupda;

    @FXML
    private AnchorPane pnaddcustomer;

    @FXML
    private TableView<users> table_users;

    @FXML
    private TableColumn<users, String> name;

    @FXML
    private TableColumn<users, String> email;

    @FXML
    private TableColumn<users, String> phone;

    @FXML
    private TableColumn<users, String> address;

    @FXML
    private TableColumn<users, String> date;

    @FXML
    private JFXButton btnaddcus;

    @FXML
    private JFXTextField currentdate;

    @FXML
    private Label totalcustomer;

    /*static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/water_plant";
    static final String USER = "root";
    static final String PASS = "Decard@123";*/
    String updatename;

    @FXML
    public void addcuscancel(javafx.event.ActionEvent event) {
        pnaddcustomer.toFront();
        CNAME.clear();
        PHONE_NUM.clear();
        EMAIL.clear();
        DAMOUNT.clear();
        ADDRESS.clear();
    }

    @FXML
    public void addcustomer(javafx.event.ActionEvent event) {
        try {
            date11.setValue(LocalDate.now());
            paneregister.toFront();
            btnupda.setVisible(false);
        } catch (Exception e) {
            System.out.println("Cant load the window");
        }
    }

    @FXML
    public void delete(javafx.event.ActionEvent event) throws SQLException, ClassNotFoundException {
       // Class.forName(JDBC_DRIVER);
        Connection con = utility.Utility();//DriverManager.getConnection(DB_URL, USER, PASS);
        int selectedIndex = table_users.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            users selectedItem = table_users.getSelectionModel().getSelectedItem();
            String name = selectedItem.getCaust_name();
           String query = "SET FOREIGN_KEY_CHECKS=OFF";
            PreparedStatement pst = con.prepareStatement(query);
            pst.executeQuery();
            pst.close();
            String query1 = "DELETE FROM water_plant.register WHERE CAUS_FULL_NAME = ?";
            PreparedStatement pst1 = con.prepareStatement(query1);
            pst1.setString(1, name);
            pst1.executeUpdate();
            pst1.close();
           /* String query2 = "SET FOREIGN_KEY_CHECKS=ON";
            PreparedStatement pst2 = con.prepareStatement(query2);
            pst2.executeQuery();
            pst2.close();
            con.close();*/
            table_users.getItems().remove(selectedIndex);
            register_customer_table();
            //JOptionPane.showMessageDialog(null,"Deleted Successfully");
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Granti Aqua");
            alert.setHeaderText(null);
            alert.setContentText("Customer Deleted Successfully");
            alert.showAndWait();
            // pst.close();
            con.close();
            totalcustomer();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR:");
            alert.setHeaderText("No selection was made.");
            alert.setContentText("You have not selected an item to delete. Please try again.");
            alert.showAndWait();
        }

    }

    @FXML
    public void registercustomer(javafx.event.ActionEvent event) {
        String email="-";
        validate v= new validate();
        if (v.validateEmail(EMAIL.getText()) & v.validatePhoneNo(PHONE_NUM.getText())) {
            try {
               // long startTime = System.nanoTime();
               // Class.forName(JDBC_DRIVER);
                String qry = "INSERT INTO water_plant.register (CAUS_FULL_NAME,EMAIL,PHONE_NUM,ADDRESS,JOINING_DATE,DEPOSIT_AMOUNT) VALUES (?,?,?,?,?,?)";
                Connection con =utility.Utility();// DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pstmt = con.prepareStatement(qry);
                pstmt.setString(1, CNAME.getText());
                if(EMAIL.getText().equals(""))
                    pstmt.setString(2,email);
                else
                    pstmt.setString(2, EMAIL.getText());
                pstmt.setString(3, PHONE_NUM.getText());
                pstmt.setString(4, ADDRESS.getText());
                pstmt.setDate(5, Date.valueOf(date11.getValue()));
                pstmt.setString(6, DAMOUNT.getText());
                int row = pstmt.executeUpdate();
                //long endTime = System.nanoTime();
                //long duration = (endTime - startTime);
                //System.out.println("duration" + duration);
                //System.out.println(row);
                pstmt.close();
                con.close();
                //JOptionPane.showMessageDialog(null,"Customer Registered");
                Alert alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Granti Aqua");
                alert.setHeaderText(null);
                alert.setContentText("Customer Registered Successfully");
                alert.showAndWait();
                //System.out.println("praful");
                register_customer_table();
                pnaddcustomer.toFront();
                totalcustomer();
                CNAME.clear();
                PHONE_NUM.clear();
                EMAIL.clear();
                DAMOUNT.clear();
                ADDRESS.clear();
                //orderController o=new orderController();
                //o.autocomplete();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                Alert alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Granti Aqua");
                alert.setHeaderText(null);
                alert.setContentText("Customer is Already Present");
                alert.showAndWait();
                pnaddcustomer.toFront();
                //throwables.printStackTrace();

            }
        }
    }

    @FXML
    public void update(javafx.event.ActionEvent event){
        btnupda.setVisible(true);
        try {
            users selectedItem = table_users.getSelectionModel().getSelectedItem();
            updatename = selectedItem.getCaust_name();
          //  Class.forName(JDBC_DRIVER);
            String qry = "SELECT CAUS_FULL_NAME,EMAIL,PHONE_NUM,ADDRESS,JOINING_DATE,DEPOSIT_AMOUNT FROM water_plant.register WHERE CAUS_FULL_NAME=?";
            Connection con = utility.Utility();//DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = con.prepareStatement(qry);
            pstmt.setString(1, updatename);
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                CNAME.setText(rs.getString(1));
                EMAIL.setText(rs.getString(2));
                PHONE_NUM.setText(rs.getString(3));
                ADDRESS.setText(rs.getString(4));
                date11.setValue(LocalDate.parse(rs.getString(5)));
                DAMOUNT.setText(rs.getString(6));
            }
               pstmt.close();
                con.close();
            paneregister.toFront();
            btnregister.setVisible(false);
            registercancel.setVisible(false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void updateregister(javafx.event.ActionEvent event) {
        try {
          //  Class.forName(JDBC_DRIVER);
            String qry = "UPDATE water_plant.register SET CAUS_FULL_NAME=?,EMAIL=?,PHONE_NUM=?,ADDRESS=?,JOINING_DATE=?,DEPOSIT_AMOUNT=? WHERE CAUS_FULL_NAME=?";
            Connection con = utility.Utility();//DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pstmt = con.prepareStatement(qry);
            pstmt.setString(1, CNAME.getText());
            pstmt.setString(2, EMAIL.getText());
            pstmt.setString(3, PHONE_NUM.getText());
            pstmt.setString(4, ADDRESS.getText());
            pstmt.setDate(5, Date.valueOf(date11.getValue()));
            pstmt.setString(6, DAMOUNT.getText());
            pstmt.setString(7, updatename);
            int row = pstmt.executeUpdate();
            pstmt.close();
            con.close();
            //JOptionPane.showMessageDialog(null,"Customer Updated");
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Granti Aqua");
            alert.setHeaderText(null);
            alert.setContentText("Customer Updated Successfully");
            alert.showAndWait();
            register_customer_table();
            pnaddcustomer.toFront();
            CNAME.clear();
            PHONE_NUM.clear();
            EMAIL.clear();
            DAMOUNT.clear();
            ADDRESS.clear();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        registercancel.setVisible(true);
        register_customer_table();
        btnregister.setVisible(true);
        btnupda.setVisible(false);
    }

    public void register_customer_table(){
        try {
            name.setCellValueFactory(new PropertyValueFactory<users, String>("caust_name"));
            email.setCellValueFactory(new PropertyValueFactory<users, String>("Email"));
            phone.setCellValueFactory(new PropertyValueFactory<users, String>("Phone_Num"));
            address.setCellValueFactory(new PropertyValueFactory<users, String>("Address"));
            date.setCellValueFactory(new PropertyValueFactory<users, String>("date"));


            ObservableList<users> listM = utility.getDataUsers();

            table_users.setItems(listM);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void totalcustomer() throws SQLException, ClassNotFoundException {
       // Class.forName(JDBC_DRIVER);
        String qry = "SELECT COUNT(*) from water_plant.register";
        Connection con = utility.Utility();//DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement pstmt = con.prepareStatement(qry);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            totalcustomer.setText(rs.getString(1));
        }
        con.close();
        pstmt.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            totalcustomer();
            currentdate.setText(String.valueOf(LocalDate.now()));
            currentdate.setEditable(false);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        register_customer_table();
    }
}

