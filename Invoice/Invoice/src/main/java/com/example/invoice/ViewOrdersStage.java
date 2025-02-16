package com.example.invoice;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewOrdersStage extends Stage {
    private ListView<String> ordersList = new ListView<>();

    public ViewOrdersStage() {
        setTitle("View Orders");

        Button refreshButton = new Button("Refresh");

        VBox vbox = new VBox(ordersList, refreshButton);
        Scene scene = new Scene(vbox, 400, 400);
        setScene(scene);

        refreshButton.setOnAction(e -> loadOrders());
        loadOrders();
    }

    private void loadOrders() {
        ordersList.getItems().clear();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Orders");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String orderInfo = "Order ID: " + resultSet.getInt("OrderID") +
                        ", Total: " + resultSet.getDouble("TotalAmount") +
                        ", Date: " + resultSet.getTimestamp("OrderDate") +
                        ", Status: " + resultSet.getString("Status");
                ordersList.getItems().add(orderInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}