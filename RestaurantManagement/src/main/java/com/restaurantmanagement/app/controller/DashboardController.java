package com.restaurantmanagement.app.controller;

import com.restaurantmanagement.app.entity.User;
import com.restaurantmanagement.app.entity.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Button menuButton;
    @FXML private Button orderButton;
    @FXML private Button employeeButton;
    @FXML private Button inventoryButton;
    @FXML private Button reportButton;
    @FXML private Button billButton;
    @FXML private Button backButton;
    @FXML private Button logoutButton;

    private User currentUser;

    public void initializeDashboard(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + currentUser.getUsername());
        configureButtonsVisibility();
    }

    private void configureButtonsVisibility() {
        Button[] allButtons = {menuButton, orderButton, employeeButton, inventoryButton, reportButton, billButton};
        for (Button button : allButtons) {
            button.setVisible(false);
        }

        switch (currentUser.getRole()) {
            case MANAGER:
                setButtonVisibility(menuButton, employeeButton, inventoryButton, billButton, reportButton);
                break;
            case CHEF:
                setButtonVisibility(menuButton, inventoryButton);
                break;
            case CASHIER:
                setButtonVisibility(billButton, orderButton, reportButton);
                break;
        }
    }

    private void setButtonVisibility(Button... buttons) {
        for (Button button : buttons) {
            button.setVisible(true);
        }
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/restaurantmanagement/fxml/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleMenu() {
        if (currentUser.getRole() == Role.MANAGER) {
            loadView("manager_menu.fxml");
        } else {
            loadView("menu.fxml");
        }
    }

    @FXML private void handleOrders() {
        loadView("order.fxml");
    }

    @FXML private void handleEmployees() {
        loadView("employee.fxml");
    }

    @FXML private void handleInventory() {
        loadView("inventory.fxml");
    }

    @FXML private void handleReport() {
        if (currentUser.getRole() == Role.MANAGER) {
            loadView("manager_report.fxml");
        } else {
            loadView("report.fxml");
        }
    }

    @FXML private void handleBill() {
        if (currentUser.getRole() == Role.MANAGER) {
            loadView("manager_bill.fxml");
        } else {
            loadView("bill.fxml");
        }
    }

    // Xử lý quay lại màn hình Dashboard
    @FXML
    private void handleBack() {
        // Lấy cửa sổ hiện tại (Stage)
        Stage stage = (Stage) backButton.getScene().getWindow();

        // Tải lại view Dashboard mà không đóng cửa sổ hiện tại
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/restaurantmanagement/fxml/Dashboard.fxml"));
            Parent root = loader.load();

            // Thiết lập lại Scene mới cho cửa sổ hiện tại
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Xử lý đăng xuất và quay lại Login.fxml
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/restaurantmanagement/fxml/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
