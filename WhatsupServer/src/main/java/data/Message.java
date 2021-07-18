package data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class Message<T> {
	@XmlElement(name = "data")
	@NotNull
	private T data;

	@XmlElement(name = "messageType")
	@NotNull
	private EMessageType messageType;

	public T getData() {
		return data;
	}

	public EMessageType getMessageType() {
		return messageType;
	}

	public Message(@NotNull T data, @NotNull EMessageType messageType) {
		super();
		this.data = data;
		this.messageType = messageType;
	}

	public Message() {
		super();
//		this.data = null;
//		this.sender = null;
//		this.messageType = null;
	}

}
