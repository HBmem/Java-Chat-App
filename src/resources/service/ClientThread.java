package resources.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import resources.ui.ClientFrame;

public class ClientThread extends Thread{
    

    private Socket socket;
    private BufferedReader in;
    private ClientFrame clientFrame;

    public ClientThread(Socket s, ClientFrame clientFrame) {
        this.socket = s;
        this.clientFrame = clientFrame;

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
             while (true) {
                String response = in.readLine();
                if (response == null) {
                    break;
                }
                clientFrame.logTextFromServer(response + "\n");
             }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
