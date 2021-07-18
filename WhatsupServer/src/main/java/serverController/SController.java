package serverController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Chat;
import data.EMyStatus;
import data.FriendsRequests;
import data.IdsGenerator;
import data.Message;
import data.MessagesThread;
import data.ResponseEnvelope;
import data.User;

public class SController {

	private Checker checker;
	private Map<String, List<String>> friends;
	private List<User> users;
	// String is the id
	private List<Chat> chats;

	public SController() {
		friends = new HashMap<>();
		chats = new ArrayList<>();
		users = new ArrayList<>();
		checker = new Checker(users, chats);
	}

	public ResponseEnvelope<User> registerNewUser(String username, String name) {

		if (checker.checkUserNameNotRegistered(username)) {
			String genratedID = IdsGenerator.generateId();
			User user = new User(genratedID, username, name);
			users.add(user);
			friends.put(genratedID, new ArrayList<>());
			ResponseEnvelope<User> ret = new ResponseEnvelope<>(user);
			return ret;
		}
		ResponseEnvelope ret2 = new ResponseEnvelope<>("usrname not valid",
				"user name is already gained by another user");

		return ret2;
	}

	public ResponseEnvelope<User> addFriend(String userID, String FriendUserName) {

		System.out.println("add Friend in server aufgerufen parameter:+  " + userID + " " + FriendUserName);

		ResponseEnvelope Error1 = DoCheksForRegistring(userID, FriendUserName);
		if (Error1.getState() != EMyStatus.OK)
			return Error1;

		ResponseEnvelope Error2 = DoCheksForAddingNewFriend(userID, FriendUserName);
		if (Error2.getState() != EMyStatus.OK)
			return Error2;

		return new ResponseEnvelope<User>(AddFriend(userID, FriendUserName));

	}

	public ResponseEnvelope<User> getUserbyID(String ID) {

		if (!checker.checkUserRegistered(ID)) {
			ResponseEnvelope ret = new ResponseEnvelope<>("Friend usrname does not exist ", "");
			return ret;
		}

		return new ResponseEnvelope<User>(findUserByID(ID));
	}

	private User AddFriend(String userID, String friendUserName) {
		User friend = findUserByUsername(friendUserName);

		friends.get(userID).add(friend.getID());
		System.out.println("new Friend added" + friend.getUsername());
		// friends.get(friend.getID()).add(userID);

		// Now both are friends thus we can create a chat between them
		if (friends.get(friend.getID()).contains(userID)) {
			Chat chat = new Chat(userID, friend.getID());
			chats.add(chat);
			System.out.println("chat created!!");
		}

		User FriendIDNOTKNOWN = new User("fake", friend.getUsername(), friend.getName());
		return FriendIDNOTKNOWN;
	}

	public ResponseEnvelope addMessage(Message m) {

		return null;
	}

	public ResponseEnvelope<MessagesThread> getMessages(String ID, String FromUsername) {

		ResponseEnvelope ch = DoCheksChatExisiting(ID, FromUsername);
		if (ch.getState() != EMyStatus.OK)
			return ch;

		String FromID = findUserByUsername(FromUsername).getID();
		return new ResponseEnvelope<MessagesThread>(new MessagesThread(getMessagesList(ID, FromID)));
	}

	public ResponseEnvelope newMessageSent(String userID, String friendUserName, Message m) {
		ResponseEnvelope ch = DoCheksChatExisiting(userID, friendUserName);
		if (ch.getState() != EMyStatus.OK)
			return ch;

		String friendID = findUserByUsername(friendUserName).getID();

		Chat chat = getChat(userID, friendID);
		chat.putNewMessage(userID, m);

		return new ResponseEnvelope<>();

	}

	private ResponseEnvelope DoCheksForRegistring(String userID, String friendUserName) {
		if (!checker.checkUserRegistered(userID)) {
			// System.out.println("add Friend in server aufgerufen parameter:+ " + ID + " "
			// + FromUsername);
			ResponseEnvelope retEror1 = new ResponseEnvelope<>("User ID not valid",
					"Your User ID is not registered by our server");
			return retEror1;
		}
		if (!checker.checkUserNameRegistered(friendUserName)) {
			ResponseEnvelope retEror2 = new ResponseEnvelope<>("Friend usrname does not exist ", "");
			return retEror2;

		}
		return new ResponseEnvelope<>();
	}

	private ResponseEnvelope DoCheksForAddingNewFriend(String userID, String friendUserName) {
		User friend = findUserByUsername(friendUserName);

		if (userID.equals(friend.getID())) {
			// System.out.println("add Friend in server aufgerufen parameter:+ " + ID + " "
			// + FromUsername);
			ResponseEnvelope retEror1 = new ResponseEnvelope<>("misuse Failure",
					"You can not add your self as a friend");
			return retEror1;
		}
		if (friends.get(userID).contains(friend.getID())) {
			ResponseEnvelope retEror2 = new ResponseEnvelope<>("Can not add friend",
					"you are already has this friend!!");
			return retEror2;

		}
		return new ResponseEnvelope<>();
	}

	public ResponseEnvelope CheckChatExsist(String userID, String friendUserName) {
		return DoCheksChatExisiting(userID, friendUserName);
	}

	private ResponseEnvelope DoCheksChatExisiting(String userID, String friendUserName) {

		ResponseEnvelope retEror = DoCheksForRegistring(userID, friendUserName);
		if (retEror.getState() != EMyStatus.OK)
			return retEror;

		String FromID = findUserByUsername(friendUserName).getID();
		if (!checker.IsChat(userID, FromID)) {
			ResponseEnvelope retEror2 = new ResponseEnvelope<>("No Chat",
					"there is not a chat between them yet to get the messages");
			return retEror2;
		}

		return new ResponseEnvelope<>();

	}

	public ResponseEnvelope<FriendsRequests> listOfFriendshipRequests(String ID) {

		if (!checker.checkUserRegistered(ID)) {
			ResponseEnvelope retEror = new ResponseEnvelope<>("usrname not valid",
					"user name is already gained by another user");
			return retEror;
		}

		List<String> ret = new ArrayList<>();

		for (var EachEntry : friends.entrySet()) {
			if (!EachEntry.getKey().equals(ID)) {
				Inner: for (var friend : EachEntry.getValue()) {
					if (friend.equals(ID) && !hasFriend(ID, EachEntry.getKey())) {
						ret.add(findUserByID(EachEntry.getKey()).getUsername());
						break Inner;
					}
				}
			}
		}

		System.out.println("form Server list ist:" + ret);

		return new ResponseEnvelope<FriendsRequests>(new FriendsRequests(ret));
	}

	private boolean hasFriend(String ID, String friendID) {

		boolean ret = friends.get(ID).contains(friendID);

		System.out.println("data passed " + ID + " " + friendID + " ergebnis : " + ret);

		return ret;
	}

	private List<Message> getMessagesList(String ID, String FromID) {
		return getChat(ID, FromID).getMessagesAndClear(FromID);
	}

	private Chat getChat(String ID1, String ID2) {
		Chat tempchat = new Chat(ID1, ID2);
		return chats.stream().filter(c -> c.equals(tempchat)).findFirst().get();
	}

	private User findUserByUsername(String username) {
		return users.stream().filter(e -> e.getUsername().equals(username)).findFirst().get();
	}

	private User findUserByID(String ID) {
		return users.stream().filter(e -> e.getID().equals(ID)).findFirst().get();
	}

}
