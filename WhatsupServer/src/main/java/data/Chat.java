package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat {

	private Map<String, List<Message>> messagesOnUserID;

	private String ID1, ID2;

	public Chat(String ID1, String ID2) {
		this.messagesOnUserID = new HashMap<>();
		this.ID1 = ID1;
		this.ID2 = ID2;

		addMessagnger(ID1);
		addMessagnger(ID2);
	}

	private void addMessagnger(String userID) {
		messagesOnUserID.put(userID, new ArrayList<>());
	}

	public void putNewMessage(String ID, Message m) {
		messagesOnUserID.get(ID).add(m);
	}

	public List<Message> getMessagesAndClear(String ID) {

		List<Message> ret = new ArrayList(messagesOnUserID.get(ID));
		messagesOnUserID.get(ID).clear();

		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID1 == null) ? 0 : ID1.hashCode());
		result = prime * result + ((ID2 == null) ? 0 : ID2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chat other = (Chat) obj;
		if (ID1 == null) {
			if (other.ID1 != null)
				return false;
		} else if ((ID1.equals(other.ID1) && ID2.equals(other.ID2)) || (ID1.equals(other.ID2) && ID2.equals(other.ID1)))
			return true;

		return false;
	}

}
