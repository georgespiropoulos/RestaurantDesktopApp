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
    public String[] drinkNames;

    public Dish[] dishes;
    public String[] dishNames;

    public int[] tables;
    public String[] tableIds;

    public Order order;
    private int orderid;

    ObservableList<String> selectedList = FXCollections.observableArrayList();
    String selected;

    public void setTab(){
        try {
            dishNames = new String[dishes.length];
            drinkNames = new String[drinks.length];
            tableIds = new String[tables.length];
            for (int i = 0; i < dishes.length; i++){
                dishNames[i]= dishes[i].getDishid()+" "+dishes[i].getDishname()+" [Dish]";
            }
            for (int i = 0; i < drinks.length; i++){
                drinkNames[i]= drinks[i].getDrinkid()+" "+drinks[i].getDrinkname()+" [Drink]";
            }
            for (int i = 0; i < tables.length; i++){
                tableIds[i]= Integer.toString(tables[i]);
            }
            selectTableOrdersPopUp.setItems(FXCollections.observableArrayList(tableIds));
            selectTableOrdersPopUp.setPromptText("Select");
            EventHandler<ActionEvent> event =
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                selected = selectTableOrdersPopUp.getValue().toString();
                                int selectedTable = Integer.parseInt(selected);
                            }catch (Exception e){}
                        }
                    };
            selectTableOrdersPopUp.setOnAction(event);
            addDishOrderPopUp.setItems(FXCollections.observableArrayList(dishNames));
            addDishOrderPopUp.setPromptText("Select");
            EventHandler<ActionEvent> event2 =
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                String selected;
                                selected = addDishOrderPopUp.getValue().toString();
                                selectedList.add(selected);
                                newOrderPopUpList.setItems(selectedList);
                            }catch (Exception e){}
                        }
                    };
            addDishOrderPopUp.setOnAction(event2);
            addDrinkOrderPopUp.setItems(FXCollections.observableArrayList(drinkNames));
            addDrinkOrderPopUp.setPromptText("Select");
            EventHandler<ActionEvent> event3 =
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                String selected;
                                selected = addDrinkOrderPopUp.getValue().toString();
                                selectedList.add(selected);
                                newOrderPopUpList.setItems(selectedList);
                            }catch (Exception e){}
                        }
                    };
            addDrinkOrderPopUp.setOnAction(event3);
        }catch (Exception e){
            System.out.println(e);
        }

    }
    public void apply(){
        if(selectedList != null && selected != null) {
            try {
                DBUtils.insertOrder(Integer.parseInt(selected));
                orderid = DBUtils.getOrder(Integer.parseInt(selected));
                for (int i = 0; i < selectedList.size(); i++) {
                    String[] strings = selectedList.get(i).split(" ", 3);
                    if (strings[2].equals("[Drink]")) {
                        DBUtils.addDrinktoOrder(Integer.parseInt(strings[0]), orderid);
                    }
                    if (strings[2].equals("[Dish]")) {
                        DBUtils.addDishtoOrder(Integer.parseInt(strings[0]), orderid);
                    }
                }
                order = DBUtils.getOrderDetails(orderid);
                DBUtils.setReservedTable(order.getTableid());
                order.setDrinks(DBUtils.getOrderDrinks(orderid));
                order.setDishes(DBUtils.getOrderDishes(orderid));
                DBUtils.updateOrder(order.getOrderid(),order.getTableid(),order.getBill());
            }catch (Exception e){
                System.out.println(e);
            }
        }
        Stage stage = (Stage) newOrdersPopUpAdd.getScene().getWindow();
        stage.close();
    }

    public void cancel(){
        Stage stage = (Stage) newOrdersPopUpCancel.getScene().getWindow();
        stage.close();
    }
}
