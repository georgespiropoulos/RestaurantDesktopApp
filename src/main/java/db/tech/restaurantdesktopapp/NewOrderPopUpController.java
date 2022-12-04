package db.tech.restaurantdesktopapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class NewOrderPopUpController {
    @FXML
    ComboBox selectTableOrdersPopUp, addDishOrderPopUp, addDrinkOrderPopUp;

    @FXML
    ListView newOrderPopUpList;

    @FXML
    Button newOrdersPopUpAdd, newOrdersPopUpCancel;

    public Drink[] drinks;

    public Dish[] dishes;

    public int[] tables;

    public void setTab(){
        try {
            ObservableList<String> selectedList = FXCollections.observableArrayList();
//            drinks = DBUtils.getAvailableDrinksList();
//            dishes = DBUtils.getAvailableDishesList();
//            tables = DBUtils.getAvailableTables();
            selectTableOrdersPopUp.setItems(FXCollections.observableArrayList(tables));
            selectTableOrdersPopUp.setPromptText("Select");
            EventHandler<ActionEvent> event =
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                String selected;
                                selected = selectTableOrdersPopUp.getValue().toString();
                                int selectedTable = Integer.parseInt(selected);
                            }catch (Exception e){}
                        }
                    };
            selectTableOrdersPopUp.setOnAction(event);
            addDishOrderPopUp.setItems(FXCollections.observableArrayList(dishes));
            addDishOrderPopUp.setPromptText("Select");
            EventHandler<ActionEvent> event2 =
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                String selected;
                                selected = addDishOrderPopUp.getValue().toString();
//                                String line = String.format("%-4.4s \t %-30.30s\t %-5.5s €",;
//                                selectedList.add(line);
                            }catch (Exception e){}
                        }
                    };
            selectTableOrdersPopUp.setOnAction(event);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void apply(){

        Stage stage = (Stage) newOrdersPopUpAdd.getScene().getWindow();
        stage.close();
    }

    public void cancel(){
        Stage stage = (Stage) newOrdersPopUpCancel.getScene().getWindow();
        stage.close();
    }
}
