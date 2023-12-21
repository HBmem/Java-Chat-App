package resources.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import resources.service.ClientThread;

public class ClientFrame extends JFrame implements ActionListener {

    private static final int PORT = 5000;
    private static final String FONT = "Serif";

    private JTextArea clientLogTextArea;

    private transient Socket socket;
    private transient BufferedReader input;
    private transient PrintWriter output;

    private boolean connected = false;
    private String clientName = "";

    public ClientFrame() {
        this.setTitle("Chat Client");
        this.setDefaultCloseOperation(3);
        this.setSize(490, 460);
        this.setLayout(null);

        ImageIcon image = new ImageIcon("src/assets/client-icon.png");
        this.setIconImage(image.getImage());

        initializeUI();
    }

    public void initializeUI() {
        this.add(headerPanel());
        this.add(clientLogPanel());
        this.add(footerPanel());

        this.setResizable(false);
        this.setVisible(true);
    }

    private JPanel headerPanel() {
        JButton connectBtn = new JButton();
        JTextField usernameTextField = new JTextField();

        JPanel header = new JPanel();
        header.setBackground(Color.LIGHT_GRAY);
        header.setLayout(null);
        header.setBounds(0, 0, 420, 34);

        connectBtn.setText("Connect to Server");
        connectBtn.setBounds(10, 3, 140, 28);
        connectBtn.setBorder(null);
        connectBtn.addActionListener(e -> {
            String t = usernameTextField.getText();
            if (t.isEmpty()) {
                logText("Please enter a username\n");
            } else {
                clientName = t;
                logText("Your username is " + t + "\n");
                connectToServer();
            }
        });

        usernameTextField.setPreferredSize(new Dimension(250, 40));
        usernameTextField.setFont(new Font(FONT, Font.PLAIN, 15));
        usernameTextField.setBounds(160, 3, 260, 28);
        header.add(usernameTextField);

        header.add(connectBtn);
        return header;
    }

    private JPanel clientLogPanel() {
        JPanel clientLogPanel = new JPanel();
        clientLogPanel.setBackground(Color.GRAY);
        clientLogPanel.setBounds(0, 35, 420, 285);

        clientLogTextArea = new JTextArea(15, 25);
        clientLogTextArea.setLineWrap(true);
        clientLogTextArea.setFont(new Font(FONT, Font.PLAIN, 15));
        JScrollPane clientLogScrollArea = new JScrollPane(clientLogTextArea);

        clientLogPanel.add(clientLogScrollArea);
        return clientLogPanel;
    }

    private JPanel footerPanel() {
        JPanel footer = new JPanel();
        footer.setBackground(Color.LIGHT_GRAY);
        footer.setBounds(0, 320, 420, 100);

        JTextField clientTextArea = new JTextField();
        clientTextArea.setPreferredSize(new Dimension(250, 40));
        clientTextArea.setFont(new Font(FONT, Font.PLAIN, 15));
        clientTextArea.addActionListener(e -> {
            String t = clientTextArea.getText();
            sendMessageToServer("(" + clientName + "): " + t);
            clientTextArea.setText("");
        });

        footer.add(clientTextArea);
        return footer;
    }

    private void connectToServer() {
        if (!clientName.isEmpty()) { 
            try {
                socket = new Socket("localhost", PORT);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);

                output.println(clientName + " has joined the server"); // Send username to server

                ClientThread clientThread = new ClientThread(socket, this);
                clientThread.start();

                connected = true;
            } catch (Exception e) {
                logText("Exception occurred in client: " + e.getMessage() + "\n");
                closeConnection();
            }

        }
    }

    private void closeConnection() {
        try {
            if (output != null) output.close();
            if (input != null) input.close();
            if (socket != null) socket.close();
            connected = false;
        } catch (IOException e) {
            logText("Error while closing connection: " + e.getMessage() + "\n");
        }
    }
    public void sendMessageToServer(String message) {
        if (connected && output != null) {
            output.println(message);
        } else {
            logText("Not connected to the server\n");
        }
    }

    public void logTextFromServer(String message) {
        logText(message);
    }

    public void logText(String s) {
        clientLogTextArea.append(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
