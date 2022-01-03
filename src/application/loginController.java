package application;

import com.jaspersoft.ireport.components.spiderchart.properties.BackgroundColorProperty;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.plugin2.util.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static javax.swing.BoxLayout.*;


public class loginController implements Initializable {
    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btngoback;

    @FXML
    private JFXButton btnSignup;

    @FXML
    private Pane pnSignup;

    @FXML
    private Pane pnLogin;

    @FXML
    private JFXTextField txtusername;

    @FXML
    private JFXTextField txtemail;

    @FXML
    private JFXButton setsignup;

    @FXML
    private JFXPasswordField textpassword;


    @FXML
    private JFXTextField txtSecanswer;

    @FXML
    private JFXTextField txtgst;

    @FXML
    private JFXTextField txtpan;

    @FXML
    private JFXPasswordField loginpassword;

    @FXML
    private JFXTextField loginusername;

    @FXML
    private JFXCheckBox foradmin;

    @FXML
    private JFXCheckBox foruser;
    @FXML
    private Label passwordLabel;

   /* static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/water_plant";
    static final String USER = "root";
    static final String PASS = "Decard@123";*/
    public void signupdb(ActionEvent event) {
        Connection con = null;
        PreparedStatement pre = null;
        if (event.getSource() == setsignup) {
            validate v = new validate();
            //finally block is used to close resource
            if (v.validateEmail(txtemail.getText()) & v.validateusername(txtusername.getText()) ){//& v.validatePassword(textpassword.getText())) {
                try {
                    String email = txtemail.getText();
                    String username = txtusername.getText();
                    String password = textpassword.getText();
                    String panno = txtpan.getText();
                    String gstno = txtgst.getText();
                    String securityq = securityQ.getValue();
                    String answer = txtSecanswer.getText();
                    String selection;
                    String admin_verify;
                    if(foradmin.isSelected()){
                         selection="admin";
                         admin_verify="True";

                    }else{
                         selection="user";
                         admin_verify="False";
                    }

                    //Register JDBC driver
                   // Class.forName(JDBC_DRIVER);
                    String query = "INSERT INTO water_plant.login (Email,User_name,Password,pan_no,gst_no,security_question,answer,adminstration_verification,adminORuser) VALUES (?,?,?,?,?,?,?,?,?)";

                    //Open a connection
                    System.out.println("Connecting to selected database.....");
                    con = utility.Utility();//DriverManager.getConnection(DB_URL, USER, PASS);
                    System.out.println("Connection database Successfully");

                    //Execute query
                    pre = con.prepareStatement(query);
                    pre.setString(1, email);
                    pre.setString(2, username);
                    pre.setString(3, password);
                    pre.setString(4, panno);
                    pre.setString(5, gstno);
                    pre.setString(6, securityq);
                    pre.setString(7, answer);
                    pre.setString(8, admin_verify);
                    pre.setString(9, selection);
                    int row = pre.executeUpdate();
                    if (row == 0) {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Login");
                        alert.setHeaderText(null);
                        alert.setContentText("Username Already Exists ");
                        alert.showAndWait();
                    } else {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Granti Aqua");
                        alert.setHeaderText(null);
                        alert.setContentText("Sign up Successfully ");
                        alert.showAndWait();
                        pnLogin.toFront();
                    }
                    pre.close();
                txtemail.clear();
                txtusername.clear();
                txtgst.clear();
                txtpan.clear();
                txtSecanswer.clear();
                textpassword.clear();
                foradmin.setSelected(false);
                foruser.setSelected(false);
                } catch (SQLException se) {
                    se.printStackTrace();
                    System.out.println(se);
                } catch (HeadlessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (pre != null)
                            con.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                    try {
                        if (con != null)
                            con.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }//end finally try
                }//end try
            }
        }
    }

    @FXML
    private JFXCheckBox areyouadmin;

