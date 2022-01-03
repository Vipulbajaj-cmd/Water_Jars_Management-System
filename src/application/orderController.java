package application;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.utility.Utility;

public class orderController implements Initializable {

    @FXML
    private AnchorPane pnorder;

    @FXML
    private TableView<orders> table_orders;

    @FXML
    private TableColumn<orders, Integer> oid;

    @FXML
    private TableColumn<orders, String> ocname;

    @FXML
    private TableColumn<orders, String> NumOfJar;

    @FXML
    private TableColumn<orders, String> TypeOfJar;

    @FXML
    private TableColumn<orders, String> oaddress;

    @FXML
    private TableColumn<orders, String> ophone;

    @FXML
    private TableColumn<orders, String> otypeofcus;

    @FXML
    private TableColumn<orders, String> oamount;

    @FXML
    private TableColumn<orders, String> odate11;

    @FXML
    private AnchorPane pnplaceorder;

    @FXML
    private TextField placecaus;

    @FXML
    private TextField placephone;

    @FXML
    private TextField placeemail;

    @FXML
    private TextField placeadress;

    @FXML
    private JFXRadioButton placedaily;

    @FXML
    private JFXRadioButton placecustom;

    @FXML
    private CustomTextField autotxtfield;

    @FXML
    private TextField placenoofjar;

    @FXML
    private JFXRadioButton placetypeofjar1;

    @FXML
    private JFXRadioButton placetypeofjar2;

    @FXML
    private TextField placeamount;

    @FXML
    private JFXDatePicker orderdate;
   /* static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/water_plant";
    static final String USER = "root";
    static final String PASS = "Decard@123";*/

    int ID = 0;
    ObservableList<orders> listO;

    @FXML
    void changecmount(javafx.event.ActionEvent event) {
       // System.out.println(event.getText());
        if (placetypeofjar1.isSelected()) {
            int i = Integer.parseInt(placenoofjar.getText());
            placeamount.setText(String.valueOf(i * settingController.normalprice));
        } else if (placetypeofjar2.isSelected()) {
            int i = Integer.parseInt(placenoofjar.getText());
            placeamount.setText(String.valueOf(i * settingController.chilledprice));
        }
    }

    @FXML
    void deleteorder(javafx.event.ActionEvent event) throws SQLException, ClassNotFoundException {
        Connection con = Utility();
        int selectedIndex = table_orders.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            orders selectedItem = (orders) table_orders.getSelectionModel().getSelectedItem();
            int order_id = selectedItem.getId();
            String query = "DELETE FROM water_plant.orders WHERE ORDER_ID = ?";
            PreparedStatement pst1 = con.prepareStatement(query);
            pst1.setInt(1, order_id);
            int row = pst1.executeUpdate();
            table_orders.getItems().remove(selectedIndex);
            pst1.close();
            con.close();
            //JOptionPane.showMessageDialog(null, "Deleted Successfully");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Granti Aqua");
            alert.setHeaderText(null);
            alert.setContentText("Order Deleted Successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR:");
            alert.setHeaderText("No selection was made.");
            alert.setContentText("You have not selected an item to delete. Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    void order(javafx.event.ActionEvent event) {
        pnplaceorder.toFront();
        orderdate.setValue(LocalDate.now());
        placecaus.setEditable(true);
        placeemail.setEditable(true);
        placeadress.setEditable(true);
        placephone.setEditable(true);
    }

    @FXML
    void ordercancel(javafx.event.ActionEvent event) {
        pnorder.toFront();
        clear();
    }

    @FXML
    void placeorderss(javafx.event.ActionEvent event) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        //finally block is used to close resource
        validate v=new validate();

            String type_jar = null;
            if (placetypeofjar1.isSelected()) {
                type_jar = placetypeofjar1.getText();
            } else if (placetypeofjar2.isSelected()) {
                type_jar = placetypeofjar2.getText();
            } else {
                //JOptionPane.showMessageDialog(null, "Select type of jar");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Granti Aqua");
                alert.setHeaderText(null);
                alert.setContentText("Please Select Type Of Jar");
                alert.showAndWait();
            }

            if (placedaily.isSelected()) {
                // Class.forName(JDBC_DRIVER);
                String qry = "INSERT INTO water_plant.orders(ID,NO_OF_JAR,TYPE_OF_JAR,TYPE_OF_CUS,AMOUNT,DATE) VALUES (?,?,?,?,?,?)";
                con = utility.Utility();//DriverManager.getConnection(DB_URL, USER, PASS);
                pstmt = con.prepareStatement(qry);
                pstmt.setInt(1, ID);
                pstmt.setString(2, placenoofjar.getText());
                pstmt.setString(3, type_jar);
                pstmt.setString(4, placedaily.getText());
                pstmt.setString(5, placeamount.getText());
                pstmt.setDate(6, Date.valueOf(orderdate.getValue()));
                int row = pstmt.executeUpdate();
                pstmt.close();
                con.close();
                // JOptionPane.showMessageDialog(null, "Orders Placed");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Granti Aqua");
                alert.setHeaderText(null);
                alert.setContentText("Order Placed Successfully");
                alert.showAndWait();


            } else if (placecustom.isSelected()) {
                if (v.validateEmail(placeemail.getText()) & v.validatePhoneNo(placephone.getText())) {
                    // Class.forName(JDBC_DRIVER);
                    String qry = "INSERT INTO water_plant.register(CAUS_FULL_NAME,EMAIL,PHONE_NUM,ADDRESS,JOINING_DATE) VALUES(?,?,?,?,?)";
                    con = utility.Utility();//DriverManager.getConnection(DB_URL, USER, PASS);
                    pstmt = con.prepareStatement(qry);
                    pstmt.setString(1, placecaus.getText());
                    pstmt.setString(2, placeemail.getText());
                    pstmt.setString(3, placephone.getText());
                    pstmt.setString(4, placeadress.getText());
                    pstmt.setDate(5, Date.valueOf(orderdate.getValue()));
                    int row = pstmt.executeUpdate();
                    pstmt.close();
                    String qry1 = "INSERT INTO water_plant.orders(ID,NO_OF_JAR,TYPE_OF_JAR,TYPE_OF_CUS,AMOUNT,DATE) VALUES ((SELECT ID FROM water_plant.register WHERE CAUS_FULL_NAME=?),?,?,?,?,?)";
                    pstmt1 = con.prepareStatement(qry1);
                    pstmt1.setString(1, placecaus.getText());
                    pstmt1.setString(2, placenoofjar.getText());
                    pstmt1.setString(3, type_jar);
                    pstmt1.setString(4, placecustom.getText());
                    pstmt1.setString(5, placeamount.getText());
                    pstmt1.setString(6, String.valueOf(orderdate.getValue()));
                    int row1 = pstmt1.executeUpdate();
                    pstmt1.close();
                    con.close();
                    //JOptionPane.showMessageDialog(null, "Orders Placed");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Granti Aqua");
                    alert.setHeaderText(null);
                    alert.setContentText("Order Placed Successfully");
                    alert.showAndWait();
                }
            } else {
                //JOptionPane.showMessageDialog(null, "Select type of customer");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Granti Aqua");
                alert.setHeaderText(null);
                alert.setContentText("Please Select Type of Customer");
                alert.showAndWait();
            }
            orders_customer_table();
            pnorder.toFront();
            clear();

    }
    @FXML
    void putamountchilled(javafx.event.ActionEvent event) {
        placetypeofjar1.setSelected(false);
        int i = Integer.parseInt(placenoofjar.getText());
        placeamount.setText(String.valueOf(i * settingController.chilledprice));
    }

