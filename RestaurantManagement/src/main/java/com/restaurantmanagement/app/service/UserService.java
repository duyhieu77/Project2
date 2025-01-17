package com.restaurantmanagement.app.service;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    // Giả lập cơ sở dữ liệu người dùng với (username, password:role).
    private final Map<String, String> userDatabase = new HashMap<>();

    public UserService() {
        // Dữ liệu mẫu cho các vai trò.
        userDatabase.put("manager", "1234:MANAGER"); // Quản lý
        userDatabase.put("cashier", "1234:CASHIER"); // Thu Ngân
        userDatabase.put("chef", "1234:CHEF");       // Đầu bếp
    }

    // Xác thực người dùng và trả về vai trò.
    public String authenticateUser(String username, String password) {
        String storedData = userDatabase.get(username);
        if (storedData != null) {
            String[] parts = storedData.split(":");
            if (parts[0].equals(password)) {
                return parts[1]; // Trả về vai trò.
            }
        }
        return null; // Thông tin không hợp lệ.
    }
}

