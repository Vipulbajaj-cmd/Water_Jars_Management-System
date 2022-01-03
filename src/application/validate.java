package application;

import javafx.scene.control.Alert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validate {
    public boolean validateEmail(String email){
        Pattern p=Pattern.compile("^(.+)@(.+)$");
        Matcher m=p.matcher(email);
        if(email.equals(""))
            return true;
        else if(m.find() && m.group().equals(email)){
            return  true;
        }else{
            Alert alert= new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Email");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Email");
            alert.showAndWait();
        }
    return false;
    }

    public boolean validatePhoneNo(String phone){
        Pattern p=Pattern.compile("(0|91)?[7-9][0-9]{9}");
        Matcher m=p.matcher(phone);
        if(m.find() && m.group().equals(phone)){
            return  true;
        }else{
            Alert alert= new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Email");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Phone Number");
            alert.showAndWait();
        }
        return false;
    }

    public boolean validateusername(String name){
        Pattern p=Pattern.compile("^[a-z0-9_-]{3,15}$");
        Matcher m=p.matcher(name);
        if(m.find() && m.group().equals(name)){
            return  true;
        }else{
            Alert alert= new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Email");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Username. Username should be 3 to 15 character with any lowercase character, digit or special symbol'_ -' only");
            alert.showAndWait();
        }
        return false;
    }

   /* public boolean validatePassword(String password){
        Pattern p=Pattern.compile("((?=.*[a-z])(?=.*d)(?=.*[@#$%]).{6,15})");
        Matcher m=p.matcher(password);
        if(m.find() && m.group().equals(password)){
            return  true;
        }else{
            Alert alert= new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Email");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid password");
            alert.showAndWait();
        }
        return false;
    }*/
}
