module com.example.Warehouse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.Warehouse to javafx.fxml;
    exports com.example.Warehouse;
    exports com.example.Warehouse.Entity;
    opens com.example.Warehouse.Entity to javafx.fxml;
    exports com.example.Warehouse.Utils;
    opens com.example.Warehouse.Utils to javafx.fxml;
    exports com.example.Warehouse.controller;
    opens com.example.Warehouse.controller to javafx.fxml;
}