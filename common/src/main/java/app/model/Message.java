package app.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Szocs, Arpad (EXTERN: msg DAVID)
 * @version $Id: Message.java 31604 11/28/2017 16:30 Szocs, Arpad (EXTERN: msg DAVID) $
 * @since 11/28/2017
 */
public class Message implements Serializable {

	private static final long serialVersionUID = -4813839444819419527L;

	private Long id;
	private User user;

	private LocalDateTime date;

	private String message;

	public Message(Long id, User user, LocalDateTime date, String message) {
		this.id = id;
		this.user = user;
		this.date = date;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}
}