    @FXML
    void putamountnormal(javafx.event.ActionEvent event) {
        placetypeofjar2.setSelected(false);
        int i = Integer.parseInt(placenoofjar.getText());
        placeamount.setText(String.valueOf(i * settingController.normalprice));
    }

    @FXML
    void search(javafx.event.ActionEvent event) {
        Connection con = null;
        PreparedStatement pre = null;
        String email = null, phone = null, add = null;
        try {
            //Register JDBC driver
            //Class.forName(JDBC_DRIVER);
            String query = "SELECT ID,EMAIL,PHONE_NUM, ADDRESS FROM water_plant.register WHERE CAUS_FULL_NAME=?";

            //Open a connection
            System.out.println("Connecting to selected database.....");
            con = utility.Utility();//DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection database Successfully");

            //Execute query
            pre = con.prepareStatement(query);
            pre.setString(1, autotxtfield.getText());
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ID = rs.getInt(1);
                email = rs.getString("EMAIL");
                phone = rs.getString("PHONE_NUM");
                add = rs.getString("ADDRESS");
            }
            pre.close();
            con.close();
            if (email == null) {
                //JOptionPane.showMessageDialog(null, "user not found");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Granti Aqua");
                alert.setHeaderText(null);
                alert.setContentText("User Not Found");
                alert.showAndWait();
            } else {
                placecaus.setText(autotxtfield.getText());
                placecaus.setEditable(false);
                placeemail.setText(email);
                placeemail.setEditable(false);
                placeadress.setText(add);
                placeadress.setEditable(false);
                placephone.setText(phone);
                placephone.setEditable(false);
                placedaily.setSelected(true);
                placecustom.setDisable(true);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void orders_customer_table() {
        try {
            oid.setCellValueFactory(new PropertyValueFactory<orders, Integer>("Id"));
            ocname.setCellValueFactory(new PropertyValueFactory<orders, String>("cname"));
            NumOfJar.setCellValueFactory(new PropertyValueFactory<orders, String>("NumOfJar"));
            TypeOfJar.setCellValueFactory(new PropertyValueFactory<orders, String>("TypeOfJar"));
            ophone.setCellValueFactory(new PropertyValueFactory<orders, String>("phone"));
            oaddress.setCellValueFactory(new PropertyValueFactory<orders, String>("Address"));
            odate11.setCellValueFactory(new PropertyValueFactory<orders, String>("Date"));
            otypeofcus.setCellValueFactory(new PropertyValueFactory<orders, String>("TypeOfCus"));
            oamount.setCellValueFactory(new PropertyValueFactory<orders, String>("Amount"));
            try {
                listO = utility2.getDataUsers();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            table_orders.setItems(listO);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
        public void clear() {
            pnorder.toFront();
            placecaus.clear();
            placephone.clear();
            placeemail.clear();
            placeadress.clear();
            placenoofjar.clear();
            placetypeofjar2.setSelected(false);
            placetypeofjar1.setSelected(false);
            placedaily.setSelected(false);
            placecustom.setSelected(false);
            placecustom.setDisable(false);
            placeamount.clear();
            autotxtfield.clear();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingController s=new settingController();
        try {
            s.productprice();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //String[] possiblewords={"hi","hello","how are you"};
        try {
            autocomplete();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(autotxtfield, complete);
        orders_customer_table();
    }
}
