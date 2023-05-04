import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPPacketClient {
    public static void main(String[] args) {

        String serverAddress = "localhost";
        int serverPort = 1024;
        Socket socket = null;

        while (true){
            try{
                socket = new Socket(serverAddress, serverPort);
                break;
            }catch (Exception e){
                System.out.println("Failed to connect to the server. Reconnect after 2sec... ");

                try {
                    Thread.sleep(2000);
                }catch (InterruptedException b){
                    System.err.println("CAN'T WAIT 2 SEC!");
                }

            }
        }

        System.out.println("Successfully connected to server!");

        Scanner in = new Scanner(System.in);
        DataOutputStream dataOut = null;
        OutputStream serverWriter = null;
        try {
            serverWriter = socket.getOutputStream();
            dataOut = new DataOutputStream(serverWriter);
        }catch (IOException e){
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        Packet packet;
        int packetNum = 0;
        while (true) {
            System.out.print("Enter the data packet: ");
            String message = in.nextLine();
            packet = new Packet(message, packetNum);

            if (message.equals("CLOSE")) {
                System.out.println("Stopping server...");
                try {
                    dataOut.writeInt(-1);
                }catch (IOException e){
                    System.err.println("Server not responding! Error: " + e.getMessage());
                    e.printStackTrace();
                    System.exit(0);
                }
                System.exit(0);
                break;
            }
            try {
                dataOut.writeInt(packet.toBytes().length);
                serverWriter.write(packet.toBytes());
            }catch (IOException e){
                System.err.println("Server not responding! Error: " + e.getMessage());
                e.printStackTrace();
                System.exit(0);
            }
            ++packetNum;
        }
        try {
            socket.close();
        }catch (IOException e){
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
}
