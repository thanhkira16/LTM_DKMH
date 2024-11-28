package Controller;

import Model.Mail;

import javax.crypto.SecretKey;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class FileController {

    public static ArrayList<byte[]> get(String email) {
        ArrayList<byte[]> fileDataList = new ArrayList<>();
        File directory = new File("./src/main/java/Resources/" + email);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    byte[] encryptedData = convertFileToByteArray(file);
                    SecretKey key = MailController.getEncryptMessageKey(email);
                    if (key != null) {
                        try {
                            byte[] decryptedData = AESUtils.decrypt(encryptedData, key);
                            fileDataList.add(decryptedData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            System.out.println("No files found in the directory.");
        }
        return fileDataList;
    }

    public static ArrayList<Mail> get2(String email) {
        ArrayList<Mail> fileDataList = new ArrayList<>();
        File directory = new File("./src/main/java/Resources/" + email);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    byte[] encryptedData = convertFileToByteArray(file);
                    SecretKey key = MailController.getEncryptMessageKey(email);
                    if (key != null) {
                        try {
                            byte[] decryptedData = AESUtils.decrypt(encryptedData, key);
                            fileDataList.add(new Mail(file.getName(), decryptedData));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            System.out.println("No files found in the directory.");
        }
        return fileDataList;
    }

    public static void createFolderAndFile(String folderName) {
        File parent = new File("./src/main/java/Resources");
        if (!parent.exists()) {
            System.out.println("Parent directory does not exist: " + parent.getAbsolutePath());
        }
        File folder = new File(parent, folderName);
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
        File folder = new File("./src/main/java/Resources/" + folderName);
        File file = new File(folder, fileName);
        try {
            SecretKey key = MailController.getOrGenerateEncryptMessageKey(folderName);
            byte[] encryptedContent = AESUtils.encrypt(fileContent.getBytes(), key);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(encryptedContent);
                System.out.println("File created and encrypted: " + file.getAbsolutePath());
                return true;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while creating the encrypted file.");
            e.printStackTrace();
        }
        return false;
    }

    public static byte[] convertFileToByteArray(File file) {
        byte[] fileData = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileData);
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
