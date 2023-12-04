package resources.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerFrame extends JFrame implements ActionListener {
    
    JButton startServerBtn;
    JScrollPane serverLogScrollArea;
    JTextArea serverLogTextArea;

    private JPanel headerPanel() {
        JPanel header = new JPanel();
        header.setBackground(Color.LIGHT_GRAY);
        header.setBounds(0, 0, 420, 35);

        // Button used to turn the server on or off
        startServerBtn = new JButton();
        startServerBtn.setText("Start Server");
        startServerBtn.setBounds(0, 0, 100, 100);
        startServerBtn.addActionListener(e -> {
            System.out.println("Print!!!");
            serverLogTextArea.append("Server Start\n");
        });

        header.add(startServerBtn);
        return header;
    }

    private JPanel severLogPanel() {
        JPanel serverLogPanel = new JPanel();
        serverLogPanel.setBackground(Color.GRAY);
        serverLogPanel.setBounds(0, 35, 420, 285);

        serverLogTextArea = new JTextArea(15,25);
        serverLogTextArea.setLineWrap(true);
        serverLogTextArea.setFont(new Font("Serif", Font.PLAIN, 15));
        serverLogScrollArea = new JScrollPane(serverLogTextArea);

        serverLogPanel.add(serverLogScrollArea);
        return serverLogPanel;
    }

    public ServerFrame() {
        this.setTitle(getTitle());
        this.setTitle("Chat App");
        this.setDefaultCloseOperation(3);
        this.setSize(490, 415);
        this.setLayout(null);

        ImageIcon image = new ImageIcon("src/assets/window-icon.png");
        this.setIconImage(image.getImage());
        // this.getContentPane().setBackground(Color.gray);

        this.add(headerPanel());
        this.add(severLogPanel());

        this.setResizable(false);
        this.setVisible(true);
    }

    public void logText(String s) {
        serverLogTextArea.append(s + "/n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
