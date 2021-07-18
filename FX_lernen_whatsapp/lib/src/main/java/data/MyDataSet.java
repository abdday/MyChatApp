package data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDataSet {

	private final PropertyChangeSupport change = new PropertyChangeSupport(this);
	private User myInfo;
	private List<User> friends;
	// String is the username of friend
	private Map<String, ChatInfo> chatThreads;

	public MyDataSet() {
		myInfo = null;
		friends = new ArrayList<>();
		chatThreads = new HashMap<>();
	}

	public User getMyInfo() {
		return myInfo;
	}

	public void setNewFriend(User friend) {
		friends.add(friend);

		change.firePropertyChange("newFriend", null, friend);
	}

	public void setMyInfo(User myInfo) {
		System.out.println("anzahl listeners ist" + change.getPropertyChangeListeners().length);
		System.out.println("aufgerufen jaa mydataSet");
		if (this.myInfo == myInfo)
			return;
		User old = this.myInfo;
		this.myInfo = myInfo;
		change.firePropertyChange("myInfo", old, myInfo);

		System.out.println("aufgerufen jaa mydataSet after");
	}

	public void addListener(PropertyChangeListener listener) {
		change.addPropertyChangeListener(listener);
		System.out.println("listener added");
	}

	public Map<String, ChatInfo> getChatThreads() {
		return chatThreads;
	}

	public List<User> getFirends() {
		return friends;
	}

}
