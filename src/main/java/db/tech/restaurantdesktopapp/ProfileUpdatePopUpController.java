package db.tech.restaurantdesktopapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ProfileUpdatePopUpController {
    @FXML
    TextField nameProfileEdit, surnameProfileEdit, usernameProfileEdit, passwordProfileEdit;

    @FXML
    Text idProfileEdit;

    @FXML
    Button profilePopUpApply, profilePopUpCancel;

    public void setText(User user){
        idProfileEdit.setText(Integer.toString(user.getId()));
        nameProfileEdit.setText(user.getName());
        surnameProfileEdit.setText(user.getSurname());
        usernameProfileEdit.setText(user.getUsername());
        passwordProfileEdit.setText(user.getPass());
    }

    private String username;

    public void apply(){
        try {
            username = usernameProfileEdit.getText();
            DBUtils.updateUser(nameProfileEdit.getText(), surnameProfileEdit.getText(), username, passwordProfileEdit.getText(), Integer.parseInt(idProfileEdit.getText()));
            Stage stage = (Stage) profilePopUpApply.getScene().getWindow();
            stage.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public String getUsername(){
        return username;
    }

    public void cancel(){
        Stage stage = (Stage) profilePopUpApply.getScene().getWindow();
        stage.close();
    }


}
