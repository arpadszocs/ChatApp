package app.model;

import java.io.Serializable;

/**
 * @author Szocs, Arpad (EXTERN: msg DAVID)
 * @version $Id: User.java 31604 11/28/2017 10:31 Szocs, Arpad (EXTERN: msg DAVID) $
 * @since 11/28/2017
 */
public class User implements Serializable {

	private static final long serialVersionUID = -3296694306326449157L;

	private Long id;
	private String name;

	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
