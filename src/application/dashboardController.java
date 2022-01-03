package application;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class dashboardController implements Initializable {

   /*static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/water_plant";
    static final String USER = "root";
    static final String PASS = "Decard@123";*/

    @FXML
    private Button btnStudents;

    @FXML
    private Button btnTeachers;

    @FXML
    private Button btnFees;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnSettings;

    @FXML
    private StackPane masterpane;

    @FXML
    private void handleClicks(ActionEvent event) {
        masterpane.toFront();
        try {
            if (event.getSource() == btnStudents) {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("customerpane.fxml"));
                //pane.autosize();
                masterpane.getChildren().setAll(pane);
               // pane.autosize();
            } else if (event.getSource() == btnTeachers) {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("orderspane.fxml"));
                masterpane.getChildren().setAll(pane);
            } else if (event.getSource() == btnFees) {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("bill.fxml"));
                masterpane.getChildren().setAll(pane);
            } else if (event.getSource() == btnUsers) {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("report.fxml"));
                masterpane.getChildren().setAll(pane);
            } else if (event.getSource() == btnSettings) {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("setting.fxml"));
                masterpane.getChildren().setAll(pane);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private AnchorPane masterpane1;

    @FXML
    void contact(ActionEvent event) {
        masterpane1.toFront();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("customerpane.fxml"));
            //pane.autosize();
            masterpane.getChildren().setAll(pane);
           // pane.autosize();
            //System.out.println(pane+"@@@@@@@@@@@@");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void logout(ActionEvent event) throws IOException {
            Stage stage= (Stage) masterpane.getScene().getWindow();
            stage.close();
            Parent root4 = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage stage4=new Stage();
           // stage4.setFullScreen(true);
            stage4.setScene(new Scene(root4));
            stage4.show();
    }

}
