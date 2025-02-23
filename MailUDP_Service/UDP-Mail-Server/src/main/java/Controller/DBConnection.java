/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class DBConnection {
    // Thông tin kết nối

    public static void main(String[] args) {
        // Kết nối đến PostgreSQL
        try (Connection conn = openConnection()) {
            if (conn != null) {
                System.out.println("Kết nối thành công!");

                // Tạo bảng
                createUsersTable(conn);
                createBoxChatTable(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   private static void createUsersTable(Connection conn) throws SQLException {
    String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS Account ("
            + "ID INT AUTO_INCREMENT PRIMARY KEY, "
            + "username VARCHAR(50) NOT NULL UNIQUE, "
            + "pass VARCHAR(200) NOT NULL, "
            + "email VARCHAR(100) NOT NULL UNIQUE "
            + ") ENGINE=InnoDB;";  

    try (PreparedStatement pstmt = conn.prepareStatement(createUsersTableSQL)) {
        pstmt.executeUpdate();
        System.out.println("Table 'Account' created successfully.");
    }
}



    public static Connection openConnection() throws SQLException {
         String URL = "jdbc:mysql://localhost:3306/UDPMailServer";
         String USER = "root"; // Tên người dùng
         String PASSWORD = ""; // Mật khẩu
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

  private static void createBoxChatTable(Connection conn) throws SQLException {
    String createBoxChatTableSQL = "CREATE TABLE IF NOT EXISTS Mail ("
            + "ID INT AUTO_INCREMENT PRIMARY KEY, "
            + "emailSend VARCHAR(100), "
            + "emailReceive VARCHAR(100), "
            + "sendedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP"
            + ") ENGINE=InnoDB;";

    try (PreparedStatement pstmt = conn.prepareStatement(createBoxChatTableSQL)) {
        pstmt.executeUpdate();
        System.out.println("Table 'Mail' created successfully.");
    }
}


}

/*
 Cập nhật dữ liệu
                String updateSQL = "UPDATE sinhvien SET tuoi = ? WHERE ten = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                    pstmt.setInt(1, 21);
                    pstmt.setString(2, "Nguyen Van A");
                    pstmt.executeUpdate();
                }

                // Xóa dữ liệu
                String deleteSQL = "DELETE FROM sinhvien WHERE ten = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                    pstmt.setString(1, "Nguyen Van A");
                    pstmt.executeUpdate();
                }
*/

