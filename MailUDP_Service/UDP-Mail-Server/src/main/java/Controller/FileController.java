/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import static Controller.DBConnection.openConnection;
import Model.Mail;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class FileController {

//    public static void createFileDB(Mail mail) {
//        createFile(mail.getEmailReceive(), mail.getTitle(), mail.getContent());
//        String insertSQL = "INSERT INTO mail ( emailsend, emailreceive ) VALUES (?, ?)";
//        try {
//            Connection conn = openConnection();
//            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
//            pstmt.setString(1, mail.getEmailSend());
//            pstmt.setString(2, mail.getEmailReceive());
//            pstmt.executeUpdate();
//            
//        } catch (SQLException e) {
//            System.out.println(new RuntimeException(e));
//        }
//    }
    
    public static ArrayList<byte[]> get(String email) {
        ArrayList<byte[]> fileDataList = new ArrayList<>(); // Danh sách chứa byte[]
        File directory = new File("./src/main/java/Resources/" +email);
        File[] files = directory.listFiles();
        if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        byte[] fileData = convertFileToByteArray(file);
                        fileDataList.add(fileData); // Thêm mảng byte vào danh sách
                    }
                }
            } else {
                System.out.println("No files found in the directory.");
            }
        return fileDataList;
    }
    
     public static ArrayList<Mail> get2(String email) {
        ArrayList<Mail> fileDataList = new ArrayList<>(); // Danh sách chứa byte[]
        File directory = new File("./src/main/java/Resources/" +email);
        File[] files = directory.listFiles();
        if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        byte[] fileData = convertFileToByteArray(file);
                        fileDataList.add(new Mail(file.getName(), fileData)); // Thêm mảng byte vào danh sách
                    }
                }
            } else {
                System.out.println("No files found in the directory.");
            }
        return fileDataList;
    }
    
    public static void createFolderAndFile(String folderName) {
        // Tạo thư mục
        File parent = new File("./src/main/java/Resources" );
        if (!parent.exists()) {
            System.out.println("Parent directory does not exist: " + parent.getAbsolutePath());
        }
        File folder = new File(parent, folderName); //
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Folder created: " + folderName);
            } else {
                System.out.println("Failed to create folder: " + folderName);
                return;
            }
        } else {
            System.out.println("Folder already exists: " + folderName);
        }
        
         createFile(folder.getName(), "dangkytinchi", 
            "Bạn chưa đăng ký tín chỉ trên hệ thống, vui lòng đăng ký.", 
            "system", new Date());
    }
    
    public static Boolean createFile(String folderName, String fileName, String fileContent, String sender, Date sendedAt) {
        // Tạo file trong thư mục
        File folder = new File("./src/main/java/Resources/" + folderName );
        File file = new File(folder, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Sender: " + sender + "\n " +fileContent);
            System.out.println("File created: " + file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
            return false;
        }
    }
    
    public static byte[] convertFileToByteArray(File file) {
        byte[] fileData = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileData); // Đọc nội dung file vào mảng byte
        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getName());
        }
        return fileData;
    }
    
    private static void convertByteToFile(byte[] data, int length, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName, true)) {
            fos.write(data, 0, length);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + fileName);
        }
    }
}












