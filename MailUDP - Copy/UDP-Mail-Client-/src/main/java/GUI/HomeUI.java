/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Model.Account;
import Model.Mail;
import Model.Request;
import com.mycompany.udpsocketclient.UDPSocketClient;
import static com.mycompany.udpsocketclient.UDPSocketClient.send;
import java.awt.CardLayout;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author iemmeiemi
 */
public class HomeUI extends javax.swing.JFrame {
    ArrayList<Mail> list;
    DatagramSocket clientSocket;
    InetAddress serverAddress;
    Account acc;
    private DefaultTableModel model;
    /**
     * Creates new form HomeUI
     */
    public HomeUI(Account acc, DatagramSocket clientSocket, InetAddress serverAddress) {
        this.acc = acc;
        this.clientSocket = clientSocket;
        this.serverAddress = serverAddress;
        initComponents();
        model = (DefaultTableModel) tbMail.getModel();
    }

    public ArrayList<Mail> getList() {
        return list;
    }

    public void setList(ArrayList<Mail> list) {
        this.list = list;
        model.setRowCount(0);
        loadData();
    }

    public void loadData() {
        for (Mail mail :list) {
            model.addRow( new Object[] {
                mail.getTitle(), mail.getContent()
            });
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnHome = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnHomeHome = new javax.swing.JButton();
        btnHomeSend = new javax.swing.JButton();
        btnHomeProfile = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMail = new javax.swing.JTable();
        btnOpen = new javax.swing.JButton();
        pnSend = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnHomeHome1 = new javax.swing.JButton();
        btnHomeSend1 = new javax.swing.JButton();
        btnHomeProfile1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        tfSendMail = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfReceiver = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfTitle = new javax.swing.JTextField();
        pnProfile = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnHomeHome2 = new javax.swing.JButton();
        btnHomeSend2 = new javax.swing.JButton();
        btnHomeProfile2 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        lbProUsername = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbProEmail = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mail");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        pnHome.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("HOME CLIENT");
        jPanel2.add(jLabel1);

        pnHome.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        btnHomeHome.setText("HOME");
        btnHomeHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeHomeActionPerformed(evt);
            }
        });
        jPanel3.add(btnHomeHome);

        btnHomeSend.setText("SEND");
        btnHomeSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeSendActionPerformed(evt);
            }
        });
        jPanel3.add(btnHomeSend);

        btnHomeProfile.setText("PROFILE");
        jPanel3.add(btnHomeProfile);

        pnHome.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel4.setLayout(new java.awt.BorderLayout());

        tbMail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Content"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbMail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seeMailDetail(evt);
            }
        });
        jScrollPane1.setViewportView(tbMail);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        btnOpen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnOpen.setText("OPEN MAIL");
        jPanel4.add(btnOpen, java.awt.BorderLayout.PAGE_END);

        pnHome.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnHome, "home");

        pnSend.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("SEND");
        jPanel5.add(jLabel2);

        pnSend.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        btnHomeHome1.setText("HOME");
        btnHomeHome1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeHome1ActionPerformed(evt);
            }
        });
        jPanel6.add(btnHomeHome1);

        btnHomeSend1.setText("SEND");
        jPanel6.add(btnHomeSend1);

        btnHomeProfile1.setText("PROFILE");
        btnHomeProfile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeProfile1ActionPerformed(evt);
            }
        });
        jPanel6.add(btnHomeProfile1);

        pnSend.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        jPanel1.setLayout(new java.awt.BorderLayout());

        tfSendMail.setToolTipText("Enter Content...");
        jPanel1.add(tfSendMail, java.awt.BorderLayout.CENTER);

        btnSend.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSend.setText("SEND");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });
        jPanel1.add(btnSend, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 242, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel8, java.awt.BorderLayout.LINE_START);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 242, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel9, java.awt.BorderLayout.LINE_END);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("To");
        jPanel7.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 100, 20));

        tfReceiver.setToolTipText("Enter the Title");
        tfReceiver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfReceiverActionPerformed(evt);
            }
        });
        jPanel7.add(tfReceiver, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 280, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Title");
        jPanel7.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 100, 20));

        tfTitle.setToolTipText("Enter the Title");
        tfTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTitleActionPerformed(evt);
            }
        });
        jPanel7.add(tfTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(312, -3, 280, 30));

        jPanel1.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        pnSend.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnSend, "send");

        pnProfile.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("PROFILE");
        jPanel10.add(jLabel4);

        pnProfile.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        btnHomeHome2.setText("HOME");
        jPanel11.add(btnHomeHome2);

        btnHomeSend2.setText("SEND");
        jPanel11.add(btnHomeSend2);

        btnHomeProfile2.setText("PROFILE");
        btnHomeProfile2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeProfile2ActionPerformed(evt);
            }
        });
        jPanel11.add(btnHomeProfile2);

        pnProfile.add(jPanel11, java.awt.BorderLayout.PAGE_END);

        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel6.setText("Username");

        jLabel8.setText("Email");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(188, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(lbProEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbProUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(211, 211, 211))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGap(191, 191, 191)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(444, Short.MAX_VALUE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(lbProUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(190, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(242, Short.MAX_VALUE)))
        );

        jPanel12.add(jPanel13, java.awt.BorderLayout.CENTER);

        pnProfile.add(jPanel12, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnProfile, "profile");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeHomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHomeHomeActionPerformed

    private void btnHomeSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeSendActionPerformed
        // TODO add your handling code here:
        ((CardLayout)getContentPane().getLayout()).show(getContentPane(), "send");
    }//GEN-LAST:event_btnHomeSendActionPerformed

    private void btnHomeProfile2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeProfile2ActionPerformed
        // TODO add your handling code here:
        ((CardLayout)getContentPane().getLayout()).show(getContentPane(), "profile");
    }//GEN-LAST:event_btnHomeProfile2ActionPerformed

    private void seeMailDetail(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seeMailDetail
        // TODO add your handling code here:
        int selectedRow = tbMail.getSelectedRow(); // Lấy chỉ số dòng đã chọn
        if (selectedRow >= 0) {
            String title = (String) model.getValueAt(selectedRow, 0); // Lấy tiêu đề
            String content = (String) model.getValueAt(selectedRow, 1); // Lấy nội dung
            JOptionPane.showMessageDialog(this, "Title: " + title + "\nContent: " + content);
        }
    }//GEN-LAST:event_seeMailDetail

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        String content = "Sent at: "+ new Date() +"\n" + tfSendMail.getText();
        String title = tfTitle.getText();
        String receiver = tfReceiver.getText();
        Mail m = new Mail(title, content, acc.getEmail(), receiver);
        Request req = new Request("mail/send", m);
        send(req, clientSocket, serverAddress);
    }//GEN-LAST:event_btnSendActionPerformed

    public void sendMailResponse(Boolean status) {
        if(status) {
            showDialog("Mail Send", "Successfully!");
            tfSendMail.setText("");
            tfReceiver.setText("");
            tfTitle.setText("");
        } else {
            showDialog("Mail Send", "Failed!");

        }
    }
    
    public void showDialog(String title, String content) {
        JOptionPane.showMessageDialog(this, "Title: " + title + "\nContent: " + content);
    }
    
    private void tfReceiverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfReceiverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfReceiverActionPerformed

    private void tfTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTitleActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
         int confirm = JOptionPane.showConfirmDialog(
                    HomeUI.this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    send(new Request("logout", acc), clientSocket, serverAddress);
                    System.exit(0); // Đóng ứng dụng
                }
    }//GEN-LAST:event_formWindowClosing

    private void btnHomeHome1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeHome1ActionPerformed
        // TODO add your handling code here:
        ((CardLayout)getContentPane().getLayout()).show(getContentPane(), "home");

    }//GEN-LAST:event_btnHomeHome1ActionPerformed

    private void btnHomeProfile1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeProfile1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHomeProfile1ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(HomeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(HomeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(HomeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(HomeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new HomeUI(clientSocket, serverAddress).setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHomeHome;
    private javax.swing.JButton btnHomeHome1;
    private javax.swing.JButton btnHomeHome2;
    private javax.swing.JButton btnHomeProfile;
    private javax.swing.JButton btnHomeProfile1;
    private javax.swing.JButton btnHomeProfile2;
    private javax.swing.JButton btnHomeSend;
    private javax.swing.JButton btnHomeSend1;
    private javax.swing.JButton btnHomeSend2;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbProEmail;
    private javax.swing.JLabel lbProUsername;
    private javax.swing.JPanel pnHome;
    private javax.swing.JPanel pnProfile;
    private javax.swing.JPanel pnSend;
    private javax.swing.JTable tbMail;
    private javax.swing.JTextField tfReceiver;
    private javax.swing.JTextField tfSendMail;
    private javax.swing.JTextField tfTitle;
    // End of variables declaration//GEN-END:variables
}