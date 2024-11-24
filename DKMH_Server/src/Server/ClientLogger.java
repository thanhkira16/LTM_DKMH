package Server;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientLogger {
    private JFrame frame;
    private JTextArea logArea;
    private ConcurrentHashMap<Integer, String> connectedClients;
    private AtomicInteger clientCounter;

    public ClientLogger() {
        connectedClients = new ConcurrentHashMap<>();
        clientCounter = new AtomicInteger(0);
        setupUI();
    }

    private void setupUI() {
        frame = new JFrame("Server Log");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void logConnection(String clientInfo) {
        int clientId = clientCounter.incrementAndGet();
        connectedClients.put(clientId, clientInfo);
        logMessage("Client connected: [ID=" + clientId + ", Info=" + clientInfo + "]");
    }

    public void logDisconnection(int clientId) {
        String clientInfo = connectedClients.remove(clientId);
        if (clientInfo != null) {
            logMessage("Client disconnected: [ID=" + clientId + ", Info=" + clientInfo + "]");
        } else {
            logMessage("Unknown client disconnected: [ID=" + clientId + "]");
        }
    }

    public void logQuery(int clientId, String query) {
        String clientInfo = connectedClients.get(clientId);
        if (clientInfo != null) {
            logMessage("Query executed by [ID=" + clientId + ", Info=" + clientInfo + "]: " + query);
        } else {
            logMessage("Query executed by unknown client [ID=" + clientId + "]: " + query);
        }
    }

    private void logMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
}
