module com.restaurantmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.restaurantmanagement.app;
    exports com.restaurantmanagement.app.controller;

    opens com.restaurantmanagement.app.controller to javafx.fxml;
}
