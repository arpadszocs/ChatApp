package app.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private static final String IP = "localhost";
    private static final int port = 9090;

    private boolean running = false;


    public void start() throws IOException {
        Socket socket = new Socket(IP, port);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (true) {
            System.out.println(reader.readLine());
        }
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
