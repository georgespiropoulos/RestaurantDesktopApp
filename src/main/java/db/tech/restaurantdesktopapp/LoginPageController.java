package db.tech.restaurantdesktopapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginPageController {

    @FXML
    private Label wrongPassUser;

    @FXML
    private TextField usernameTextbox;

    @FXML
    private TextField passwordTextbox;

    @FXML
    private Button loginButton;

    User user = new User();

    @FXML
    public void onLoginClick(){
        if (usernameTextbox.getText().equals("") || passwordTextbox.getText().equals("")){
            wrongPassUser.setVisible(true);
        }
        else{
            try{
                DBUtils.dbConnect();
                String pass;
                pass = DBUtils.checkPass(usernameTextbox.getText());
                if (passwordTextbox.getText().equals(pass)){
                    if(DBUtils.checkAdmin(usernameTextbox.getText())){
                        user = user.getDetails(usernameTextbox.getText());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
                        Parent root = loader.load();
                        AdminPageController controller = (AdminPageController) loader.getController();
                        controller.setUser(user);
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();
                        System.out.println("Login:\n"+user);

                    }else{
                        user = user.getDetails(usernameTextbox.getText());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePage.fxml"));
                        Parent root = loader.load();
                        EmployeePageController controller = (EmployeePageController) loader.getController();
                        controller.setUser(user);
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();
                        System.out.println("Login:\n"+user);
                    }

                }else{
                    wrongPassUser.setVisible(true);
                }
            }catch (Exception e){
                wrongPassUser.setText("Something went wrong!");
                wrongPassUser.setVisible(true);
                System.out.println(e);
            }
        }
    }
}