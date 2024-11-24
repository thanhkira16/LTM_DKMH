/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.udpsocketserver;


import Controller.AccountController;
import Controller.FileController;
import GUI.ServerUI;
import Model.Account;
import Model.Mail;
import Model.OnlineAccount;
import Model.Request;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Iterator;
import java.util.List;
import javax.swing.SwingUtilities;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class UDPSocketServer {
    static DatagramSocket serverSocket;
    static List<OnlineAccount> onlineAccount = new ArrayList<>();
    private static ServerUI ui;
    
    public static void main(String args[]) throws Exception
    {  	
        //Khởi tạo DatagramSocket
         serverSocket = new DatagramSocket(2023);
        System.out.println("Server is started");	
        SwingUtilities.invokeLater(() -> {
                ui = new ServerUI();
                ui.setVisible(true);// Truyền socket vào MainFrame
        });
        
//Xử lý
        while(true) {
            //Tạo DatagramPacket nhận Data từ Socket
            new Thread(() -> {
                //THiết lập byte[] nhận và gửi 
                byte[] receiveData = new byte[1024];
                
        
                try {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);    //Sử dụng hàm rêceive để nhận dữ liệu

                    InetAddress IPAddress = receivePacket.getAddress();   //Lấy địa chỉ IP của bên gửi
                    int port = receivePacket.getPort();  //Lấy cổng bên gửi         	

                     // Giải tuần tự hóa đối tượng
                    ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Request req = (Request) ois.readObject();

                    // Gửi phản hồi lại cho máy khách
                    Request res = process(req, IPAddress, port); 
                    send (res, IPAddress, port);
                    System.out.println("gửi phản hồi: ...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
   	}
    }
    
    private static void send(Request res, InetAddress IPAddress, int port) throws IOException {
        byte[] sendData = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(res);
        oos.flush();
        sendData = baos.toByteArray(); 

        //Tạo DatagramPacket để send dữ liệu
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        serverSocket.send(sendPacket);
    }
    
    private static void transfer(String receiver) throws IOException {
        for (OnlineAccount o: onlineAccount) {
            if(o.getEmail().equals(receiver)) {
                
                send(new Request("mail/reload", receiver), o.getIPAddress(), o.getPort());
                break;
            }
        }
    }
    
    private void broadcast(String sender) throws IOException {
        for (OnlineAccount o: onlineAccount) {
            if(o.getEmail().equals(sender)) {
                
            } else {
                send(new Request("mail/reload", true), o.getIPAddress(), o.getPort());
            }
        }
    }
    
    private static Request process(Request req, InetAddress IPAddress, int port) throws IOException {
        Request response = new Request();
        switch (req.getMessage()) {
            case "hello" ->  {
                String responseMessage = "Dữ liệu đã nhận!";
                response = new Request( "hello", responseMessage);
                break;
            }  
            
            case "register" -> {
                Account acc = req.getAccount();
                if (AccountController.register(acc)) { //
                    FileController.createFolderAndFile(acc.getEmail());
                    response = new Request( "register", true);
                } else {
                    response = new Request( "register", false);
                }
                break;
            }
            
            case "login" -> {
                Account acc = AccountController.login(req.getAccount());
                if(acc.getID() != 0) {
                    onlineAccount.add(new OnlineAccount(acc.getEmail(), IPAddress, port));
                    for(OnlineAccount o : onlineAccount) {
                        System.err.println("online: " + o.getEmail() + " " + o.getIPAddress());
                    }
                    SwingUtilities.invokeLater(() -> {
                        ui.setOnlineAccount(onlineAccount);
                    });
                }
                response = new Request( "login", acc);
                break;
            }
            
            case "logout" -> {
                System.err.println("logout: " + IPAddress);
                Iterator<OnlineAccount> iterator = onlineAccount.iterator();
                while (iterator.hasNext()) {
                    OnlineAccount o = iterator.next();
                    if (o.getEmail().equals(req.getAccount().getEmail())) {
                        iterator.remove(); // Sử dụng iterator để xóa
                        response = new Request( "logout", true);
                        break;
                    } else {
                        response = new Request("logout", false);
                    }
                }
                SwingUtilities.invokeLater(() -> {
                        ui.setOnlineAccount(onlineAccount);
                });
                break;
            }
            
            case "mail/send" -> {
                Mail mail = req.getMail();
                Boolean status = FileController.createFile(mail.getEmailReceive(), mail.getTitle(), mail.getContent(), mail.getEmailSend(), mail.getSendedAt());
                transfer(mail.getEmailReceive());
                response = new Request("mail/send", status);
                break;
            }
            
            case "mail/get" -> {
                ArrayList<Mail> files = FileController.get2(req.getData().toString());
                readContentFromBytes(files.get(0).getByteContent());

                response = new Request("mail/get", files);
                break;
            }
        }
        return response;
    }
    
    public static void readContentFromBytes(byte[] data) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             InputStreamReader isr = new InputStreamReader(bais);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line); // In ra nội dung
            }
        } catch (IOException e) {
            System.err.println("Error reading from byte array.");
            e.printStackTrace();
        }
    } 
}

