package app.server;

import app.MessageListener;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {

    private Socket socket;
    private final MessageListener messageListener;

    private Scanner scanner = null;

    public ServerThread(Socket socket, MessageListener messageListener) {
        this.socket = socket;
        this.messageListener = messageListener;
    }

    @Override
    public void run() {
        String response;
        while (true) {
            try {
                scanner = new Scanner(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ((response = scanner.nextLine()) != null) {
                System.out.println(response);
                try {
                    messageListener.accept(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


    }
}
