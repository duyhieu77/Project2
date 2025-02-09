package com.example.project2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class HelloApplication extends Application {
    private final EmployeeManager employeeManager = new EmployeeManager();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        // Nhập thông tin nhân viên
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);

        Label positionLabel = new Label("Position:");
        TextField positionField = new TextField();
        grid.add(positionLabel, 0, 1);
        grid.add(positionField, 1, 1);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        grid.add(emailLabel, 0, 3);
        grid.add(emailField, 1, 3);

        Label phoneLabel = new Label("Phone:");
        TextField phoneField = new TextField();
        grid.add(phoneLabel, 0, 4);
        grid.add(phoneField, 1, 4);

        Button saveButton = new Button("Save Employee");
        grid.add(saveButton, 1, 5);

        Label employeeIdLabel = new Label("Employee ID:");
        TextField employeeIdField = new TextField();
        grid.add(employeeIdLabel, 0, 6);
        grid.add(employeeIdField, 1, 6);

        Label leaveStartLabel = new Label("Leave Start (YYYY-MM-DD):");
        TextField leaveStartField = new TextField();
        grid.add(leaveStartLabel, 0, 7);
        grid.add(leaveStartField, 1, 7);

        Label leaveEndLabel = new Label("Leave End (YYYY-MM-DD):");
        TextField leaveEndField = new TextField();
        grid.add(leaveEndLabel, 0, 8);
        grid.add(leaveEndField, 1, 8);

        Label reasonLabel = new Label("Reason:");
        TextField reasonField = new TextField();
        grid.add(reasonLabel, 0, 9);
        grid.add(reasonField, 1, 9);

        Button saveLeaveButton = new Button("Save Leave History");
        grid.add(saveLeaveButton, 1, 10);

        Button showEmployeesButton = new Button("Show Employees");
        grid.add(showEmployeesButton, 1, 11);

        Button showLeaveHistoryButton = new Button("Show Leave History");
        grid.add(showLeaveHistoryButton, 1, 12);

        Scene scene = new Scene(grid, 400, 450);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Lưu nhân viên
        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            String position = positionField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            if (name.isEmpty() || position.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Name and Position cannot be empty.");
                alert.showAndWait();
                return;
            }

            boolean success = employeeManager.saveEmployee(name, position, email, phone);
            Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                    success ? "Employee saved successfully!" : "Error saving employee.");
            alert.showAndWait();
        });

        // Lưu lịch sử nghỉ phép
        saveLeaveButton.setOnAction(e -> {
            try {
                int employeeId = Integer.parseInt(employeeIdField.getText());
                String leaveStart = leaveStartField.getText();
                String leaveEnd = leaveEndField.getText();
                String reason = reasonField.getText();

                boolean success = employeeManager.saveLeaveHistory(employeeId, leaveStart, leaveEnd, reason);
                Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                        success ? "Leave saved successfully!" : "Error saving leave.");
                alert.showAndWait();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employee ID must be a number.");
                alert.showAndWait();
            }
        });

        // Hiển thị danh sách nhân viên
        showEmployeesButton.setOnAction(e -> {
            List<Employee> employees = employeeManager.getAllEmployees();
            StringBuilder employeeList = new StringBuilder("Danh sách nhân viên:\n");
            for (Employee emp : employees) {
                employeeList.append(emp.getId()).append(": ").append(emp.getName()).append("\n");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, employeeList.toString());
            alert.showAndWait();
        });

        // Hiển thị danh sách nghỉ phép
        showLeaveHistoryButton.setOnAction(e -> {
            List<LeaveHistory> leaveHistories = employeeManager.getAllLeaveHistories();
            StringBuilder leaveList = new StringBuilder("Danh sách nghỉ phép:\n");
            for (LeaveHistory leave : leaveHistories) {
                leaveList.append("ID: ").append(leave.getId())
                        .append(", Employee ID: ").append(leave.getEmployeeId())
                        .append(", Từ: ").append(leave.getLeaveStart())
                        .append(", Đến: ").append(leave.getLeaveEnd())
                        .append(", Lý do: ").append(leave.getReason()).append("\n");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, leaveList.toString());
            alert.showAndWait();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}