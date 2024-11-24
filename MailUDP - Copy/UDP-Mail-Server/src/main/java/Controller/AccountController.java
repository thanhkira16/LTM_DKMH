/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import static Controller.DBConnection.openConnection;
import Model.Account;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author iemmeiemi
 */
public class AccountController {
    public static Boolean register(Account acc) {
        String insertSQL = "INSERT INTO account ( username, pass, email ) VALUES (?, ?, ?)";
        try {
            Connection conn = openConnection();
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, acc.getUsername());
            pstmt.setString(2, hashPass(acc.getPass()));
            pstmt.setString(3, acc.getEmail());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(new RuntimeException(e));
            return false;
        }
    }
    
    public static Account login(Account acc) {
        String selectSQL = "SELECT * FROM account WHERE email = ? AND pass = ?";
        try (Connection conn = openConnection();
            PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, acc.getEmail());
            pstmt.setString(2, hashPass(acc.getPass()));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                
                return new Account(rs.getInt("id"), rs.getString("email"), rs.getString("username"));
            } else {
                // Đăng nhập thất bại
                System.out.println("unsucc");
                return new Account();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static String hashPass(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(pass.getBytes());

            // Chuyển đổi byte[] sang chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing data", e);
        }
    }
}
