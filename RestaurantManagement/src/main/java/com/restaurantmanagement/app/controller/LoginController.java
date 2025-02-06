package com.restaurantmanagement.app.controller;

import com.restaurantmanagement.app.entity.User;
import com.restaurantmanagement.app.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    private UserService userService;

    public LoginController() {
        this.userService = new UserService();
    }

    // Phương thức handleLogin sẽ được gọi khi người dùng ấn nút đăng nhập
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Kiểm tra nếu username và password không rỗng
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Thông báo", "Vui lòng nhập đầy đủ thông tin đăng nhập.", AlertType.WARNING);
            return;
        }

        // Tìm người dùng từ UserService
        User user = userService.getUserByUsername(username);

        // Kiểm tra thông tin đăng nhập
        if (user != null && user.getPasswordHash().equals(password)) {
            System.out.println("Đăng nhập thành công!");

            // Chuyển sang giao diện Dashboard
            try {
                // Tải FXML cho dashboard
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/restaurantmanagement/fxml/Dashboard.fxml"));
                Parent dashboard = loader.load();

                // Truyền user vào DashboardController
                DashboardController dashboardController = loader.getController();
                dashboardController.initializeDashboard(user);

                // Chuyển màn hình sang Dashboard
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(dashboard));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Lỗi", "Không thể mở màn hình Dashboard.", AlertType.ERROR);
            }
        } else {
            System.out.println("Thông tin đăng nhập không chính xác!");
            showAlert("Thông báo", "Tên người dùng hoặc mật khẩu không đúng.", AlertType.ERROR);
        }
    }

    // Phương thức hiển thị Alert thông báo
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Phương thức handle sự kiện nhấn button Đăng ký (nếu có)
    @FXML
    private void handleRegister() {
        // Chuyển sang giao diện đăng ký (nếu có)
        System.out.println("Chuyển sang màn hình đăng ký!");
    }

}
