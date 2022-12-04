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
    private Button logoutAdmin, profileMenuA,employeesMenuA, ordersMenuA, mainMenuA, tablesMenuA, showLogAdmin,
            completedButtonAdmin, AddDrinkButtonAdmin, AddDishButtonAdmin;

    @FXML
    private Pane profilePane, employeesPane, ordersPane, menuPane, tablesPane, logPaneAdmin;

    @FXML
    private ComboBox dropdownEmployees, dropdownOrdersAdmin;

    @FXML
    private ListView DropdownListOrdersAdmin, availableTablesListAdmin, reservedTablesListAdmin, logListAdmin,
    dishesListMenuAdmin, drinksListMenuAdmin;


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
            if(order.getStatus()==0){
                completedButtonAdmin.setDisable(false);
                completedButtonAdmin.setVisible(true);
            }else{
                completedButtonAdmin.setDisable(true);
                completedButtonAdmin.setVisible(false);
                AddDrinkButtonAdmin.setDisable(true);
                AddDishButtonAdmin.setDisable(true);
            }
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

    public void completedOrder(){
        try {
            DBUtils.updateOrderStatus(Integer.parseInt(orderIDTxtAdmin.getText()));
            DBUtils.freeTable(Integer.parseInt(tableNoTxtAdmin.getText()));
            completedButtonAdmin.setDisable(true);
            completedButtonAdmin.setVisible(false);
            AddDrinkButtonAdmin.setDisable(true);
            AddDishButtonAdmin.setDisable(true);
            String[] orders;
            orders = DBUtils.getOrders();
            for (int i = 0; i < orders.length; i++) {
                int status = DBUtils.getOrderDetails(Integer.parseInt(orders[i])).getStatus();
                if (status == 1) {
                    orders[i] = orders[i] + " [COMPLETED]";
                } else orders[i] = orders[i] + " [PENDING]";
            }
            dropdownOrdersAdmin.setItems(FXCollections.observableArrayList(orders));
            dropdownOrdersAdmin.setPromptText("Select Order ID");
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

    public void setTablesTab(){
        try {
            int[] tablesA;
            int[] tablesNA;
            String[] tablesAids;
            String[] tablesNAids;
            tablesA = DBUtils.getAvailableTables();
            tablesNA = DBUtils.getNotAvailableTables();
            tablesNAids = new String[tablesNA.length];
            tablesAids = new String[tablesA.length];
            for (int i = 0; i<tablesA.length; i++){
                tablesAids[i] = Integer.toString(tablesA[i]);
            }
            for (int i = 0; i<tablesNA.length; i++){
                tablesNAids[i] = Integer.toString(tablesNA[i]);
            }
            availableTablesListAdmin.setItems(FXCollections.observableArrayList(tablesAids));
            reservedTablesListAdmin.setItems(FXCollections.observableArrayList(tablesNAids));

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addTable(){
        try {
            DBUtils.addTable();
            setTablesTab();
        }catch (Exception e){}
    }

    public void deleteTable(){
        try {
            ObservableList<String> index = availableTablesListAdmin.getSelectionModel().getSelectedItems();
            int tableid = Integer.parseInt(index.get(0));
            DBUtils.deleteTable(tableid);
            setTablesTab();
        }catch (Exception e){}
    }

    public void setLogTab(){
        try{
            String[] log = DBUtils.showLog();
            logListAdmin.setItems(FXCollections.observableArrayList(log));
        }catch (Exception e){}
    }

    public void setMenuTab(){
        try {
            Dish[] Dishes;
            Drink[] Drinks;
            String[] DishLines;
            String[] DrinkLines;
            Dishes = DBUtils.getDishes();
            Drinks = DBUtils.getDrinks();
            DishLines = new String[Dishes.length];
            DrinkLines = new String[Drinks.length];
            for (int i = 0; i<Dishes.length; i++){
                Dish dish = Dishes[i];
                String avail;
                if (dish.isDishavailability()) avail = "Available";
                else avail = "Not Available";
                DishLines[i] = dish.getDishid()+" "+dish.getDishname()+" "+avail+" "+dish.getPrice()+"€";
            }
            for (int i = 0; i<Drinks.length; i++){
                Drink drink = Drinks[i];
                String avail;
                if (drink.isDrinkavailability()) avail = "Available";
                else avail = "Not Available";
                DrinkLines[i] = drink.getDrinkid()+" "+drink.getDrinkname()+" "+avail+" "+drink.getPrice()+"€";
            }
            dishesListMenuAdmin.setItems(FXCollections.observableArrayList(DishLines));
            drinksListMenuAdmin.setItems(FXCollections.observableArrayList(DrinkLines));

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void editDishMenu(){
        try {
            if(dishesListMenuAdmin.getSelectionModel().getSelectedItems() == null) {
                return;
            }
            ObservableList<String> dishes = dishesListMenuAdmin.getSelectionModel().getSelectedItems();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPopUpAddAdmin.fxml"));
            Parent root = loader.load();
            MenuPopUpController controller = (MenuPopUpController) loader.getController();
            String[] dishSt = dishes.get(0).split(" ",2);
            int dishid = Integer.parseInt(dishSt[0]);
            Dish dish = DBUtils.getDish(dishid);
            controller.mode = 1;
            controller.op = 2;
            controller.id = dish.getDishid();
            controller.nameMenuPopUpAddAdmin.setText(dish.getDishname());
            controller.descMenuPopUpAddAdmin.setText(dish.getDishdescription());
            controller.priceMenuPopUpAddAdmin.setText(Float.toString(dish.getPrice()));
            controller.availabilityMenuPopUpAddAdmin.setSelected(dish.isDishavailability());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Dish");
            stage.getIcons().add(new Image(PageLoader.class.getResourceAsStream("/Images/logo.png")));
            stage.showAndWait();
            setMenuTab();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addDishMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPopUpAddAdmin.fxml"));
            Parent root = loader.load();
            MenuPopUpController controller = (MenuPopUpController) loader.getController();
            controller.mode = 1;
            controller.op = 1;
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Dish");
            stage.getIcons().add(new Image(PageLoader.class.getResourceAsStream("/Images/logo.png")));
            stage.showAndWait();
            setMenuTab();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void editDrinkMenu(){
        try {
            if(drinksListMenuAdmin.getSelectionModel().getSelectedItems() == null) {
                return;
            }
            ObservableList<String> drinks = drinksListMenuAdmin.getSelectionModel().getSelectedItems();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPopUpAddAdmin.fxml"));
            Parent root = loader.load();
            MenuPopUpController controller = (MenuPopUpController) loader.getController();
            String[] drinkSt = drinks.get(0).split(" ",2);
            int drinkid = Integer.parseInt(drinkSt[0]);
            Drink drink = DBUtils.getDrink(drinkid);
            controller.id = drink.getDrinkid();
            controller.mode = 2;
            controller.op = 2;
            controller.nameMenuPopUpAddAdmin.setText(drink.getDrinkname());
            controller.descMenuPopUpAddAdmin.setText(drink.getDrinkdescription());
            controller.priceMenuPopUpAddAdmin.setText(Float.toString(drink.getPrice()));
            controller.availabilityMenuPopUpAddAdmin.setSelected(drink.isDrinkavailability());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Drink");
            stage.getIcons().add(new Image(PageLoader.class.getResourceAsStream("/Images/logo.png")));
            stage.showAndWait();
            setMenuTab();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addDrinkMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPopUpAddAdmin.fxml"));
            Parent root = loader.load();
            MenuPopUpController controller = (MenuPopUpController) loader.getController();
            controller.mode = 2;
            controller.op = 1;
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Drink");
            stage.getIcons().add(new Image(PageLoader.class.getResourceAsStream("/Images/logo.png")));
            stage.showAndWait();
            setMenuTab();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void deleteDish(){
        try {
            ObservableList<String> index = dishesListMenuAdmin.getSelectionModel().getSelectedItems();
            String[] str = index.get(0).split(" ", 2);
            int dishid = Integer.parseInt(str[0]);
            DBUtils.deleteDish(dishid);
            setMenuTab();
        }catch (Exception e){System.out.println(e);}
    }

    public void deleteDrink(){
        try {
            ObservableList<String> index = drinksListMenuAdmin.getSelectionModel().getSelectedItems();
            String[] str = index.get(0).split(" ", 2);
            int drinkid = Integer.parseInt(str[0]);
            DBUtils.deleteDrink(drinkid);
            setMenuTab();
        }catch (Exception e){System.out.println(e);}
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
        logPaneAdmin.setVisible(false);
        logPaneAdmin.setDisable(true);
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
        showLogAdmin.setDisable(false);
        showLogAdmin.setVisible(true);
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
        logPaneAdmin.setVisible(false);
        logPaneAdmin.setDisable(true);
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
        showLogAdmin.setDisable(false);
        showLogAdmin.setVisible(true);
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
        logPaneAdmin.setVisible(false);
        logPaneAdmin.setDisable(true);
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
        showLogAdmin.setDisable(false);
        showLogAdmin.setVisible(true);
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
        logPaneAdmin.setVisible(false);
        logPaneAdmin.setDisable(true);
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
        showLogAdmin.setDisable(false);
        showLogAdmin.setVisible(true);
        setMenuTab();
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
        logPaneAdmin.setVisible(false);
        logPaneAdmin.setDisable(true);
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
        showLogAdmin.setDisable(false);
        showLogAdmin.setVisible(true);
        setTablesTab();
    }

    public void onClickShowLog(){
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
        logPaneAdmin.setVisible(true);
        logPaneAdmin.setDisable(false);
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
        showLogAdmin.setDisable(true);
        showLogAdmin.setVisible(true);
        setLogTab();
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
