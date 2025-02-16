package com.example.invoice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Invoice Management System");

        Button addOrderButton = new Button("Add Order");
        Button viewOrdersButton = new Button("View Orders");
        Button revenueReportButton = new Button("View Revenue Reports");

        VBox vbox = new VBox(addOrderButton, viewOrdersButton, revenueReportButton);
        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();

        addOrderButton.setOnAction(e -> {
            new AddOrderStage().show();
        });

        viewOrdersButton.setOnAction(e -> {
            new ViewOrdersStage().show();
        });

        revenueReportButton.setOnAction(e -> {
            new RevenueReportStage().show();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}