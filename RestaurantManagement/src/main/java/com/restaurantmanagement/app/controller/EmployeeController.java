package com.restaurantmanagement.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.restaurantmanagement.app.entity.Employee;
import com.restaurantmanagement.app.service.EmployeeService;

public class EmployeeController {

    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> idColumn;
    @FXML private TableColumn<Employee, String> nameColumn;
    @FXML private TableColumn<Employee, String> roleColumn;
    @FXML private TableColumn<Employee, String> statusColumn;

    private EmployeeService employeeService;

    public EmployeeController() {
        this.employeeService = new EmployeeService();
    }

    @FXML
    private void initialize() {
        // Initialize table columns with employee data (mock data or from database)
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        employeeTable.setItems(employeeService.getEmployeeList());
    }

    @FXML
    private void handleAddEmployee() {
        // Logic to add new employee
        // Open a dialog to input new employee data
    }

    @FXML
    private void handleEditEmployee() {
        // Logic to edit selected employee
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            // Edit employee details
        } else {
            showAlert("Không có nhân viên nào được chọn", "Vui lòng chọn một nhân viên để chỉnh sửa.");
        }
    }

    @FXML
    private void handleDeleteEmployee() {
        // Logic to delete selected employee
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeService.deleteEmployee(selectedEmployee);
            employeeTable.setItems(employeeService.getEmployeeList());
        } else {
            showAlert("Không có nhân viên nào được chọn", "Vui lòng chọn một nhân viên để xóa.");
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
