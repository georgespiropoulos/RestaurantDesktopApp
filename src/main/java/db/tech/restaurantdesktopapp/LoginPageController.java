package db.tech.restaurantdesktopapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPageController {
    @FXML
    private Label wrongPassUser;

    @FXML
    private TextField usernameTextbox;

    @FXML
    private TextField passwordTextbox;

    @FXML
    private Button loginButton;

    @FXML
    public void onLoginClick(){
        if (usernameTextbox.getText().equals("") || passwordTextbox.getText().equals("")){
            wrongPassUser.setVisible(true);
        }
        else{
            try{
                String pass;
                pass = DBUtils.checkPass(usernameTextbox.getText());
                if (passwordTextbox.getText().equals(pass)){
                    if(DBUtils.checkAdmin(usernameTextbox.getText())){
                        Parent root = FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();

                    }else{
                        Parent root = FXMLLoader.load(getClass().getResource("EmployeePage.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                    }

                }else{
                    wrongPassUser.setVisible(true);
                }
            }catch (Exception e){
                wrongPassUser.setVisible(true);
            }
        }
    }
}