package db.tech.restaurantdesktopapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class PageLoader extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PageLoader.class.getResource("uncleHenrysLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 944);
        stage.setTitle("Uncle Henry's");
        stage.getIcons().add(new Image(PageLoader.class.getResourceAsStream("/Images/logo.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}