    public void logindb(ActionEvent event) {
        Connection con = null;
        PreparedStatement pre = null;
        if (event.getSource() == btnLogin) {
                String username = loginusername.getText();
                String password = loginpassword.getText();
                try {
                    //Register JDBC driver
                    //Class.forName(JDBC_DRIVER);
                    String query = "SELECT * FROM water_plant.login WHERE User_name=? and Password=?";

                    //Open a connection
                    System.out.println("Connecting to selected database.....");
                    con = utility.Utility();// DriverManager.getConnection(DB_URL, USER, PASS);
                    System.out.println("Connection database Successfully");

                    //Execute query
                    pre = con.prepareStatement(query);
                    pre.setString(1, username);
                    pre.setString(2, password);
                    ResultSet rs = pre.executeQuery();
                    String databaseusername = null;
                    String databasepassword = null;
                    String databaseadminstration_verification = null;
                    String databaseadminuser= null;
                    while (rs.next()) {
                        databaseusername = rs.getString("User_name");
                        databasepassword = rs.getString("Password");
                        databaseadminstration_verification = rs.getString("adminstration_verification");
                        databaseadminuser= rs.getString("adminORuser");
                        System.out.println(databaseadminstration_verification);
                    }
                    if (areyouadmin.isSelected()) {
                        if (username.equals(databaseusername) && password.equals(databasepassword) && databaseadminuser.equals("admin")) {
                            newpanel(event);
                            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Granti Aqua");
                            alert.setHeaderText(null);
                            alert.setContentText("Login Sucessfully");
                            alert.showAndWait();*/
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Granti Aqua");
                            alert.setHeaderText(null);
                            alert.setContentText("You are not Admin!!");
                            alert.showAndWait();
                        }
                    } else {
                        if (username.equals("") && password.equals("")) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Granti Aqua");
                            alert.setHeaderText(null);
                            alert.setContentText("Please Enter Username and Password");
                            alert.showAndWait();
                        } else if (username.equals(databaseusername) && password.equals(databasepassword) && databaseadminstration_verification.equals("True")) {
                            gotodash();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Granti Aqua");
                            alert.setHeaderText(null);
                            alert.setContentText("Login Sucessfully");
                            alert.showAndWait();
                        } else if (!(username.equals(databaseusername) && password.equals(databasepassword))) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Granti Aqua");
                            alert.setHeaderText(null);
                            alert.setContentText("Please Check Username and Passsword");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Granti Aqua");
                            alert.setHeaderText(null);
                            alert.setContentText("Adminstration verification failed");
                            alert.showAndWait();
                        }

                        pre.close();
                    }
                    loginusername.clear();
                    loginpassword.clear();
                    areyouadmin.setSelected(false);
                    clickpassword.setSelected(false);
                    //password.setText("");
                    passwordLabel.setText("");

                }catch (SQLException se) {
                    se.printStackTrace();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //finally block is used to close resource
                finally {
                    try {
                        if (pre != null)
                            con.close();
                    } catch (SQLException se) {
                    }
                    try {
                        if (con != null)
                            con.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                        //System.out.println(se);
                    }//end finally try
                }//end try
            }
        }

    @FXML
    void gotodash() throws IOException {
        Parent root3 = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Stage stage=new Stage();
       // stage.setFullScreen(true);
        stage.setScene(new Scene(root3));
        stage.show();
        Stage stage1= (Stage) pnLogin.getScene().getWindow();
        stage1.close();
    }

    public void handleClicksinlogin(javafx.event.ActionEvent event) throws SQLException, ClassNotFoundException {
        if (event.getSource() == btnSignup) {
            String qry = "SELECT COUNT(*) FROM water_plant.login";
            Connection con = utility.Utility();
            PreparedStatement pre = con.prepareStatement(qry);
            ResultSet rs= pre.executeQuery();
            int count=0;
            while(rs.next()) {
                count=rs.getInt(1);
            }
            if(count>0){
                foradmin.setDisable(true);
            }
            pnSignup.toFront();
        } else if (event.getSource() == btngoback) {
            foradmin.setDisable(false);
            pnLogin.toFront();
        }

    }


    @FXML
    void handleforgpass(javafx.event.ActionEvent event) throws IOException {
     try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("forget.fxml"));
                Parent root1=(Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
       }
        catch (Exception e) {
            e.printStackTrace();
     }
    }



    @FXML
    private JFXTextField getemailforg;

    @FXML
    private javafx.scene.control.Label forguser;

    @FXML
    private Label forgpass;

    @FXML
    private Pane pnseepass;

    @FXML
    private JFXTextField forganswer;

    @FXML
    void forgetpass(ActionEvent event) {
        Connection con = null;
        PreparedStatement pre = null;
        try {
            String email = getemailforg.getText();
            System.out.println(email);
            String securityq= securityQ.getValue();
            String answer= forganswer.getText();
            System.out.println(securityq);
            //Register JDBC driver
           // Class.forName(JDBC_DRIVER);
            String query = "SELECT User_name, Password FROM water_plant.login WHERE Email=? AND security_question=? AND answer=?";

            //Open a connection
            System.out.println("Connecting to selected database.....");
            con = utility.Utility();//DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection database Successfully");

            //Execute query
            pre = con.prepareStatement(query);
            pre.setString(1, email);
            pre.setString(2, securityq);
            pre.setString(3, answer);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String username = rs.getString("User_name");
                String password = rs.getString("Password");
                if (username == null && password == null) {
                    //JOptionPane.showMessageDialog(null, "Invalid Email");
                    Alert alert= new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Granti Aqua");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Email");
                    alert.showAndWait();

                } else {
                    //System.out.println("hello");
                    pnseepass.toFront();
                    forguser.setText(username);
                    forgpass.setText(password);

                }
            }
            pre.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //finally block is used to close resource
        finally {
            try {
                if (pre != null)
                    con.close();
            } catch (SQLException se) {
            }
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
            Stage stage = (Stage) pnseepass.getScene().getWindow();
            stage.close();
        }



    @FXML
    private JFXComboBox<String> securityQ;

    public void question(){
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "What's your favourite color ?",
                        "Which is your favourite movie ?",
                        "Which is your favourite pet");
        securityQ.setItems(options);
    }



    @FXML
    private JFXCheckBox clickpassword;
    @FXML
    void showpassword(ActionEvent event) {
        if(clickpassword.isSelected()){
            passwordLabel.setText(loginpassword.getText());
        }else{
            passwordLabel.setText("");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
           question();

    }



    public void newpanel(ActionEvent event) throws SQLException, ClassNotFoundException {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(25, 206, 235));
        panel.setBounds(0,0,500,100);
        JLabel label= new JLabel();
        label.setText("User Authentication :");
        label.setBounds(50,0,300,95);
        label.setFont(new Font("Calibri", Font.BOLD, 30));
        label.setForeground(Color.WHITE);
        panel.add(label);
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.white);
        panel2.setLayout(new BoxLayout (panel2, BoxLayout.Y_AXIS));
        panel2.setBounds(60,100,500,400);
        JCheckBox cb[]=new JCheckBox[10];
        int i=0;
        String count="";
        String qry = "SELECT User_name,adminstration_verification,adminORuser FROM water_plant.login";
        Connection con = utility.Utility();
        PreparedStatement pre = con.prepareStatement(qry);
        ResultSet rs= pre.executeQuery();
        while(rs.next()) {
            cb[i]=new JCheckBox(rs.getString(1));
            cb[i].setBackground(Color.white);
            cb[i].setFont(new Font("Calibri", Font.BOLD, 25));
            if (rs.getString(2).equals("True")) {
                cb[i].setSelected(true);
            }
            if (rs.getString(3).equals("admin")) {
                cb[i].setEnabled(false);
                cb[i].setText(rs.getString(1)+" (admin)");
            }
            cb[i].setVisible(true);
            i++;
        }
        String qry5 = "SELECT COUNT(*) FROM water_plant.login";
        PreparedStatement pre5 = con.prepareStatement(qry5);
        ResultSet rs5= pre5.executeQuery();
        while(rs5.next()) {
            count=rs5.getString(1);
        }
        int finalCount = Integer.parseInt(count);
        for(i=0;i<finalCount;i++) {
            panel2.add(cb[i]);
        }
        JButton b=new JButton("Submit");
        b.setFont(new Font("Calibri", Font.BOLD,30));
        b.setBackground(new Color(25, 206, 235));
        panel2.add(b);
        JPanel panel3 = new JPanel();
        panel3.setBounds(0,100,60,400);
        panel3.setBackground(Color.white);
        JFrame frame = new JFrame("Administrator panel");
        frame.setSize(500, 500);
        frame.setBackground(Color.white);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.add(panel);
        frame.add(panel2);
        frame.add(panel3);
        b.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String usernameselected = null;
                String usernamenotselected = null;
                for (int i = 0; i < finalCount; i++) {
                    if (cb[i].isSelected()) {
                        usernameselected = cb[i].getText();
                        System.out.println(usernameselected);
                        String qry0 = "UPDATE water_plant.login SET adminstration_verification=? WHERE User_name=?";
                        Connection con = null;
                        try {
                            con = utility.Utility();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        PreparedStatement pre = null;
                        try {
                            pre = con.prepareStatement(qry0);
                            pre.setString(1, "True");
                            pre.setString(2, usernameselected);

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            int row = pre.executeUpdate();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            con.close();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            pre.close();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        usernamenotselected = cb[i].getText();
                        System.out.println(usernamenotselected);
                        String qry1 = "UPDATE water_plant.login SET adminstration_verification=? WHERE User_name=?";
                        Connection con1 = null;
                        try {
                            con1 = utility.Utility();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        PreparedStatement pre1 = null;
                        try {
                            pre1 = con1.prepareStatement(qry1);
                            pre1.setString(1, "False");
                            pre1.setString(2, usernamenotselected);

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            int row = pre1.executeUpdate();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            con1.close();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            pre1.close();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "User Authentication Submitted Sucessfully");
                frame.dispose();
            }
        });
    }
}

