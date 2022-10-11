module db.tech.restaurantdesktopapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens db.tech.restaurantdesktopapp to javafx.fxml;
    exports db.tech.restaurantdesktopapp;
}