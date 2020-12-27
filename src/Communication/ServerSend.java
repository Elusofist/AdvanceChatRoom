package Communication;

import Server.Server;

import java.util.Formatter;
import java.util.Scanner;

public class ServerSend extends Thread {
    private Formatter socketOut;
    private Scanner socketIn;

    public ServerSend(Formatter socketOut, Scanner socketIn) {
        this.socketOut = socketOut;
        this.socketIn = socketIn;
    }

    @Override
    public void run() {
        while (true) {
            String sendMessage = socketIn.nextLine();
            System.out.println(sendMessage);
            for (Formatter socketOut1: Server.formatters) {
                socketOut1.format(sendMessage+"\n");
                socketOut1.flush();
            }
        }
    }
}