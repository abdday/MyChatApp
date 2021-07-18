package data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.sun.istack.NotNull;

@XmlRootElement(name = "MessagesThread")
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ Message.class })
public class MessagesThreadOLD {

	@XmlElement(name = "userID1", required = true)
	@NotNull
	private String userID1;
	@XmlElement(name = "userID2", required = true)
	@NotNull
	private String userID2;
	@XmlElement(name = "messages")
	@NotNull
	private List<Message> messages;

	public MessagesThreadOLD() {
		super();
		this.userID1 = "";
		this.userID2 = "";
		this.messages = new ArrayList<>();
	}

	public MessagesThreadOLD(String userID1, String userID2, List<Message> messages) {
		super();
		this.userID1 = userID1;
		this.userID2 = userID2;
		this.messages = messages;
	}

	public String getUserID1() {
		return userID1;
	}

	public String getUserID2() {
		return userID2;
	}

	public List<Message> getMessages() {
		return messages;
	}

}
