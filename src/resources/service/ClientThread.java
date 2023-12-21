package resources.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

import resources.ui.ClientFrame;

public class ClientThread extends Thread {

    Logger logger = Logger.getLogger(getClass().getName());

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

    @Override
    public void run() {
        try {
            while (true) {
                String response = in.readLine();
                if (response == null) {
                    break;
                }
                logger.info(response);
                clientFrame.logTextFromServer(response + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                socket.close();
                clientFrame.activateBtn();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
