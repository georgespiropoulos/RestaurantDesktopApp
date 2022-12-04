package db.tech.restaurantdesktopapp;

import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;

public class EmployeePageController implements Initializable {

    @FXML
    private Label day, date, time, idTxtE, nameTxtE, surnameTxtE, usernameTxtE, PasswordTxtE,
    orderIDTxtEmployee1, tableNoTxtEmployee1, ordersSummaryTxtEmployee1;

    @FXML
    private Button logoutAdmin;

    @FXML
    private Pane profilePane, ordersPane, menuPane, tablesPane;

    @FXML
    private Button profileMenuE, ordersMenuE, mainMenuE, tablesMenuE;
    @FXML
    private ComboBox dropdownOrdersEmployee1;

    @FXML
    private ListView DropdownListOrdersEmployee1;

    private User user = new User();


    public void setUser(User user){
        idTxtE.setText(Integer.toString(user.getId()));
        nameTxtE.setText(user.getName());
        surnameTxtE.setText(user.getSurname());
        usernameTxtE.setText(user.getUsername());
        int passlen = user.getPass().length();
        String pass = "";
        int i=0;
        while (i < passlen){
            pass = pass+"*";
            i++;
        }
        PasswordTxtE.setText(pass);
    }

    public void setOrdersTab(){
        try {
            String[] orders;
            orders = DBUtils.getOrders();
            for (int i = 0; i<orders.length; i++){
                int status = DBUtils.getOrderDetails(Integer.parseInt(orders[i])).getStatus();
                if (status == 1) {
                    orders[i] = orders[i] + " [COMPLETED]";
                }else orders[i] = orders[i] + " [PENDING]";
            }
            dropdownOrdersEmployee1.setItems(FXCollections.observableArrayList(orders));
            dropdownOrdersEmployee1.setPromptText("Select Order ID");
            EventHandler<ActionEvent> click =
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                String selected;
                                selected = dropdownOrdersEmployee1.getValue().toString();
                                String[] selectedid = selected.split(" ",2);
                                Order selectedOrder = DBUtils.getOrderDetails(Integer.parseInt(selectedid[0]));
                                setOrderDetails(selectedOrder);
                            }catch (Exception e){}
                        }
                    };
            dropdownOrdersEmployee1.setOnAction(click);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void setOrderDetails(Order order){
        try {
            order.setDishes(DBUtils.getOrderDishes(order.getOrderid()));
            order.setDrinks(DBUtils.getOrderDrinks(order.getOrderid()));
            orderIDTxtEmployee1.setText(Integer.toString(order.getOrderid()));
            tableNoTxtEmployee1.setText(Integer.toString(order.getTableid()));
            ordersSummaryTxtEmployee1.setText(String.format("%.2f €",order.getBill()));
            ObservableList<String> list = FXCollections.observableArrayList();
            for (int i = 0; i < order.getDishes().length; i++){
                String line = String.format("%-4.4s x\t %-30.30s\t %-5.5s €",DBUtils.timesBoughtDish(order.getDishes()[i].getDishid()
                        , order.getOrderid()), order.getDishes()[i].getDishname(), order.getDishes()[i].getPrice());
                list.add(line);
            }
            for (int i = 0; i < order.getDrinks().length; i++){
                String line = String.format("%-4.4s x\t %-30.30s\t %-5.5s €",DBUtils.timesBoughtDrink(order.getDrinks()[i].getDrinkid()
                        , order.getOrderid()), order.getDrinks()[i].getDrinkname(), order.getDrinks()[i].getPrice());
                list.add(line);
            }
            DropdownListOrdersEmployee1.setItems(list);
        }catch (Exception e){}
    }

    public void addDrink(){
        try {
            if (orderIDTxtEmployee1.getText() != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ordersPopUp.fxml"));
                Parent root = loader.load();
                OrdersPopUpController controller = (OrdersPopUpController) loader.getController();
                controller.mode = 1;
                controller.orderid = Integer.parseInt(orderIDTxtEmployee1.getText());
                ObservableList<String> drinks;
                drinks = DBUtils.getAvailableDrinks();
                controller.ordersPopUpList.setItems(drinks);
                controller.ordersPopUpList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Add Drinks");
                stage.getIcons().add(new Image(PageLoader.class.getResourceAsStream("/Images/logo.png")));
                stage.showAndWait();
                if (controller.selectedDrinks != null) {
                    Order order = DBUtils.getOrderDetails(controller.orderid);
                    setOrderDetails(order);
                    String[] orders;
                    orders = DBUtils.getOrders();
                    for (int i = 0; i<orders.length; i++){
                        int status = DBUtils.getOrderDetails(Integer.parseInt(orders[i])).getStatus();
                        if (status == 1) {
                            orders[i] = orders[i] + " [COMPLETED]";
                        }else orders[i] = orders[i] + " [PENDING]";
                    }
                    dropdownOrdersEmployee1.setItems(FXCollections.observableArrayList(orders));
                    dropdownOrdersEmployee1.setPromptText("Select Order ID");
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addDish(){
        try {
            if (orderIDTxtEmployee1.getText() != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ordersPopUp.fxml"));
                Parent root = loader.load();
                OrdersPopUpController controller = (OrdersPopUpController) loader.getController();
                controller.mode = 2;
                controller.orderid = Integer.parseInt(orderIDTxtEmployee1.getText());
                ObservableList<String> dishes;
                dishes = DBUtils.getAvailableDishes();
                controller.ordersPopUpList.setItems(dishes);
                controller.ordersPopUpList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Add Dishes");
                stage.getIcons().add(new Image(PageLoader.class.getResourceAsStream("/Images/logo.png")));
                stage.showAndWait();
                if (controller.selectedDishes != null) {
                    Order order = DBUtils.getOrderDetails(controller.orderid);
                    setOrderDetails(order);
                    String[] orders;
                    orders = DBUtils.getOrders();
                    for (int i = 0; i<orders.length; i++){
                        int status = DBUtils.getOrderDetails(Integer.parseInt(orders[i])).getStatus();
                        if (status == 1) {
                            orders[i] = orders[i] + " [COMPLETED]";
                        }else orders[i] = orders[i] + " [PENDING]";
                    }
                    dropdownOrdersEmployee1.setItems(FXCollections.observableArrayList(orders));
                    dropdownOrdersEmployee1.setPromptText("Select Order ID");
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void newOrder(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewOrdersPopUp.fxml"));
            Parent root = loader.load();
            NewOrderPopUpController controller = (NewOrderPopUpController) loader.getController();
            controller.drinks = DBUtils.getAvailableDrinksList();
            controller.dishes = DBUtils.getAvailableDishesList();
            controller.tables = DBUtils.getAvailableTables();
            controller.setTab();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("New Order");
            stage.getIcons().add(new Image(PageLoader.class.getResourceAsStream("/Images/logo.png")));
            stage.showAndWait();
            if(controller.order != null){
                setOrderDetails(controller.order);
                String[] orders;
                orders = DBUtils.getOrders();
                for (int i = 0; i<orders.length; i++){
                    int status = DBUtils.getOrderDetails(Integer.parseInt(orders[i])).getStatus();
                    if (status == 1) {
                        orders[i] = orders[i] + " [COMPLETED]";
                    }else orders[i] = orders[i] + " [PENDING]";
                }
                dropdownOrdersEmployee1.setItems(FXCollections.observableArrayList(orders));
                dropdownOrdersEmployee1.setPromptText("Select Order ID");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void onClickProfile(){
        profilePane.setVisible(true);
        profilePane.setDisable(false);
        ordersPane.setVisible(false);
        ordersPane.setDisable(true);
        menuPane.setVisible(false);
        menuPane.setDisable(true);
        tablesPane.setVisible(false);
        tablesPane.setDisable(true);
        profileMenuE.setVisible(true);
        profileMenuE.setDisable(true);
        ordersMenuE.setVisible(true);
        ordersMenuE.setDisable(false);
        mainMenuE.setVisible(true);
        mainMenuE.setDisable(false);
        tablesMenuE.setVisible(true);
        tablesMenuE.setDisable(false);
    }

    public void onClickOrders(){
        profilePane.setVisible(false);
        profilePane.setDisable(true);
        ordersPane.setVisible(true);
        ordersPane.setDisable(false);
        menuPane.setVisible(false);
        menuPane.setDisable(true);
        tablesPane.setVisible(false);
        tablesPane.setDisable(true);
        profileMenuE.setVisible(true);
        profileMenuE.setDisable(false);
        ordersMenuE.setVisible(true);
        ordersMenuE.setDisable(true);
        mainMenuE.setVisible(true);
        mainMenuE.setDisable(false);
        tablesMenuE.setVisible(true);
        tablesMenuE.setDisable(false);
        setOrdersTab();
    }

    public void onClickMenu(){
        profilePane.setVisible(false);
        profilePane.setDisable(true);
        ordersPane.setVisible(false);
        ordersPane.setDisable(true);
        menuPane.setVisible(true);
        menuPane.setDisable(false);
        tablesPane.setVisible(false);
        tablesPane.setDisable(true);
        profileMenuE.setVisible(true);
        profileMenuE.setDisable(false);
        ordersMenuE.setVisible(true);
        ordersMenuE.setDisable(false);
        mainMenuE.setVisible(true);
        mainMenuE.setDisable(true);
        tablesMenuE.setVisible(true);
        tablesMenuE.setDisable(false);
    }

    public void onClickTables(){
        profilePane.setVisible(false);
        profilePane.setDisable(true);
        ordersPane.setVisible(false);
        ordersPane.setDisable(true);
        menuPane.setVisible(false);
        menuPane.setDisable(true);
        tablesPane.setVisible(true);
        tablesPane.setDisable(false);
        profileMenuE.setVisible(true);
        profileMenuE.setDisable(false);
        ordersMenuE.setVisible(true);
        ordersMenuE.setDisable(false);
        mainMenuE.setVisible(true);
        mainMenuE.setDisable(false);
        tablesMenuE.setVisible(true);
        tablesMenuE.setDisable(true);
    }

    public void profileUpdate(){
        try {
            user = user.getDetails(usernameTxtE.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfilePopUp.fxml"));
            Parent root = loader.load();
            ProfileUpdatePopUpController controller = (ProfileUpdatePopUpController) loader.getController();
            controller.setText(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Profile");
            stage.getIcons().add(new Image(PageLoader.class.getResourceAsStream("/Images/logo.png")));
            stage.showAndWait();
            if (controller.getUsername() != null) {
                String username = controller.getUsername();
                user = user.getDetails(username);
                setUser(user);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void onClickLogout(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("uncleHenrysLogin.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) logoutAdmin.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            DBUtils.dbDisconnect();
        }catch (Exception e){
            System.out.println("Couldn't load screen!");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDateTime();
    }

    private void setDateTime(){
        clock();
        setDate();
        setDay();
    }

    private void setDay(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE").withLocale(Locale.ENGLISH);
            day.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void setDate(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            date.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void clock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            time.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
