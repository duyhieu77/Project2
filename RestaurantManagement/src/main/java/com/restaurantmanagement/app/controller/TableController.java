package com.restaurantmanagement.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.restaurantmanagement.app.entity.Table;
import com.restaurantmanagement.app.service.TableService;

public class TableController {

    @FXML private TableView<Table> tableTable;
    @FXML private TableColumn<Table, String> tableIdColumn;
    @FXML private TableColumn<Table, Integer> seatsColumn;
    @FXML private TableColumn<Table, String> statusColumn;

    private TableService tableService;

    public TableController() {
        this.tableService = new TableService();
    }

    @FXML
    private void initialize() {
        // Initialize table columns with table data (mock data or from database)
        tableIdColumn.setCellValueFactory(cellData -> cellData.getValue().tableIdProperty());
        seatsColumn.setCellValueFactory(cellData -> cellData.getValue().seatsProperty().asObject()); // Sửa tại đây
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        tableTable.setItems(tableService.getTableList());
    }

    @FXML
    private void handleAddTable() {
        // Logic to add a new table
    }

    @FXML
    private void handleEditTable() {
        // Logic to edit selected table
        Table selectedTable = tableTable.getSelectionModel().getSelectedItem();
        if (selectedTable != null) {
            // Edit table details
        } else {
            showAlert("Không có bàn ăn nào được chọn", "Vui lòng chọn một bàn để chỉnh sửa.");
        }
    }

    @FXML
    private void handleDeleteTable() {
        // Logic to delete selected table
        Table selectedTable = tableTable.getSelectionModel().getSelectedItem();
        if (selectedTable != null) {
            tableService.deleteTable(selectedTable);
            tableTable.setItems(tableService.getTableList());
        } else {
            showAlert("Không có bàn ăn nào được chọn", "Vui lòng chọn một bàn để xóa.");
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
