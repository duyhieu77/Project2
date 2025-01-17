package com.restaurantmanagement.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.restaurantmanagement.app.entity.MenuItem;
import com.restaurantmanagement.app.service.MenuService;

public class MenuController {

    @FXML private TableView<MenuItem> menuTable;
    @FXML private TableColumn<MenuItem, String> menuItemIdColumn;
    @FXML private TableColumn<MenuItem, String> menuItemNameColumn;
    @FXML private TableColumn<MenuItem, Double> priceColumn;
    @FXML private TableColumn<MenuItem, String> categoryColumn;

    private MenuService menuService;

    public MenuController() {
        this.menuService = new MenuService();
    }

    @FXML
    private void initialize() {
        // Initialize table columns with menu data (mock data or from database)
        menuItemIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        menuItemNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject()); // Sửa tại đây
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

        menuTable.setItems(menuService.getMenuList());
    }

    @FXML
    private void handleAddMenuItem() {
        // Logic to add a new menu item
    }

    @FXML
    private void handleEditMenuItem() {
        // Logic to edit selected menu item
        MenuItem selectedMenuItem = menuTable.getSelectionModel().getSelectedItem();
        if (selectedMenuItem != null) {
            // Edit menu item details
        } else {
            showAlert("Không có món ăn nào được chọn", "Vui lòng chọn một món để chỉnh sửa.");
        }
    }

    @FXML
    private void handleDeleteMenuItem() {
        // Logic to delete selected menu item
        MenuItem selectedMenuItem = menuTable.getSelectionModel().getSelectedItem();
        if (selectedMenuItem != null) {
            menuService.deleteMenuItem(selectedMenuItem);
            menuTable.setItems(menuService.getMenuList());
        } else {
            showAlert("Không có món ăn nào được chọn", "Vui lòng chọn một món để xóa.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
