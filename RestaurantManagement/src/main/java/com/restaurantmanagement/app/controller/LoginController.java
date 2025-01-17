package com.restaurantmanagement.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import com.restaurantmanagement.app.service.UserService;

public class LoginController {

    @FXML
    private TextField usernameField; // Trường nhập tên đăng nhập.
    @FXML
    private PasswordField passwordField; // Trường nhập mật khẩu.

    private final UserService userService = new UserService(); // Dịch vụ xác thực người dùng.

    // Xử lý sự kiện khi người dùng nhấn nút "Đăng nhập".
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi", "Tên đăng nhập hoặc mật khẩu không được để trống!");
            return;
        }

        // Xác thực người dùng và lấy vai trò.
        String role = userService.authenticateUser(username, password);
        if (role != null) {
            navigateToDashboard(role);
        } else {
            showAlert("Lỗi", "Tên đăng nhập hoặc mật khẩu không đúng!");
        }
    }

    // Chuyển đến giao diện theo vai trò người dùng.
    private void navigateToDashboard(String role) {
        Stage currentStage = (Stage) usernameField.getScene().getWindow();
        switch (role) {
            case "MANAGER": // Quản lý nhân viên.
                loadFXML("/com/restaurantmanagement/fxml/EmployeeManagement.fxml", "Quản lý Nhân viên", currentStage);
                break;
            case "CASHIER": // Quản lý bàn ăn.
                loadFXML("/com/restaurantmanagement/fxml/TableManagement.fxml", "Quản lý Bàn ăn", currentStage);
                break;
            case "CHEF": // Quản lý thực đơn.
                loadFXML("/com/restaurantmanagement/fxml/MenuManagement.fxml", "Quản lý Thực đơn", currentStage);
                break;
            default:
                showAlert("Lỗi", "Chức vụ không được hỗ trợ!");
        }
    }

    // Tải file FXML tương ứng.
    private void loadFXML(String fxmlPath, String title, Stage currentStage) {
        try {
            // Kiểm tra tệp FXML có tồn tại không
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (loader.getLocation() == null) {
                showAlert("Lỗi", "Không tìm thấy tệp FXML: " + fxmlPath);
                return;
            }
            Parent root = loader.load();  // Tải giao diện FXML.
            currentStage.getScene().setRoot(root);  // Cập nhật giao diện cho Stage hiện tại.
            currentStage.setTitle(title);  // Cập nhật tiêu đề cửa sổ.
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể tải giao diện! " + e.getMessage());
        }
    }


    // Hiển thị thông báo lỗi.
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
