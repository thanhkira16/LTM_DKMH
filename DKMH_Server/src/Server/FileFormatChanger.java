/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileFormatChanger extends JFrame {

    private JButton selectFileButton;

    public FileFormatChanger() {
        // Cài đặt giao diện
        setTitle("Chuyển đổi định dạng tệp");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Nút chọn tệp
        selectFileButton = new JButton("Chọn tệp TXT");
        selectFileButton.setBounds(120, 50, 150, 40);
        add(selectFileButton);

        // Sự kiện khi nhấn nút
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile.getName().endsWith(".txt")) {
                        try {
                            // Đổi định dạng
                            convertFile(selectedFile);
                            JOptionPane.showMessageDialog(null, "Tệp đã được chuyển đổi thành công!");
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Lỗi: Không thể chuyển đổi tệp.");
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn tệp .txt!");
                    }
                }
            }
        });
    }

    private void convertFile(File inputFile) throws IOException {
        // Tạo tệp mới không có định dạng (hoặc định dạng không xác định)
        String newFileName = inputFile.getParent() + File.separator + "ConvertedFile";
        File outputFile = new File(newFileName);

        // Sao chép nội dung từ tệp gốc sang tệp mới
        Files.copy(inputFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileFormatChanger frame = new FileFormatChanger();
            frame.setVisible(true);
        });
    }
}
