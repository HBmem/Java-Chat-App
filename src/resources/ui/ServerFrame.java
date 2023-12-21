package resources.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import resources.service.ServerThread;

public class ServerFrame extends JFrame implements ActionListener {

    transient Logger logger = Logger.getLogger(getClass().getName());

    private String serverStatus = "OFF";
    private JButton startServerBtn;
    private JTextArea serverLogTextArea;

    private transient List<ServerThread> serverThreads = new ArrayList<>();
    private transient List<PrintWriter> clientWriters = new ArrayList<>();

    private JPanel headerPanel() {
        JPanel header = new JPanel();
        header.setBackground(Color.LIGHT_GRAY);
        header.setBounds(0, 0, 420, 35);

        // Button used to turn the server on or off
        startServerBtn = new JButton();
        startServerBtn.setText("Start Server");
        startServerBtn.setBounds(0, 0, 100, 100);
        startServerBtn.addActionListener(e -> {
            logText("Server Start \n");
            startServerBtn.setEnabled(false);
            startServer();
        });

        header.add(startServerBtn);
        return header;
    }

    private JPanel severLogPanel() {
        JPanel serverLogPanel = new JPanel();
        serverLogPanel.setBackground(Color.GRAY);
        serverLogPanel.setBounds(0, 35, 420, 285);

        serverLogTextArea = new JTextArea(15, 25);
        serverLogTextArea.setLineWrap(true);
        serverLogTextArea.setFont(new Font("Serif", Font.PLAIN, 15));
        JScrollPane serverLogScrollArea = new JScrollPane(serverLogTextArea);

        serverLogPanel.add(serverLogScrollArea);
        return serverLogPanel;
    }

    public ServerFrame() {
        this.setTitle("Chat Server");
        this.setDefaultCloseOperation(3);
        this.setSize(490, 415);
        this.setLayout(null);

        ImageIcon image = new ImageIcon("src/assets/server-icon.png");
        this.setIconImage(image.getImage());

        this.add(headerPanel());
        this.add(severLogPanel());

        this.setResizable(false);
        this.setVisible(true);
    }

    private void startServer() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                serverStatus = "ON";
                try (ServerSocket serverSocket = new ServerSocket(5000)) {
                    while (serverStatus.equals("ON")) {
                        Socket socket = serverSocket.accept();

                        // Create a PrintWriter for this client and add it to the list
                        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                        clientWriters.add(writer);

                        for (ServerThread thread : serverThreads) {
                            thread.addClientWriter(writer);
                            thread.updateServerThreads(writer);
                        }

                        ServerThread serverThread = new ServerThread(socket, clientWriters);
                        serverThread.start();
                        serverThreads.add(serverThread);
                    }
                } catch (IOException e) {
                    logger.info("Error occurred: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                startServerBtn.setEnabled(true);
            }
        };

        worker.execute();
    }

    public void logText(String s) {
        serverLogTextArea.append(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
