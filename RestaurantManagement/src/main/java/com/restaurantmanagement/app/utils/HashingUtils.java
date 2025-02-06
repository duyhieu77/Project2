package com.restaurantmanagement.app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingUtils {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] hashedBytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkPassword(String inputPassword, String storedHash) {
        return hashPassword(inputPassword).equals(storedHash);
    }
}
