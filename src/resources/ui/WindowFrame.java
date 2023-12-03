package resources.ui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class WindowFrame extends JFrame {
    
    public WindowFrame() {
        this.setTitle(getTitle());
        this.setTitle("Chat App");
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setSize(420, 420);
        this.setVisible(true);

        ImageIcon image = new ImageIcon("src/assets/window-icon.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(Color.gray);
    }
}
