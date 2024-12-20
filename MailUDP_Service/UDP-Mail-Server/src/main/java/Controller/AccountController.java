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
        String insertSQL = "INSERT INTO account ( username, pass, email, token ) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = openConnection();
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            // Hash mật khẩu
            String hashedPass = hashPass(acc.getPass());

            // Tạo token ngẫu nhiên
            String token = generateToken(acc.getEmail());

            // Gán giá trị vào câu lệnh SQL
            pstmt.setString(1, acc.getUsername());
            pstmt.setString(2, hashedPass);
            pstmt.setString(3, acc.getEmail());
            pstmt.setString(4, token);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(new RuntimeException(e));
            return false;
        }
    }

    private static String generateToken(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest((input + System.currentTimeMillis()).getBytes());

            // Chuyển đổi byte[] sang chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    public static Account login(Account acc) {
        String selectSQL = "SELECT * FROM account WHERE email = ? AND pass = ?";
        try (Connection conn = openConnection(); PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, acc.getEmail());
            pstmt.setString(2, hashPass(acc.getPass()));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                return new Account(rs.getInt("id"), rs.getString("email"), rs.getString("username"), rs.getString("token"));
            } else {
                // Đăng nhập thất bại
                System.out.println("unsucc");
                return new Account();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }   
    
    // Ma hoa tin nhan luu o server 
    public static boolean validateToken(Account acc) {
    String selectSQL = "SELECT * FROM account WHERE email = ? AND token = ?";
    try (Connection conn = openConnection(); PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
        // Gán giá trị đầu vào vào câu lệnh SQL
        pstmt.setString(1, acc.getEmail());
        pstmt.setString(2, acc.getToken());

        // Thực thi truy vấn
        ResultSet rs = pstmt.executeQuery();

        // Kiểm tra xem có dòng nào trả về hay không
        if (rs.next()) {
            // Email và token hợp lệ
            return true;
        } else {
            // Email hoặc token không hợp lệ
            return false;
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error validating account", e);
    }
}


    private static String hashPass(String pass) {
        try {
            // Sử dụng thuật toán SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
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
