package db.tech.restaurantdesktopapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MenuPopUpController {
    @FXML
    TextField nameMenuPopUpAddAdmin, priceMenuPopUpAddAdmin;
    @FXML
    TextArea descMenuPopUpAddAdmin;
    @FXML
    CheckBox availabilityMenuPopUpAddAdmin;
    @FXML
    Button menuPopUpAddAdminApply, menuPopUpAddAdminCancel;
    public int mode, op, id;

    public void apply(){
        try {
            if (mode == 1){
                if (op == 1){
                    DBUtils.insertDish(nameMenuPopUpAddAdmin.getText(), descMenuPopUpAddAdmin.getText(),
                            Float.parseFloat(priceMenuPopUpAddAdmin.getText()), availabilityMenuPopUpAddAdmin.isSelected());

                }else{
                    DBUtils.updateDish(nameMenuPopUpAddAdmin.getText(), descMenuPopUpAddAdmin.getText(),
                            Float.parseFloat(priceMenuPopUpAddAdmin.getText()), availabilityMenuPopUpAddAdmin.isSelected(), id);
                }
            }
            if (mode == 2) {
                if (op == 1){
                    DBUtils.insertDrink(nameMenuPopUpAddAdmin.getText(), descMenuPopUpAddAdmin.getText(),
                            Float.parseFloat(priceMenuPopUpAddAdmin.getText()), availabilityMenuPopUpAddAdmin.isSelected());
                }else{
                    DBUtils.updateDrink(nameMenuPopUpAddAdmin.getText(), descMenuPopUpAddAdmin.getText(),
                            Float.parseFloat(priceMenuPopUpAddAdmin.getText()), availabilityMenuPopUpAddAdmin.isSelected(), id);
                }
            }
            Stage stage = (Stage) menuPopUpAddAdminApply.getScene().getWindow();
            stage.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void cancel(){
        Stage stage = (Stage) menuPopUpAddAdminCancel.getScene().getWindow();
        stage.close();
    }
}
