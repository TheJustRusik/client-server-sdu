import java.io.*;
import java.net.*;

public class TCPPacketServer {
    public static void main(String[] args) {
        int serverPort = 1024;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(serverPort);
        }catch (IOException e){
            System.err.println("Client died! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Server started successfully. Waiting for a client...");
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        }catch (IOException e){
            System.err.println("Client died! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("New client is pop up! Client info: " + clientSocket);


        OutputStream clientWriter = null;
        InputStream in = null;
        ObjectInputStream objectIn = null;

        try {
            clientWriter = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();
            objectIn = new ObjectInputStream(in);
        }catch (Exception e){
            System.err.println("Some problems on client side!");
            e.printStackTrace();
            System.exit(0);
        }
        Packet packet = null;
        while (true) {

            try {
                packet = (Packet) objectIn.readObject();
            }catch (IOException e){
                System.err.println("Client not responding... " + e.getMessage());
                e.printStackTrace();
                System.exit(0);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Packet No#" + packet.getSerialNo() + ": " + packet.getData().toUpperCase());
            if(packet.getData().equals("CLOSE")){
                break;
            }

            try {
                clientWriter.write(0);
            }catch (Exception e){
                System.err.println("Client is not responding!");
                e.printStackTrace();
                System.exit(0);
            }


        }
        try {
            clientSocket.close();
            serverSocket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}
