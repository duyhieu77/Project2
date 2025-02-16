package com.example.invoice;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddOrderStage extends Stage {
    private TextField totalAmountField = new TextField();
    private TextArea orderDetailsArea = new TextArea();

    public AddOrderStage() {
        setTitle("Add Order");

        Button saveButton = new Button("Save Order");

        VBox vbox = new VBox(new Label("Total Amount:"), totalAmountField,
                new Label("Order Details (ItemID:Quantity:Price):"), orderDetailsArea,
                saveButton);
        Scene scene = new Scene(vbox, 400, 300);
        setScene(scene);

        saveButton.setOnAction(e -> saveOrder());
    }

    private void saveOrder() {
        String totalAmount = totalAmountField.getText();
        String[] details = orderDetailsArea.getText().split("\n");

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Lưu đơn hàng
            String sqlOrder = "INSERT INTO Orders (TotalAmount, Status) VALUES (?, 'PENDING')";
            try (PreparedStatement orderStatement = connection.prepareStatement(sqlOrder, PreparedStatement.RETURN_GENERATED_KEYS)) {
                orderStatement.setDouble(1, Double.parseDouble(totalAmount));
                orderStatement.executeUpdate();

                // Lấy OrderID mới nhất
                var generatedKeys = orderStatement.getGeneratedKeys();
                int orderId = 0;
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                }

                // Lưu chi tiết đơn hàng
                String sqlDetail = "INSERT INTO OrderDetails (OrderID, ItemID, Quantity, Price) VALUES (?, ?, ?, ?)";
                try (PreparedStatement detailStatement = connection.prepareStatement(sqlDetail)) {
                    for (String detail : details) {
                        String[] parts = detail.split(":");
                        if (parts.length == 3) { // Kiểm tra độ dài
                            int itemId = Integer.parseInt(parts[0]);
                            int quantity = Integer.parseInt(parts[1]);
                            double price = Double.parseDouble(parts[2]);

                            detailStatement.setInt(1, orderId);
                            detailStatement.setInt(2, itemId);
                            detailStatement.setInt(3, quantity);
                            detailStatement.setDouble(4, price);
                            detailStatement.executeUpdate();
                        }
                    }
                }
            }

            connection.commit();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order added successfully!");
            alert.showAndWait();
            close();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error adding order: " + e.getMessage());
            alert.showAndWait();
        }
    }
}