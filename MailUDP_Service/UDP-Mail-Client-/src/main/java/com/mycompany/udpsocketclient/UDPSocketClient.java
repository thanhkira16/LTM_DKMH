/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.udpsocketclient;

import GUI.ClientUI;
import GUI.HomeUI;
import Model.Account;
import Model.Mail;
import Model.Request;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class UDPSocketClient {
    
    private static ClientUI loginFrame;
    private static HomeUI home;
    public static void main(String args[])  {
        InetAddress serverAddress;
        
        try {
             serverAddress = InetAddress.getByName("localhost");
             final DatagramSocket clientSocket = new DatagramSocket();
             SwingUtilities.invokeLater(() -> {
                loginFrame = new ClientUI(clientSocket, serverAddress);
                loginFrame.setVisible(true);// Truyền socket vào MainFrame
            });
             int i=0;
            while(true) {
//                    if(i==0) {
//                        Account acc = new Account ("duoinuicholantu6x9@gmail.com", "1306");
//                        Request request = new Request ("login", acc);
//                        send(request, clientSocket, serverAddress);
//                    }
                
                    byte[] receiveData = new byte[1024];
                    //Nhận dữ liệu từ máy chủ
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    //Chuyển dữ liệu thành Object
                    ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Request req = (Request) ois.readObject();
                    System.out.println("Nhận được phản hồi: " + req.getMessage());
                    process(req, clientSocket, serverAddress);
                   i++;
            }
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(UDPSocketClient.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(UDPSocketClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UDPSocketClient.class.getName()).log(Level.SEVERE, null, ex);
        } 
//        finally {
//            if (clientSocket != null && !clientSocket.isClosed()) {
//                clientSocket.close(); // Đóng socket khi không còn cần thiết
//            }
//        }
    }
    
    public static void send(Request req, DatagramSocket clientSocket, InetAddress serverAddress)  {
        try {
            byte[] sendData = new byte[1024];
        
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(req);
            oos.flush();
            sendData = baos.toByteArray();

            // Gửi dữ liệu tới máy chủ
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, serverAddress, 2023);
            clientSocket.send(packet);
            System.out.println("Gửi đối tượng: " + req.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private static void process(Request req, DatagramSocket clientSocket, InetAddress serverAddress) throws IOException {
        switch (req.getMessage()) {
            case "hello" ->  {
                System.err.println(req.getMessage());
                break;
            } 
            
            case "register" ->  {
                 SwingUtilities.invokeLater(() -> {
                        loginFrame.registerResponse((Boolean) req.getData());
                });
                break;
            }
            
            case "login" ->  {
                Account acc = req.getAccount();
                
                if(acc.getID()==0) {
                    System.err.println("notdone");
                } else {
                    
                    System.err.println("done");
                    Request r = new Request("mail/get", acc.getEmail());
                    send( r, clientSocket, serverAddress);
                    SwingUtilities.invokeLater(() -> {
                        loginFrame.dispose();
                        home = new HomeUI(acc, clientSocket, serverAddress);
                        home.setVisible(true);// Truyền socket vào MainFrame
                    });
                }
                break;
            }
            
            case "logout" -> {
                if((Boolean) req.getData()) {
                    System.err.println("done");
                } else {
                    System.err.println("notdone");
                }
                break;
            }
            
            case "mail/send" -> {
                Boolean status = (Boolean) req.getData();
                SwingUtilities.invokeLater(() -> {
                     home.sendMailResponse(status);
                });
            }
            
            case "mail/get" -> {
                System.err.println("get");
                ArrayList<Mail> files = (ArrayList<Mail>) req.getData();
                ArrayList<Mail> mails = convertToMail(files);
                SwingUtilities.invokeLater(() -> {
                     home.setList(mails);
                     
                });
//                Mail mail = new Mail("Hi bro!", "hello, Iam Mai, nice to meet you", "abc@gmail.com", "hello@gmail.com");
//                Request myObject = new Request("mail/send", mail);
//                send( myObject, clientSocket, serverAddress);
            }
            
            case "mail/reload" -> {
                String email =  req.getData().toString();
                System.err.println(email);
                if(email.isBlank()) {
                    System.err.println("notdone");
                } else {
                    System.err.println("done");
                    Request r = new Request("mail/get", email);
                    send( r, clientSocket, serverAddress);
                }
            }
        }
    }
    
    
    public static void readContentFromBytes(byte[] bytes) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
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

    public static ArrayList<Mail> convertToMail(ArrayList<Mail> mails) {
        ArrayList<Mail> list  = new ArrayList<>();
        for (Mail m: mails) {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(m.getByteContent());
                InputStreamReader isr = new InputStreamReader(bais);
                BufferedReader br = new BufferedReader(isr)) {

                String line;
                StringBuilder content = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }
                list.add(new Mail(m.getTitle(), content.toString()));
            } catch (IOException e) {
                System.err.println("Error reading from byte array.");
                e.printStackTrace();
            }
        }
        
        return list;
    }
    
}
