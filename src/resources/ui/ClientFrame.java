package resources.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
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
import javax.swing.SwingConstants;

import resources.service.ClientThread;

public class ClientFrame extends JFrame implements ActionListener {

    private final int PORT = 5000;

    private JScrollPane clientLogScrollArea;
    private JTextArea clientLogTextArea;
    private boolean connected = false;
    private String clientName = "";
    private String userText;

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
            if (t.equals("")) {
                System.out.println("Please enter a username");
            } else {
                System.out.println("Username is there!");
                this.clientName = usernameTextField.getText();
                logText("Your username is " + t);
                connect();
            }
        });

        usernameTextField.setPreferredSize(new Dimension(250, 40));
        usernameTextField.setFont(new Font("Serif", Font.PLAIN, 15));
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
        clientLogTextArea.setFont(new Font("Serif", Font.PLAIN, 15));
        clientLogScrollArea = new JScrollPane(clientLogTextArea);

        clientLogPanel.add(clientLogScrollArea);
        return clientLogPanel;
    }

    private JPanel footerPanel() {
        JPanel footer = new JPanel();
        footer.setBackground(Color.LIGHT_GRAY);
        footer.setBounds(0, 320, 420, 100);

        JTextField clientTextArea = new JTextField();
        clientTextArea.setPreferredSize(new Dimension(250, 40));
        clientTextArea.setFont(new Font("Serif", Font.PLAIN, 15));
        clientTextArea.addActionListener(e -> {
            System.out.println("Enter Pressed");
            this.userText = clientTextArea.getText();
            clientTextArea.setText("");
        });

        footer.add(clientTextArea);
        return footer;
    }

    public ClientFrame() {
        this.setTitle("Chat Client");
        this.setDefaultCloseOperation(3);
        this.setSize(490, 460);
        this.setLayout(null);

        ImageIcon image = new ImageIcon("src/assets/client-icon.png");
        this.setIconImage(image.getImage());

        this.add(headerPanel());
        this.add(clientLogPanel());
        this.add(footerPanel());

        this.setResizable(false);
        this.setVisible(true);
    }

    public void connect() {
        System.out.println("tes1");
        if (clientName.isEmpty()) {
            System.out.println("tes1");
            try (Socket socket = new Socket("localhost", PORT)) {
                System.out.println("Starting connection!");
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                String userInput;
                String response;

                ClientThread clientThread = new ClientThread(socket);
                clientThread.start();

                System.out.println("Listening!");
                do {
                    String message = ( "(" + clientName + "):");
                    logText(message);
                    userInput = userText;
                    logText(userInput + "/n");
                } while (!userInput.equals(".exit"));
            } catch (Exception e) {
                System.out.println("Exception occurred in client:" + e.getStackTrace());
            }
        } else {
            System.out.println("");
        }
    }

    public void logText(String s) {
        clientLogTextArea.append(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
