package Server;

import Communication.ServerSend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;


public class Server {
    public static ArrayList<Formatter> formatters = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        final int port = 888;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                Scanner socketIn = new Scanner(socket.getInputStream());
                Formatter socketOut = new Formatter(socket.getOutputStream());
                formatters.add(socketOut);
                ServerSend send = new ServerSend(socketOut, socketIn);
                send.start();
            }

        } catch (IOException exception) {
            System.out.println("Server wasn't connected.");
        }

    }


}
