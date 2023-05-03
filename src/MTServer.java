import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MTServer {
    public static void main(String[] args) throws IOException {
        int serverPort = 1024;
        ServerSocket serverSocket = new ServerSocket(serverPort);
        System.out.println("Server started successfully. Waiting for a client...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("New client is pop up! Client info: " + clientSocket);

        Scanner serverReader = new Scanner(System.in);

        BufferedReader clientReader = null;
        PrintStream clientWriter = null;
        try {
            clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientWriter = new PrintStream(clientSocket.getOutputStream(), true);
        }catch (Exception e){
            System.err.println("Some problems on client side!");
            e.printStackTrace();
            System.exit(0);
        }

        while (true) {
            System.out.print("Server (you): ");
            String response = serverReader.nextLine();
            try {
                clientWriter.println(response);
            }catch (Exception e){
                System.err.println("Client is not responding!");
                e.printStackTrace();
                System.exit(0);
            }
            String message = null;
            try {
                message = clientReader.readLine();
            }catch (Exception e){
                System.err.println("Client is not responding!");
                e.printStackTrace();
                System.exit(0);
            }
            if (message.equals("CLOSE")) {
                System.out.println("Client CLOSED connection!");
                break;
            }
            System.out.println("Client: " + message);
        }

        clientSocket.close();
        serverSocket.close();
    }
}
