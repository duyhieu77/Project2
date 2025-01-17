package com.restaurantmanagement.app.service;

import com.restaurantmanagement.app.entity.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeService {

    private ObservableList<Employee> employeeList;

    public EmployeeService() {
        employeeList = FXCollections.observableArrayList(
                new Employee("E001", "John Doe", "Manager", "Active"),
                new Employee("E002", "Jane Smith", "Cashier", "Inactive"),
                new Employee("E003", "Mike Johnson", "Cook", "Active")
        );
    }

    public ObservableList<Employee> getEmployeeList() {
        return employeeList;
    }

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    public void editEmployee(Employee oldEmployee, Employee newEmployee) {
        int index = employeeList.indexOf(oldEmployee);
        if (index >= 0) {
            employeeList.set(index, newEmployee);
        }
    }

    public void deleteEmployee(Employee employee) {
        employeeList.remove(employee);
    }
}
