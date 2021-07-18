package serverController;

import java.util.List;

import data.Chat;
import data.User;

public class Checker {

	private List<User> users;
	private List<Chat> chats;

	public Checker(List<User> users, List<Chat> chats) {
		this.users = users;
		this.chats = chats;

	}

	public boolean checkUserNameNotRegistered(String userName) {
		return !checkUserNameRegistered(userName);
	}

	public boolean checkUserRegistered(String ID) {

		return users.stream().anyMatch(u -> u.getID().equals(ID));
	}

	public boolean checkUserNameRegistered(String userName) {

		return users.stream().anyMatch(u -> u.getUsername().equals(userName));
	}

	public boolean IsChat(String ID1, String ID2) {
		Chat tempchat = new Chat(ID1, ID2);
		return chats.stream().filter(c -> c.equals(tempchat)).findFirst().isPresent();
	}

}
