package db.tech.restaurantdesktopapp;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.control.Label;

public class EmployeePageController implements Initializable {

    @FXML
    private Label day;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Button logoutAdmin;

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
