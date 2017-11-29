package app.server;

import app.exception.ServerFullException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public final class Server {

	static final int PORT = 4444;
	private static final int POOL_SIZE = 10;
	static InetAddress ADDRESS;

	private static ServerSocket serverSocket;

	private List<ServerThread> clients = new ArrayList<>(POOL_SIZE);

	public void start() {
		try {
			ADDRESS = InetAddress.getByName(null);
			serverSocket = new ServerSocket(PORT, 0, ADDRESS);
			System.out.println("Started on host:" + ADDRESS.getHostAddress() + ":" + PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				connect(socket);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void connect(Socket clientSocket) throws IOException {
		if (clients.size() > POOL_SIZE) {
			throw new ServerFullException();
		}
		System.out.println("A client has been connected");
		ServerThread serverThread = new ServerThread(clientSocket, clients);
		this.clients.add(serverThread);
		serverThread.start();
	}
}
