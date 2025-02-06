package com.restaurantmanagement.app.controller;

import com.restaurantmanagement.app.entity.User;
import com.restaurantmanagement.app.entity.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Button menuButton;
    @FXML private Button orderButton;
    @FXML private Button employeeButton;
    @FXML private Button inventoryButton;
    @FXML private Button reportButton;

    private User currentUser;

    public void initializeDashboard(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + currentUser.getUsername());

        switch (currentUser.getRole()) {
            case MANAGER:
                menuButton.setVisible(true);
                orderButton.setVisible(true);
                employeeButton.setVisible(true);
                inventoryButton.setVisible(true);
                reportButton.setVisible(true);
                break;
            case CHEF:
                menuButton.setVisible(true);
                orderButton.setVisible(true);
                break;
            case CASHIER:
                orderButton.setVisible(true);
                reportButton.setVisible(true);
                break;
            default:
                break;
        }
    }

    @FXML private void handleMenu() {
        System.out.println("Menu Management clicked!");
    }

    @FXML private void handleOrders() {
        System.out.println("Order Management clicked!");
    }

    @FXML private void handleEmployees() {
        System.out.println("Employee Management clicked!");
    }

    @FXML private void handleInventory() {
        System.out.println("Inventory Management clicked!");
    }

    @FXML private void handleReport() {
        System.out.println("Report clicked!");
    }

    @FXML private void handleLogout() {
        System.out.println("Logout clicked!");
    }
}
