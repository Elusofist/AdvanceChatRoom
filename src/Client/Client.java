package Client;

import Communication.ClientSend;
import DataBase.ClientTableHandler;
import DataBase.ConnectionHandler;
import DataBase.SameUserNameExistsException;
import DataBase.UserNotFoundException;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.Scanner;

public class Client {
    private String username;
    private String password;

    public Client(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static void main(String[] args) throws IOException {
        try {
            final int port = 888;
            Socket socket = new Socket("localhost", port);
            Scanner socketIn = new Scanner(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);
            Formatter socketOut = new Formatter(socket.getOutputStream());
            Client client = login();
            System.out.println("Welcome to chatroom. type 'bye' to exit.");
            ClientSend send = new ClientSend(client.username, socketOut, scanner);
            send.start();
            while (true) {
                String receivedMessage = socketIn.nextLine().trim();
                int index = receivedMessage.indexOf(" ");
                String username = receivedMessage.substring(0, index);
                if (username.trim().equalsIgnoreCase(client.username)) {
                    receivedMessage = receivedMessage.substring(index + 1);
                    System.out.println(receivedMessage);
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static Client login() throws SQLException {
        System.out.println("Please login to your account ");
        boolean valid = false;
        String username = null;
        String password = null;
        while (!valid) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Username :");
            username = scanner.nextLine();
            System.out.println("Password :");
            password = scanner.nextLine();
            ClientTableHandler clientHandler = new ClientTableHandler();
            try {
                valid = clientHandler.check(username, password);
            } catch (UserNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.println("Do you want to sign up?");
                String answer = scanner.next();
                if (answer.toLowerCase().equals("yes")) {
                    while (true) {
                        try {
                            signUp(username, password);
                            break;
                        } catch (SameUserNameExistsException sameUserNameExistsException) {
                            System.out.println(sameUserNameExistsException.getMessage());
                            System.out.println("enter another username. your password is the previous one.");
                            username = scanner.next();
                            try {
                                signUp(username, password);
                            } catch (SameUserNameExistsException userNameExistsException) {
                                userNameExistsException.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return new Client(username, password);
    }

    private static void signUp(String username, String password) throws SQLException, SameUserNameExistsException {
        ClientTableHandler.addRecord(new String[]{username, password});
    }
}