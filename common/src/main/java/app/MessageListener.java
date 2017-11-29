package app;

import app.model.Message;

/**
 * @author Szocs, Arpad (EXTERN: msg DAVID)
 * @version $Id: MessageListener.java 31604 11/27/2017 13:27 Szocs, Arpad (EXTERN: msg DAVID) $
 * @since 11/27/2017
 */
public interface MessageListener {

	Message receive();

	void send(Message message);

	void ping();

	boolean ready();
}
