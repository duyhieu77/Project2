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

public class RevenueReportStage extends Stage {
    private ListView<String> reportsList = new ListView<>();

    public RevenueReportStage() {
        setTitle("Revenue Reports");

        Button refreshButton = new Button("Refresh");

        VBox vbox = new VBox(reportsList, refreshButton);
        Scene scene = new Scene(vbox, 400, 400);
        setScene(scene);

        refreshButton.setOnAction(e -> loadReports());
        loadReports();
    }

    private void loadReports() {
        reportsList.getItems().clear();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM RevenueReports");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String reportInfo = "Report Month: " + resultSet.getString("ReportMonth") +
                        ", Total Revenue: " + resultSet.getDouble("TotalRevenue") +
                        ", Total Quantity: " + resultSet.getInt("TotalQuantity");
                reportsList.getItems().add(reportInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}