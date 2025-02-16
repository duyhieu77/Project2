module com.example.invoice {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.invoice to javafx.fxml;
    exports com.example.invoice;
}