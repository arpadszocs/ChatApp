package app.server;

import app.MessageListener;
import app.model.Message;
import app.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;

import static app.server.Server.ADDRESS;
import static app.server.Server.PORT;

public class ServerThread extends Thread implements MessageListener {

	private List<ServerThread> clients;

	private Socket clientSocket;

	private ObjectOutputStream writer;
	private ObjectInputStream reader;

	public ServerThread(Socket clientSocket, List<ServerThread> clients) {
		this.clientSocket = clientSocket;
		this.clients = clients;
	}

	@Override
	public void run() {
		super.run();
		try {
			clientSocket.setSoTimeout(1000);
			writer = new ObjectOutputStream(clientSocket.getOutputStream());
			reader = new ObjectInputStream(clientSocket.getInputStream());
			writer.writeInt(1);
			send(composeMessage("system", "Connected to: " + ADDRESS + ": " + PORT));
			while (true) {
				if (reader.readInt() == 1) {
					for (ServerThread serverThread : clients) {
						serverThread.send((Message) reader.readObject());
					}
				}
				writer.writeInt(1);
			}
		} catch (IOException ex) {
			System.out.println(clientSocket.getInetAddress().getHostAddress() + " has disconnected");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Message receive() {
		try {
			return (Message) reader.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void send(Message message) {
		try {
			writer.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ping() {
		try {
			writer.write(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean ready() {
		return false;
	}

	private Message composeMessage(String userName, String message) {
		return new Message(1L, new User(-1L, userName), LocalDateTime.now(), message);
	}
}
