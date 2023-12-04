package resources.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread{
    

    private Socket socket;
    private BufferedReader in;

    public ClientThread(Socket s) {
        this.socket = s;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while(true) {
                String response = in.readLine();
                System.out.println(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
