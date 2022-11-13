package db.tech.restaurantdesktopapp;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;

public class AdminPageController implements Initializable{

    @FXML
    private Label day;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Button logoutAdmin;

    @FXML
    private Pane profilePane;
    @FXML
    private Pane employeesPane;

    @FXML
    private Pane ordersPane;

    @FXML
    private Pane menuPane;

    @FXML
    private Pane tablesPane;

    @FXML
    private Pane reservationsPane;

    @FXML
    private Button profileMenuA;

    @FXML
    private Button employeesMenuA;

    @FXML
    private Button ordersMenuA;

    @FXML
    private Button mainMenuA;

    @FXML
    private Button tablesMenuA;

    @FXML
    private Button reservationsMenuA;

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
