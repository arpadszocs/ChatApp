package app.client;

import app.MessageListener;
import app.model.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements MessageListener {

	private static Long id = 0L;

	private ObjectInputStream reader;
	private ObjectOutputStream writer;

	public static Long getUserId() {
		return id;
	}

	public void start(InetSocketAddress address) {
		try {
			id++;
			Socket socket = new Socket(address.getHostName(), address.getPort());
			writer = new ObjectOutputStream(socket.getOutputStream());
			reader = new ObjectInputStream(socket.getInputStream());
		} catch (IOException ex) {
			ex.printStackTrace();
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
		} finally {
			try {
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		try {
			return reader.readInt() == 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
