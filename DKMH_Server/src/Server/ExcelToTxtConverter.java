/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.*;

public class ExcelToTxtConverter {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel (.xlsx)");
            int userSelection = fileChooser.showOpenDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File excelFile = fileChooser.getSelectedFile();
                JFileChooser saveChooser = new JFileChooser();
                saveChooser.setDialogTitle("Lưu file txt");
                saveChooser.setSelectedFile(new File("output.txt"));
                int saveSelection = saveChooser.showSaveDialog(null);

                if (saveSelection == JFileChooser.APPROVE_OPTION) {
                    File txtFile = saveChooser.getSelectedFile();
                    convertExcelToTxt(excelFile, txtFile);
                }
            }
        });
    }

    public static void convertExcelToTxt(File excelFile, File txtFile) {
        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = new XSSFWorkbook(fis);
             BufferedWriter writer = new BufferedWriter(new FileWriter(txtFile))) {

            Sheet sheet = workbook.getSheetAt(0); // Đọc sheet đầu tiên
            for (Row row : sheet) {
                StringBuilder rowContent = new StringBuilder();
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            rowContent.append(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            rowContent.append(cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            rowContent.append(cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            rowContent.append(cell.getCellFormula());
                            break;
                        default:
                            rowContent.append("");
                            break;
                    }
                    rowContent.append("\t"); // Thêm tab giữa các cột
                }
                writer.write(rowContent.toString().trim());
                writer.newLine(); // Xuống dòng sau mỗi hàng
            }

            JOptionPane.showMessageDialog(null, "Chuyển đổi thành công! File đã được lưu tại: " + txtFile.getAbsolutePath(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Thông báo", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
