package com.restaurantmanagement.app.utils;

public class ValidationUtils {
    public static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9]{4,20}$");
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }
}
