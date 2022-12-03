package db.tech.restaurantdesktopapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EmployeesPopUpController {
    @FXML
    TextField nameEmployeesEditAdmin, surnameEmployeesEditAdmin, usernameEmployeesEditAdmin, passwordEmployeesEditAdmin;

    @FXML
    Text idTextEmployeesPopOut, idEmployeesPopOut;

    @FXML
    CheckBox AdminCheck;

    @FXML
    Button employeesPopUpApplyAdmin, employeesPopUpCancelAdmin;

    public void setText(User user){
        idTextEmployeesPopOut.setVisible(true);
        idEmployeesPopOut.setVisible(true);
        idEmployeesPopOut.setText(Integer.toString(user.getId()));
        nameEmployeesEditAdmin.setText(user.getName());
        surnameEmployeesEditAdmin.setText(user.getSurname());
        usernameEmployeesEditAdmin.setText(user.getUsername());
        passwordEmployeesEditAdmin.setText(user.getPass());
        AdminCheck.setSelected(user.getAdmin());
    }

    private String username;
    public int mode;

    public void apply(){
        try {
            username = usernameEmployeesEditAdmin.getText();
            if (mode == 1){
                DBUtils.insertUser(nameEmployeesEditAdmin.getText(), surnameEmployeesEditAdmin.getText(), username,
                        passwordEmployeesEditAdmin.getText(), AdminCheck.isSelected());
            }
            if (mode == 2) {
                DBUtils.updateUser(nameEmployeesEditAdmin.getText(), surnameEmployeesEditAdmin.getText(), username,
                        passwordEmployeesEditAdmin.getText(), Integer.parseInt(idEmployeesPopOut.getText()));
                DBUtils.updateUserRole(AdminCheck.isSelected(), Integer.parseInt(idEmployeesPopOut.getText()));
            }
            Stage stage = (Stage) employeesPopUpApplyAdmin.getScene().getWindow();
            stage.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public String getUsername(){
        return username;
    }

    public void cancel(){
        System.out.println(mode);
        Stage stage = (Stage) employeesPopUpCancelAdmin.getScene().getWindow();
        stage.close();
    }
}
