package Controller;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class MailController {

    public static void saveEncryptMessageKey(String email, SecretKey msgKey) {
        String insertSQL = "INSERT INTO encryptMessage (email, msgKey) VALUES (?, ?)";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, email);
            pstmt.setString(2, Base64.getEncoder().encodeToString(msgKey.getEncoded())); // Chuyển đổi msgKey sang Base64
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SecretKey getEncryptMessageKey(String email) {
        String selectSQL = "SELECT msgKey FROM encryptMessage WHERE email = ?";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] decodedKey = Base64.getDecoder().decode(rs.getString("msgKey")); // Giải mã msgKey từ Base64
                return AESUtils.decodeKey(decodedKey); // Chuyển đổi về SecretKey
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SecretKey getOrGenerateEncryptMessageKey(String email) throws NoSuchAlgorithmException {
        SecretKey key = getEncryptMessageKey(email);
        if (key == null) {
            key = AESUtils.generateKey(); // Tạo mới SecretKey nếu không tồn tại
            saveEncryptMessageKey(email, key); // Lưu vào cơ sở dữ liệu
        }
        return key;
    }
}
