package data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;

public class ChatInfo {

	final PropertyChangeSupport changes = new PropertyChangeSupport(ChatInfo.class);

	private User friend;
	// private VBox chatBox;
	private List<Node> chatHBoxes;
	private boolean chatAllowed;

	public ChatInfo(User friend) {
		this.friend = friend;
		// chatBox = new VBox();
		chatHBoxes = new ArrayList<>();
		this.chatAllowed = false;

	}

	public boolean isChatAllowed() {
		return chatAllowed;
	}

	public void setChatAllowed(boolean chatAllowed) {
		this.chatAllowed = chatAllowed;
	}

	public User getFriend() {
		return friend;
	}

//	public void setChatBox(VBox box) {
//		chatBox.getChildren().addAll(box.getChildren());
//	}

//	public VBox getChatBox() {
//		return chatBox;
//	}

	public PropertyChangeSupport getChangesSupporter() {
		return changes;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changes.addPropertyChangeListener(listener);
	}

	public List<Node> getChatNodes() {
		return chatHBoxes;
	}

	public void addNewMessageNode(Node n) {
		chatHBoxes.add(n);
	}

}
