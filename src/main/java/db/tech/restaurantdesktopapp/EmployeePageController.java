package db.tech.restaurantdesktopapp;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.control.Label;

public class EmployeePageController implements Initializable {

    @FXML
    private Label day, date, time, idTxtE, nameTxtE, surnameTxtE, usernameTxtE, PasswordTxtE;

    @FXML
    private Button logoutAdmin;

    @FXML
    private Pane profilePane, ordersPane, menuPane, tablesPane;

    @FXML
    private Button profileMenuE, ordersMenuE, mainMenuE, tablesMenuE;

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
