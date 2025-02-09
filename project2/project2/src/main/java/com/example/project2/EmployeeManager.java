package com.example.project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    public boolean saveEmployee(String name, String position, String email, String phone) {
        try (Connection conn = Database.getConnection()) {
            if (conn == null) return false;
            String query = "INSERT INTO employees (name, position, email, phone) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, name);
                pstmt.setString(2, position);
                pstmt.setString(3, email);
                pstmt.setString(4, phone);
                pstmt.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi SQL: " + ex.getMessage());
            return false;
        }
    }

    public boolean saveLeaveHistory(int employeeId, String leaveStart, String leaveEnd, String reason) {
        if (!isValidDate(leaveStart) || !isValidDate(leaveEnd)) {
            System.out.println("Định dạng ngày không hợp lệ.");
            return false;
        }

        try (Connection conn = Database.getConnection()) {
            String query = "INSERT INTO leave_history (employee_id, leave_start, leave_end, reason) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, employeeId);
                pstmt.setString(2, leaveStart);
                pstmt.setString(3, leaveEnd);
                pstmt.setString(4, reason);
                pstmt.executeUpdate();
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi SQL: " + ex.getMessage());
            return false;
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM employees";
            try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employee emp = new Employee(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("position"),
                            rs.getString("email"),
                            rs.getString("phone"));
                    employees.add(emp);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi SQL: " + ex.getMessage());
        }
        return employees;
    }

    public List<LeaveHistory> getAllLeaveHistories() {
        List<LeaveHistory> leaveHistories = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM leave_history";
            try (PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LeaveHistory leave = new LeaveHistory(
                            rs.getInt("id"),
                            rs.getInt("employee_id"),
                            rs.getString("leave_start"),
                            rs.getString("leave_end"),
                            rs.getString("reason"));
                    leaveHistories.add(leave);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi SQL: " + ex.getMessage());
        }
        return leaveHistories;
    }

    private boolean isValidDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
}