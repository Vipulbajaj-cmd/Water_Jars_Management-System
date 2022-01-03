package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static application.utility.Utility;

public class billreportController implements Initializable {


    @FXML
    private CustomTextField autotxtfield;

    @FXML
    private TableView<billreport> billreporttable;

    @FXML
    private TableColumn<billreport, String> billreportsd;

    @FXML
    private TableColumn<billreport, String> billreported;

    @FXML
    private TableColumn<billreport, String> billreportamount;

    @FXML
    private TextField billdepositamount;
    @FXML
    private TextField balremaining;

    public void billreportsearch(javafx.event.ActionEvent event) throws SQLException, ClassNotFoundException {
        Connection con= utility.Utility();
        String name=autotxtfield.getText();
        String qry = "SELECT ID,DEPOSIT_AMOUNT,BALANCE from water_plant.register WHERE CAUS_FULL_NAME=?";
        PreparedStatement pstmt = con.prepareStatement(qry);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        String deposit_amount= "0";
        String pending_amount= " ";
        int id=0;
        while(rs.next()){
            id=rs.getInt(1);
            deposit_amount=rs.getString(2);
            pending_amount=rs.getString(3);
        }
        pstmt.close();
        con.close();

        try {
            billreportsd.setCellValueFactory(new PropertyValueFactory<billreport, String>("sd"));
            billreported.setCellValueFactory(new PropertyValueFactory<billreport, String>("ed"));
            billreportamount.setCellValueFactory(new PropertyValueFactory<billreport, String>("amount"));

            ObservableList<billreport> listb = utility.getDatabillreport(id);

            billreporttable.setItems(listb);
        } catch (Exception e) {
            System.out.println(e);
        }
        billdepositamount.setText(deposit_amount);
        billdepositamount.setEditable(false);
        balremaining.setText(pending_amount);
        balremaining.setEditable(false);

    }
    ObservableList<String> complete= FXCollections.observableArrayList();
    void autocomplete() throws SQLException, ClassNotFoundException {
        Connection con = Utility();
        String query = "SELECT CAUS_FULL_NAME FROM water_plant.register";
        PreparedStatement pst1 = con.prepareStatement(query);
        ResultSet row = pst1.executeQuery();
        while (row.next()){
            String input= row.getString(1);
            System.out.println(input);
            complete.add(input);
            TextFields.bindAutoCompletion(autotxtfield, complete);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            autocomplete();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
