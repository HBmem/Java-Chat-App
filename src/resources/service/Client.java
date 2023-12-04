package resources.service;

public class Client {
    // private Socket socket = null;
    // private DataInputStream input = null;
    // private DataOutputStream out = null;

    // public Client(String address, int port) {
    //     // Attempt to make a connection to the server
    //     try {
    //         socket = new Socket(address, port);
    //         System.out.println("Client Connected");

    //         // Takes input
    //         input = new DataInputStream(System.in);

    //         // Sends output to the socket
    //         out = new DataOutputStream(
    //                 socket.getOutputStream());
    //     } catch (UnknownHostException u) {
    //         System.out.println(u);
    //         return;
    //     } catch (IOException i) {
    //         System.out.println(i);
    //         return;
    //     }

    //     String line = "";

    //     while (!line.equals("Exit")) {
    //         try {
    //             line = input.readLine();
    //             out.writeUTF(line);
    //         } catch (IOException i) {
    //             System.out.println(i);
    //         }
    //     }

    //     try {
    //         input.close();
    //         out.close();
    //         socket.close();
    //     } catch (IOException i) {
    //         System.out.println(i);
    //     }
    // }
    
}