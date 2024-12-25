package com.mycompany.udpsocketserver;

import Controller.AccountController;
import Controller.FileController;
import GUI.ServerUI;
import Model.Account;
import Model.Mail;
import Model.OnlineAccount;
import Model.Request;
import utils.AESUtils;
import utils.Base64Utils;
import utils.DigitalSignatureUtils;
import utils.KeyUtils;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.crypto.SecretKey;

public class UDPSocketServer {

    static DatagramSocket serverSocket;
    static List<OnlineAccount> onlineAccount = new ArrayList<>();
    private static ServerUI ui;

    public static void main(String args[]) throws Exception {
        // Initialize DatagramSocket
        serverSocket = new DatagramSocket(2023);
        System.out.println("Server is started");

        // Launch GUI
        SwingUtilities.invokeLater(() -> {
            ui = new ServerUI();
            ui.setVisible(true); // Show server UI
        });

        // Process incoming packets
        while (true) {
            // Create a new thread for each packet received
            new Thread(() -> {
                byte[] receiveData = new byte[1024];
                try {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket); // Receive data from client

                    InetAddress IPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();

                    // Deserialize object from packet
                    ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Request req = (Request) ois.readObject();

                    // Process request and send response
                    Request res = process(req, IPAddress, port);
                    send(res, IPAddress, port);

                    System.out.println("Response sent.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    // Send a response packet
    private static void send(Request res, InetAddress IPAddress, int port) throws IOException {
        byte[] sendData;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(res);
            oos.flush();
            sendData = baos.toByteArray();
        }

        // Create and send DatagramPacket
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        serverSocket.send(sendPacket);
    }

    // Process the request and generate a response
    private static Request process(Request req, InetAddress IPAddress, int port) {
        Request response = new Request();
        try {
            switch (req.getMessage()) {
                case "hello":
                    response = new Request("hello", "Dữ liệu đã nhận!");
                    break;
                case "register":
                    if (AccountController.register(req.getAccount())) {
                        FileController.createFolderAndFile(req.getAccount().getEmail());
                        response = new Request("register", true);
                    } else {
                        response = new Request("register", false);
                    }
                    break;
                case "login":
                    Account acc = AccountController.login(req.getAccount());
                    if (acc.getID() != 0) {
                        onlineAccount.add(new OnlineAccount(acc.getEmail(), IPAddress, port));
                        updateUI();
                    }
                    response = new Request("login", acc);
                    break;
                case "logout":
                    logoutUser(req.getAccount().getEmail());
                    response = new Request("logout", true);
                    updateUI();
                    break;
                case "mail/send":
                    response = handleSendMail(req);
                    break;
                case "mail/get":
                    response = handleGetMail(req);
                    break;
                case "mail/token":
                    response = handleTokenValidation(req);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new Request(req.getMessage(), false);
        }
        return response;
    }

    // Update online account list in UI
    private static void updateUI() {
        SwingUtilities.invokeLater(() -> ui.setOnlineAccount(onlineAccount));
    }

    // Logout user by email
    private static void logoutUser(String email) {
        Iterator<OnlineAccount> iterator = onlineAccount.iterator();
        while (iterator.hasNext()) {
            OnlineAccount o = iterator.next();
            if (o.getEmail().equals(email)) {
                iterator.remove();
                break;
            }
        }
    }

    // Handle mail send request
    private static Request handleSendMail(Request req) {
        Request response = new Request("mail/send", false);
        try {
            Mail mail = req.getMail();
            System.out.println("com.mycompany.udpsocketserver.UDPSocketServer.handleSendMail()" + mail);
            PrivateKey serverPrivateKey = KeyUtils.getServerPrivateKey();
            SecretKey aesKey = DigitalSignatureUtils.decryptKeyWithPrivateKey(mail.getByteContent(), serverPrivateKey);
             System.out.println("decryptKeyWithPrivateKey" + aesKey);
            // Verify the digital signature
            PublicKey clientPublicKey = AccountController.getPublicKeyByEmail(mail.getEmailSend());
            boolean isVerified = DigitalSignatureUtils.verifyAESKeySignature(aesKey, mail.getPath(), clientPublicKey);
            if (!isVerified) {
                return new Request("mail/send", false);
            }

            // Decrypt mail content
            String decryptedContent = AESUtils.decrypt(Base64Utils.decode(mail.getContent()), aesKey);
            mail.setContent(decryptedContent);

            // Save mail and notify receiver
            Boolean status = FileController.createFile(mail.getEmailReceive(), mail.getTitle(), mail.getContent(), mail.getEmailSend(), mail.getSendedAt());
            transfer(mail.getEmailReceive());
            response = new Request("mail/send", status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    // Handle mail get request
    private static Request handleGetMail(Request req) {
        ArrayList<Mail> files = FileController.get2(req.getData().toString());
        if (!files.isEmpty()) {
            readContentFromBytes(files.get(0).getByteContent());
        }
        return new Request("mail/get", files);
    }

    // Handle token validation request
    private static Request handleTokenValidation(Request req) {
        boolean isValid = AccountController.validateToken(req.getAccount());
        return new Request(isValid ? "validToken" : "invalidToken");
    }

    // Read content from byte array
    public static void readContentFromBytes(byte[] data) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             InputStreamReader isr = new InputStreamReader(bais, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading from byte array.");
            e.printStackTrace();
        }
    }

    // Notify the receiver to reload mail
    private static void transfer(String receiver) throws IOException {
        for (OnlineAccount o : onlineAccount) {
            if (o.getEmail().equals(receiver)) {
                send(new Request("mail/reload", receiver), o.getIPAddress(), o.getPort());
                break;
            }
        }
    }
}
