package data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class MessageOld<T> {
	@XmlElement(name = "data")
	@NotNull
	private T data;
	@XmlElement(name = "sender", required = true)
	@NotNull
	private ESender sender;
	@XmlElement(name = "messageType")
	@NotNull
	private EMessageType messageType;

	public T getData() {
		return data;
	}

	public ESender getSender() {
		return sender;
	}

	public EMessageType getMessageType() {
		return messageType;
	}

	public MessageOld(@NotNull T data, @NotNull ESender sender, @NotNull EMessageType messageType) {
		super();
		this.data = data;
		this.sender = sender;
		this.messageType = messageType;
	}

	public MessageOld() {
		super();
//		this.data = null;
//		this.sender = null;
//		this.messageType = null;
	}

}
