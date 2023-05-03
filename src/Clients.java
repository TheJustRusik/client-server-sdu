import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Clients {
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
        BufferedReader serverListener = null;
        PrintStream serverWriter = null;

        try {
            serverWriter = new PrintStream(socket.getOutputStream(), true);
            serverListener = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }


        while (true) {
            String response = null;
            try {
                response = serverListener.readLine();
            }catch (Exception e){
                System.err.println("Server closed for some reasons...");
                System.err.println("Error:" + e.getMessage());
                e.printStackTrace();
                System.exit(0);
            }
            System.out.println("Server: " + response);

            System.out.print("Client (you): ");
            String message = in.nextLine();

            if (message.equals("CLOSE")) {
                serverWriter.println(message);
                break;
            }
            serverWriter.println(message);
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
