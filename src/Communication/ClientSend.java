package Communication;

import java.util.Formatter;
import java.util.Scanner;

public class ClientSend extends Thread {
    private String name;
    private Formatter socketOut;
    private Scanner scanner;

    public ClientSend(String name, Formatter socketOut, Scanner scanner) {
        this.name = name;
        this.socketOut = socketOut;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        while (true) {
            String sendMessage = scanner.nextLine();
            if (sendMessage.toLowerCase().startsWith("bye")){
                socketOut.format( "Your friend is offline :( Bye!");
                socketOut.flush();
                System.exit(0);
            }
            int index = sendMessage.indexOf(" ");
            if (sendMessage.length() == index || sendMessage.length() == 0 || index == -1){
                System.out.println("Please Enter message. Your message should starts with " +
                        "the username of recipient.");
            } else {
                String username = sendMessage.substring(0, index);
                sendMessage = sendMessage.substring(index + 1);
                socketOut.format(username + " " + name + " : " + sendMessage + "\n");
                socketOut.flush();
            }
        }
    }
}
