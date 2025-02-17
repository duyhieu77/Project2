package com.example.Warehouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class WarehouseApplication extends Application {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Restaurant";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("warehouse-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        stage.setTitle("Quản lý Nhà hàng");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        connectToDatabase();
        launch();
    }

    private static void connectToDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
