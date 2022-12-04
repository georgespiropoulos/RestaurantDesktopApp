package db.tech.restaurantdesktopapp;

import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public class AdminPageController implements Initializable{

    @FXML
    private Label day, date, time, idTxtAdmin, nameTxtAdmin, surnameTxtAdmin, usernameTxtAdmin, PasswordTxtAdmin,
            idTxtAdmin1, nameTxtAdmin1, surnameTxtAdmin1, usernameTxtAdmin1, PasswordTxtAdmin1, isAdmin,
            orderIDTxtAdmin, tableNoTxtAdmin, ordersSummaryTxtAdmin;

    @FXML
    private Button logoutAdmin, profileMenuA,employeesMenuA, ordersMenuA, mainMenuA, tablesMenuA, reservationsMenuA;

    @FXML
    private Pane profilePane, employeesPane, ordersPane, menuPane, tablesPane, reservationsPane;

    @FXML
    private ComboBox dropdownEmployees, dropdownOrdersAdmin;

    @FXML
    private ListView DropdownListOrdersAdmin;


    private User user = new User();

    public void setUser(User user){
        idTxtAdmin.setText(Integer.toString(user.getId()));
        nameTxtAdmin.setText(user.getName());
        surnameTxtAdmin.setText(user.getSurname());
        usernameTxtAdmin.setText(user.getUsername());
        int passlen = user.getPass().length();
        String pass = "";
        int i=0;
        while (i < passlen){
            pass = pass+"*";
            i++;
        }
        PasswordTxtAdmin.setText(pass);
    }

    public void profileUpdate(){
        try {
            user = user.getDetails(usernameTxtAdmin.getText());
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

    public void setUserEmployees(User user){
        idTxtAdmin1.setText(Integer.toString(user.getId()));
        nameTxtAdmin1.setText(user.getName());
        surnameTxtAdmin1.setText(user.getSurname());
        usernameTxtAdmin1.setText(user.getUsername());
        int passlen = user.getPass().length();
        String pass = "";
        int i=0;
        while (i < passlen){
            pass = pass+"*";
            i++;
        }
        PasswordTxtAdmin1.setText(pass);
        isAdmin.setText(user.getAdmin().toString());
    }

    public void setEmployeesTab(){
        try {
            String[] usernames;
            usernames = DBUtils.getUsers();
            dropdownEmployees.setItems(FXCollections.observableArrayList(usernames));
            dropdownEmployees.setPromptText("Select");
            EventHandler<ActionEvent> event =
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                String selected;
                                selected = dropdownEmployees.getValue().toString();
                                User selectedUser = DBUtils.getUserDetails(selected);
                                setUserEmployees(selectedUser);
                            }catch (Exception e){}
                        }
                    };
            dropdownEmployees.setOnAction(event);
        }catch (Exception e){
            System.out.println(e);
        }
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
            dropdownOrdersAdmin.setItems(FXCollections.observableArrayList(orders));
            dropdownOrdersAdmin.setPromptText("Select Order ID");
            EventHandler<ActionEvent> click =
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                String selected;
                                selected = dropdownOrdersAdmin.getValue().toString();
                                String[] selectedid = selected.split(" ",2);
                                Order selectedOrder = DBUtils.getOrderDetails(Integer.parseInt(selectedid[0]));
                                setOrderDetails(selectedOrder);
                            }catch (Exception e){}
                        }
                    };
            dropdownOrdersAdmin.setOnAction(click);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void setOrderDetails(Order order){
        try {
            order.setDishes(DBUtils.getOrderDishes(order.getOrderid()));
            order.setDrinks(DBUtils.getOrderDrinks(order.getOrderid()));
            orderIDTxtAdmin.setText(Integer.toString(order.getOrderid()));
            tableNoTxtAdmin.setText(Integer.toString(order.getTableid()));
            ordersSummaryTxtAdmin.setText(String.format("%.2f €",order.getBill()));
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
            DropdownListOrdersAdmin.setItems(list);
        }catch (Exception e){}
    }

    public void addDrink(){
        try {
            if (orderIDTxtAdmin.getText() != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ordersPopUp.fxml"));
                Parent root = loader.load();
                OrdersPopUpController controller = (OrdersPopUpController) loader.getController();
                controller.mode = 1;
                controller.orderid = Integer.parseInt(orderIDTxtAdmin.getText());
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
                    dropdownOrdersAdmin.setItems(FXCollections.observableArrayList(orders));
                    dropdownOrdersAdmin.setPromptText("Select Order ID");
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addDish(){
        try {
            if (orderIDTxtAdmin.getText() != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ordersPopUp.fxml"));
                Parent root = loader.load();
                OrdersPopUpController controller = (OrdersPopUpController) loader.getController();
                controller.mode = 2;
                controller.orderid = Integer.parseInt(orderIDTxtAdmin.getText());
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
                    dropdownOrdersAdmin.setItems(FXCollections.observableArrayList(orders));
                    dropdownOrdersAdmin.setPromptText("Select Order ID");
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void editEmployee(){
        try {
            if (usernameTxtAdmin1 != null) {
                user = user.getDetails(usernameTxtAdmin1.getText());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeesPopUpAdmin.fxml"));
                Parent root = loader.load();
                EmployeesPopUpController controller = (EmployeesPopUpController) loader.getController();
                controller.setText(user);
                controller.mode = 2;
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Edit Employee");
                stage.getIcons().add(new Image(PageLoader.class.getResourceAsStream("/Images/logo.png")));
                stage.showAndWait();
                if (controller.getUsername() != null) {
                    String username = controller.getUsername();
                    user = user.getDetails(username);
                    setUserEmployees(user);
                    String[] usernames;
                    usernames = DBUtils.getUsers();
                    dropdownEmployees.setItems(FXCollections.observableArrayList(usernames));
                    dropdownEmployees.setPromptText("Select");
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
                dropdownOrdersAdmin.setItems(FXCollections.observableArrayList(orders));
                dropdownOrdersAdmin.setPromptText("Select Order ID");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addEmployee(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeesPopUpAdmin.fxml"));
            Parent root = loader.load();
            EmployeesPopUpController controller = (EmployeesPopUpController) loader.getController();
            controller.mode = 1;
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Employee");
            stage.getIcons().add(new Image(PageLoader.class.getResourceAsStream("/Images/logo.png")));
            stage.showAndWait();
            if (controller.getUsername() != null) {
                String username = controller.getUsername();
                user = user.getDetails(username);
                setUserEmployees(user);
                String[] usernames;
                usernames = DBUtils.getUsers();
                dropdownEmployees.setItems(FXCollections.observableArrayList(usernames));
                dropdownEmployees.setPromptText("Select");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void deleteEmployee(){
        try {
            if (usernameTxtAdmin1 != null) {
                DBUtils.deleteEmployee(Integer.parseInt(idTxtAdmin1.getText()));
                String[] usernames;
                usernames = DBUtils.getUsers();
                dropdownEmployees.setItems(FXCollections.observableArrayList(usernames));
                dropdownEmployees.setPromptText("Select");
            }
        }catch (Exception e)
        {}
    }

    public void onClickProfile(){
        profilePane.setVisible(true);
        profilePane.setDisable(false);
        employeesPane.setVisible(false);
        employeesPane.setDisable(true);
        ordersPane.setVisible(false);
        ordersPane.setDisable(true);
        menuPane.setVisible(false);
        menuPane.setDisable(true);
        tablesPane.setVisible(false);
        tablesPane.setDisable(true);
        reservationsPane.setVisible(false);
        reservationsPane.setDisable(true);
        profileMenuA.setDisable(true);
        profileMenuA.setVisible(true);
        employeesMenuA.setDisable(false);
        employeesMenuA.setVisible(true);
        ordersMenuA.setDisable(false);
        ordersMenuA.setVisible(true);
        mainMenuA.setDisable(false);
        mainMenuA.setVisible(true);
        tablesMenuA.setDisable(false);
        tablesMenuA.setVisible(true);
        reservationsMenuA.setDisable(false);
        reservationsMenuA.setVisible(true);
    }

    public void onClickEmployees(){
        profilePane.setVisible(false);
        profilePane.setDisable(true);
        employeesPane.setVisible(true);
        employeesPane.setDisable(false);
        ordersPane.setVisible(false);
        ordersPane.setDisable(true);
        menuPane.setVisible(false);
        menuPane.setDisable(true);
        tablesPane.setVisible(false);
        tablesPane.setDisable(true);
        reservationsPane.setVisible(false);
        reservationsPane.setDisable(true);
        profileMenuA.setDisable(false);
        profileMenuA.setVisible(true);
        employeesMenuA.setDisable(true);
        employeesMenuA.setVisible(true);
        ordersMenuA.setDisable(false);
        ordersMenuA.setVisible(true);
        mainMenuA.setDisable(false);
        mainMenuA.setVisible(true);
        tablesMenuA.setDisable(false);
        tablesMenuA.setVisible(true);
        reservationsMenuA.setDisable(false);
        reservationsMenuA.setVisible(true);
        setEmployeesTab();
    }

    public void onClickOrders(){
        profilePane.setVisible(false);
        profilePane.setDisable(true);
        employeesPane.setVisible(false);
        employeesPane.setDisable(true);
        ordersPane.setVisible(true);
        ordersPane.setDisable(false);
        menuPane.setVisible(false);
        menuPane.setDisable(true);
        tablesPane.setVisible(false);
        tablesPane.setDisable(true);
        reservationsPane.setVisible(false);
        reservationsPane.setDisable(true);
        profileMenuA.setDisable(false);
        profileMenuA.setVisible(true);
        employeesMenuA.setDisable(false);
        employeesMenuA.setVisible(true);
        ordersMenuA.setDisable(true);
        ordersMenuA.setVisible(true);
        mainMenuA.setDisable(false);
        mainMenuA.setVisible(true);
        tablesMenuA.setDisable(false);
        tablesMenuA.setVisible(true);
        reservationsMenuA.setDisable(false);
        reservationsMenuA.setVisible(true);
        setOrdersTab();
    }

    public void onClickMenu(){
        profilePane.setVisible(false);
        profilePane.setDisable(true);
        employeesPane.setVisible(false);
        employeesPane.setDisable(true);
        ordersPane.setVisible(false);
        ordersPane.setDisable(true);
        menuPane.setVisible(true);
        menuPane.setDisable(false);
        tablesPane.setVisible(false);
        tablesPane.setDisable(true);
        reservationsPane.setVisible(false);
        reservationsPane.setDisable(true);
        profileMenuA.setDisable(false);
        profileMenuA.setVisible(true);
        employeesMenuA.setDisable(false);
        employeesMenuA.setVisible(true);
        ordersMenuA.setDisable(false);
        ordersMenuA.setVisible(true);
        mainMenuA.setDisable(true);
        mainMenuA.setVisible(true);
        tablesMenuA.setDisable(false);
        tablesMenuA.setVisible(true);
        reservationsMenuA.setDisable(false);
        reservationsMenuA.setVisible(true);
    }

    public void onClickTables(){
        profilePane.setVisible(false);
        profilePane.setDisable(true);
        employeesPane.setVisible(false);
        employeesPane.setDisable(true);
        ordersPane.setVisible(false);
        ordersPane.setDisable(true);
        menuPane.setVisible(false);
        menuPane.setDisable(true);
        tablesPane.setVisible(true);
        tablesPane.setDisable(false);
        reservationsPane.setVisible(false);
        reservationsPane.setDisable(true);
        profileMenuA.setDisable(false);
        profileMenuA.setVisible(true);
        employeesMenuA.setDisable(false);
        employeesMenuA.setVisible(true);
        ordersMenuA.setDisable(false);
        ordersMenuA.setVisible(true);
        mainMenuA.setDisable(false);
        mainMenuA.setVisible(true);
        tablesMenuA.setDisable(true);
        tablesMenuA.setVisible(true);
        reservationsMenuA.setDisable(false);
        reservationsMenuA.setVisible(true);
    }

    public void onClickReservations(){
        profilePane.setVisible(false);
        profilePane.setDisable(true);
        employeesPane.setVisible(false);
        employeesPane.setDisable(true);
        ordersPane.setVisible(false);
        ordersPane.setDisable(true);
        menuPane.setVisible(false);
        menuPane.setDisable(true);
        tablesPane.setVisible(false);
        tablesPane.setDisable(true);
        reservationsPane.setVisible(true);
        reservationsPane.setDisable(false);
        profileMenuA.setDisable(false);
        profileMenuA.setVisible(true);
        employeesMenuA.setDisable(false);
        employeesMenuA.setVisible(true);
        ordersMenuA.setDisable(false);
        ordersMenuA.setVisible(true);
        mainMenuA.setDisable(false);
        mainMenuA.setVisible(true);
        tablesMenuA.setDisable(false);
        tablesMenuA.setVisible(true);
        reservationsMenuA.setDisable(true);
        reservationsMenuA.setVisible(true);
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
    public void initialize(URL url, ResourceBundle rb) {;
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
