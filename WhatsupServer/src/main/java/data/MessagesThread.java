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
public class MessagesThread {

	@XmlElement(name = "messages")
	@NotNull
	private List<Message> messages;

	public MessagesThread() {
		super();
		this.messages = new ArrayList<>();
	}

	public MessagesThread(List<Message> messages) {
		super();
		this.messages = messages;
	}

	public List<Message> getMessages() {
		return messages;
	}

}
