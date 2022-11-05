module db.tech.restaurantdesktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;


    opens db.tech.restaurantdesktopapp to javafx.fxml;
    exports db.tech.restaurantdesktopapp;
}