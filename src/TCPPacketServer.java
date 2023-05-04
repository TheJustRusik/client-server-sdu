import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
        DataInputStream dataIn = null;
        try {
            clientWriter = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();
            dataIn = new DataInputStream(in);
        }catch (Exception e){
            System.err.println("Some problems on client side!");
            e.printStackTrace();
            System.exit(0);
        }

        while (true) {
            int size = -1;
            try {
                size = dataIn.readInt();
            }catch (IOException e){
                System.err.println("Client not responding... " + e.getMessage());
                e.printStackTrace();
                System.exit(0);
            }
            if(size == -1){
                System.out.println("Server closed!");
                System.exit(0);
                break;
            }
            byte[] buffer = new byte[size];
            try {
                dataIn.read(buffer);
            }catch (IOException e){
                System.err.println("Client not responding... " + e.getMessage());
                e.printStackTrace();
                System.exit(0);
            }
            Packet packet = new Packet(buffer);

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
