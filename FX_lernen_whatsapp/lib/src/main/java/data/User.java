package data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

import com.sun.istack.NotNull;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
public class User {

	@XmlElement(name = "ID")
	@NotNull
	private String ID;

	@XmlElement(name = "username")
	@NotNull
	@Length(min = 5, max = 20)
	private String username;

	@XmlElement(name = "name")
	@NotNull
	private String name;

	public String getID() {
		return ID;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Friend [ID=" + ID + ", username=" + username + ", name=" + name + "]";
	}

	public User(String iD, String username, String name) {
		super();
		ID = iD;
		this.username = username;
		this.name = name;
	}

	public User() {
		super();
		ID = null;
		this.username = null;
		this.name = null;
	}

}
