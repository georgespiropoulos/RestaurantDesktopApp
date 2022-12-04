package db.tech.restaurantdesktopapp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class OrdersPopUpController {
    @FXML
    ListView ordersPopUpList;
    @FXML
    Button ordersPopUpAdd, ordersPopUpCancel;
    public Drink[] selectedDrinks;

    public Dish[] selectedDishes;
    public int orderid;
    public int mode;

    public void apply(){
        try {
            if (mode == 1){
                ObservableList<String> selected;
                int index = 0;
                int[] ids;
                String[] string;
                selected = ordersPopUpList.getSelectionModel().getSelectedItems();
                ids = new int[selected.size()];
                while (index < selected.size()) {
                    StringBuffer f = new StringBuffer();
                    string = selected.get(index).split("");
                    for (int i = 0; i < string.length; i++) {
                        if ((string[i].matches("[0-9]+"))) {
                            f.append(string[i]);
                        } else {
                            ids[index] = Integer.parseInt(f.toString());
                            break;
                        }
                    }
                    System.out.println(ids[index]);
                    index++;
                }
                selectedDrinks = new Drink[ids.length];
                for (int i=0; i<ids.length; i++){
                    selectedDrinks[i] = DBUtils.getDrink(ids[i]);
                    DBUtils.addDrinktoOrder(selectedDrinks[i].getDrinkid(),orderid);
                }
            }
            if (mode == 2) {
                ObservableList<String> selected;
                int index = 0;
                int[] ids;
                String[] string;
                selected = ordersPopUpList.getSelectionModel().getSelectedItems();
                ids = new int[selected.size()];
                while (index < selected.size()) {
                    StringBuffer f = new StringBuffer();
                    string = selected.get(index).split("");
                    for (int i = 0; i < string.length; i++) {
                        if ((string[i].matches("[0-9]+"))) {
                            f.append(string[i]);
                        } else {
                            ids[index] = Integer.parseInt(f.toString());
                            break;
                        }
                    }
                    System.out.println(ids[index]);
                    index++;
                }
                selectedDishes = new Dish[ids.length];
                for (int i=0; i<ids.length; i++){
                    selectedDishes[i] = DBUtils.getDish(ids[i]);
                    DBUtils.addDishtoOrder(selectedDishes[i].getDishid(),orderid);
                }
            }
            Order order = DBUtils.getOrderDetails(orderid);
            order.setDishes(DBUtils.getOrderDishes(order.getOrderid()));
            order.setDrinks(DBUtils.getOrderDrinks(order.getOrderid()));
            System.out.println(order.getBill());
            DBUtils.updateOrder(orderid, order.getTableid(), order.getBill());
            Stage stage = (Stage) ordersPopUpAdd.getScene().getWindow();
            stage.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void cancel(){
        System.out.println(mode);
        Stage stage = (Stage) ordersPopUpCancel.getScene().getWindow();
        stage.close();
    }
}
