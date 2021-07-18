package data;

import javafx.scene.layout.HBox;

public class LabledMessage {

	private String FriendUsername;
	private HBox messageBox;

	public LabledMessage(String friendUsername, HBox messageBox) {
		super();
		FriendUsername = friendUsername;
		this.messageBox = messageBox;
	}

	public String getFriendUsername() {
		return FriendUsername;
	}

	public HBox getMessageBox() {
		return messageBox;
	}

}
