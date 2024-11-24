package Client;

import entity.Course;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

public final class ClientMain extends javax.swing.JFrame {

    public ClientMain(List<Course> list, String fullName, String className) {
        initComponents();
        lblName.setText("Xin chào " + fullName + " - " + className);
        jLabel5.setText(fullName);
        jLabel6.setText(className);
        LoadCourses(list);
        populateComboBox(list);
        setExtendedState(MAXIMIZED_BOTH);
        setup(list);        
        jComboBoxListClass.addActionListener(e -> filterCoursesBySelectedName());   
    }
    
    private void filterCoursesBySelectedName() {
        String selectedCourseName = (String) jComboBoxListClass.getSelectedItem();
        DefaultTableModel modelCourses = (DefaultTableModel) jTableCourses.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelCourses);
        jTableCourses.setRowSorter(sorter);

        if (selectedCourseName != null) {
            RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(selectedCourseName, 2);
            sorter.setRowFilter(filter);
        } else {
            sorter.setRowFilter(null);
        }
    }
    
    private void populateComboBox(List<Course> list) {
        jComboBoxListClass.removeAllItems();

        HashSet<String> addedCourses = new HashSet<>(); 

        for (Course course : list) {
            String courseName = course.getCourseName();
            String classCode = course.getClassCode(); 

            if (!addedCourses.contains(classCode)) {
                jComboBoxListClass.addItem(courseName); 
                addedCourses.add(classCode); 
            }
        }
    }

    private void setup(List<Course> list) {
        LoadCourses(list);
        jTableCourses.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    updateTimeTable();
                }
            }
        });
    }
    
    private void updateTimeTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Tiết", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"});
        for (int i = 1; i <= 8; i++) {
            model.addRow(new Object[]{i, "", "", "", "", "", ""});
        }

        HashSet<String> classCodes = new HashSet<>();
        HashMap<String, String> scheduleMap = new HashMap<>();
        int totalCredits = 0;
        StringBuilder errorMessages = new StringBuilder();
        StringBuilder conflictPeriods = new StringBuilder();

        DefaultTableModel modelCourses = (DefaultTableModel) jTableCourses.getModel();

        for (int i = 0; i < modelCourses.getRowCount(); i++) {
            if ((Boolean) modelCourses.getValueAt(i, 0)) {
                String dayOfWeek = (String) modelCourses.getValueAt(i, 6);
                String startPeriods = (String) modelCourses.getValueAt(i, 7);
                String courseName = (String) modelCourses.getValueAt(i, 2);
                String classCode = (String) modelCourses.getValueAt(i, 4);
                int credits = (Integer) modelCourses.getValueAt(i, 3);

                if (classCodes.contains(classCode)) {
                    errorMessages.append("Mã lớp ").append(classCode).append(" đã được chọn. Vui lòng chọn mã lớp khác.\n");
                    continue;
                }

                if (totalCredits + credits > 25) {
                    errorMessages.append("Tổng số tín chỉ không được vượt quá 25. Vui lòng chọn lại.\n");
                    continue;
                }
                totalCredits += credits;

                int dayIndex = getDayIndex(dayOfWeek);
                String[] periods = startPeriods.split(",\\s*");

                for (String period : periods) {
                    try {
                        int periodIndex = Integer.parseInt(period.trim()) - 1;
                        String key = dayIndex + "-" + periodIndex;

                        if (!scheduleMap.containsKey(key)) {
                            model.setValueAt(courseName, periodIndex, dayIndex);
                            scheduleMap.put(key, courseName);
                            classCodes.add(classCode);
                        } else {
                            conflictPeriods.append("Tiết ").append(period).append(" vào thứ ").append(dayOfWeek).append(" đã có môn học.\n");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        errorMessages.append("Lỗi định dạng số: ").append(period).append("\n");
                    }
                }
            }
        }

        jTableTimeTable.setModel(model);

        if (errorMessages.length() > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, errorMessages.toString(), "Thông báo", JOptionPane.WARNING_MESSAGE);
        }

        if (conflictPeriods.length() > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, conflictPeriods.toString(), "Thông báo", JOptionPane.WARNING_MESSAGE);
        }

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null && !value.toString().isEmpty()) {
                    c.setBackground(Color.YELLOW);
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        };

        for (int col = 1; col < model.getColumnCount(); col++) {
            jTableTimeTable.getColumnModel().getColumn(col).setCellRenderer(renderer);
        }
    }

    private int getDayIndex(String dayOfWeek) {
        switch (dayOfWeek.toLowerCase()) {
            case "thứ 2":
                return 1;
            case "thứ 3":
                return 2;
            case "thứ 4":
                return 3;
            case "thứ 5":
                return 4;
            case "thứ 6":
                return 5;
            case "thứ 7":
                return 6;
            default:
                return -1;  
        }
    }
    
    public void LoadCourses(List<Course> list) {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        String[] columnNames = {
                "Chọn", "ID", "Tên môn học", "STC", "Mã lớp", "SLSV",
                "Thứ", "Tiết", "Tiết kết thúc", "Phòng",
                "Giảng viên", "Ngày bắt đầu", "Ngày kết thúc"
        };

        tableModel.setColumnIdentifiers(columnNames);
        Font font = new Font("Times New Roman", Font.PLAIN, 12);

        for (Course course : list) {
            Object[] rowData = {
                    false,
                    course.getCourseId(), course.getCourseName(), course.getCredits(), course.getClassCode(),
                    course.getStudentCount(), course.getDayOfWeek(), course.getStartPeriod(), course.getTotalPeriods(),
                    course.getRoom(), course.getInstructor(), course.getStartDate(), course.getEndDate()
            };
            tableModel.addRow(rowData);
        }
        jTableCourses.setModel(tableModel);
        jTableCourses.revalidate();
        jTableCourses.repaint();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabelDK = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabelTimeTable = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabelLogout = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanelDKHP = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableTimeTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButtonRender = new javax.swing.JButton();
        lblName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCourses = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxListClass = new javax.swing.JComboBox<>();
        jTextFieldInput = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1232, 6, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 204, 153));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Tên sinh viên");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Lớp");

        jPanel3.setBackground(new java.awt.Color(255, 204, 51));

        jLabelDK.setBackground(new java.awt.Color(255, 255, 51));
        jLabelDK.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabelDK.setText("Đăng ký học phần");
        jLabelDK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelDKMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelDK, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelDK, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 204, 51));

        jLabelTimeTable.setBackground(new java.awt.Color(255, 255, 51));
        jLabelTimeTable.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabelTimeTable.setText("Thời khoá biểu");
        jLabelTimeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelTimeTableMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTimeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTimeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 204, 51));

        jLabelLogout.setBackground(new java.awt.Color(255, 255, 51));
        jLabelLogout.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabelLogout.setText("Đăng xuất");
        jLabelLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelLogoutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel4)
                .addGap(35, 35, 35)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(48, 48, 48)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(324, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 818));

        jTableTimeTable.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTableTimeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tiết", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"
            }
        ));
        jScrollPane2.setViewportView(jTableTimeTable);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Thời khoá biểu");

        jButtonRender.setBackground(new java.awt.Color(0, 102, 51));
        jButtonRender.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jButtonRender.setForeground(new java.awt.Color(0, 204, 102));
        jButtonRender.setText("Render");
        jButtonRender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonRenderMouseClicked(evt);
            }
        });

        lblName.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblName.setText("Xin chào");

        jTableCourses.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(jTableCourses);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Chọn môn");

        jComboBoxListClass.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jComboBoxListClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextFieldInput.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextFieldInput.setText("Nhập thông tin tìm kiếm");

        jButtonSearch.setBackground(new java.awt.Color(255, 204, 0));
        jButtonSearch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButtonSearch.setText("Tìm kiếm");
        jButtonSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelDKHPLayout = new javax.swing.GroupLayout(jPanelDKHP);
        jPanelDKHP.setLayout(jPanelDKHPLayout);
        jPanelDKHPLayout.setHorizontalGroup(
            jPanelDKHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDKHPLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxListClass, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jTextFieldInput, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSearch)
                .addGap(131, 131, 131)
                .addComponent(lblName)
                .addContainerGap(213, Short.MAX_VALUE))
            .addGroup(jPanelDKHPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDKHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanelDKHPLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonRender, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanelDKHPLayout.setVerticalGroup(
            jPanelDKHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDKHPLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanelDKHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxListClass, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldInput, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDKHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
        );

        getContentPane().add(jPanelDKHP, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelLogoutMouseClicked
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có muốn thoát không?", 
            "Xác nhận thoát", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            ClientLogin loginForm = new ClientLogin(); 
            loginForm.setVisible(true);
            
        }
    }//GEN-LAST:event_jLabelLogoutMouseClicked

    private void jLabelTimeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelTimeTableMouseClicked
        jPanelDKHP.setVisible(false);
    }//GEN-LAST:event_jLabelTimeTableMouseClicked

    private void jLabelDKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDKMouseClicked
        jPanelDKHP.setVisible(true);
    }//GEN-LAST:event_jLabelDKMouseClicked

    private void jButtonRenderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRenderMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        fileChooser.setSelectedFile(new java.io.File("timetable.xlsx")); 
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            saveTableToExcel(jTableTimeTable, fileToSave.getAbsolutePath()); 
        }
    }//GEN-LAST:event_jButtonRenderMouseClicked
    
    private void saveTableToExcel(JTable table, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Thời Khóa Biểu");

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < table.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(table.getColumnName(i));
        }

        for (int rowIndex = 0; rowIndex < table.getRowCount(); rowIndex++) {
            Row row = sheet.createRow(rowIndex + 1);
            for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
                Cell cell = row.createCell(colIndex);
                Object value = table.getValueAt(rowIndex, colIndex);
                if (value != null) {
                    if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    } else {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            JOptionPane.showMessageDialog(this, "File đã được lưu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu file: " + e.getMessage(), "Thông báo", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    private void jButtonSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSearchMouseClicked
        String searchText = jTextFieldInput.getText().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) jTableCourses.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        jTableCourses.setRowSorter(sorter);

        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    for (int i = 0; i < entry.getValueCount(); i++) {
                        String cellValue = entry.getStringValue(i).toLowerCase();
                        if (cellValue.contains(searchText)) {
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }//GEN-LAST:event_jButtonSearchMouseClicked
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ClientMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            String fullName = "John Doe";
            String className = "Class A";
            List<Course> list = new ArrayList<>();
            new ClientMain(list, fullName, className).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRender;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JComboBox<String> jComboBoxListClass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelDK;
    private javax.swing.JLabel jLabelLogout;
    private javax.swing.JLabel jLabelTimeTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelDKHP;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableCourses;
    private javax.swing.JTable jTableTimeTable;
    private javax.swing.JTextField jTextFieldInput;
    private javax.swing.JLabel lblName;
    // End of variables declaration//GEN-END:variables
}
