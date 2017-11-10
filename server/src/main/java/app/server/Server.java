package app.server;

import app.MessageListener;
import exception.ServerAlreadyStartedException;
import exception.ServerFullException;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Server {

    private static final int POOL_SIZE = 10;
    private static final int PORT = 9090;
    private static ServerSocket serverSocket;

    private static boolean running = false;


    private List<Socket> sockets = new ArrayList<>();

    public void start() throws IOException {
        if (isRunning()) {
            throw new ServerAlreadyStartedException("Started on: " + PORT);
        }
        running = true;
        serverSocket = new ServerSocket(PORT);
        System.out.println("Started on :" + PORT);
        while (running) {
            Socket socket = serverSocket.accept();
            connect(socket);
        }
    }

    public static void stop() {
        running = false;
    }

    private void connect(Socket socket) {
        if (sockets.size() > POOL_SIZE) {
            throw new ServerFullException();
        }
        this.sockets.add(socket);
        ServerThread serverThread = new ServerThread(socket, listener);
        serverThread.start();
    }

    private void disconnect() {

    }

    public static boolean isRunning() {
        return running;
    }

    MessageListener listener = message -> {
        Iterator<Socket> it = sockets.iterator();
        while (it.hasNext()) {
            PrintStream ps = (PrintStream) it.next().getOutputStream();
            ps.println("Have got message: " + message);
        }
    };
}
