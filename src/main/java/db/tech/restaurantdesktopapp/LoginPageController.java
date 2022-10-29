package db.tech.restaurantdesktopapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

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
    }
}