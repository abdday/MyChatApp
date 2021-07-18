package appController;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import data.ChatInfo;
import data.EMessageOwner;
import data.EMessageType;
import data.LabledMessage;
import data.Message;
import data.MyDataSet;
import data.User;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import myExceptions.FieldEmptyException;
import myExceptions.ServerException;

public class AppController {

	private MyDataSet model;
	private Network myNetwork;
	private ScheduledExecutorService schedular;
	private ScheduledService<List<Map<ChatInfo, List<Message>>>> service;
	private String currentFriendUsername;

	public AppController(MyDataSet model) {
		myNetwork = new Network(this);
		this.model = model;
		schedular = Executors.newSingleThreadScheduledExecutor();
		this.currentFriendUsername = "UnKnownYet";
	}

	public void register(String username, String name) throws ServerException, FieldEmptyException, Exception {
		if (username.isEmpty() || name.isEmpty())
			throw new FieldEmptyException("you should fill all fields");

		setMyInfo(myNetwork.registerMe(username, name));
	}

	public void addFreund(String friendUsername) throws ServerException, FieldEmptyException {
		if (friendUsername.isEmpty())
			throw new FieldEmptyException("you should fill all fields");

		User friend = myNetwork.addFriend(friendUsername);
		setNewFriend(friend);

	}

	private void setMyInfo(User user) {
		System.out.println("setMyInfo functoin : my id is " + user.getID());
		model.setMyInfo(user);
	}

	private void setNewFriend(User user) {
		model.setNewFriend(user);
	}

	public void showNewFriendsRequests() throws ServerException {
		System.out.println("Before showNewFriendsRequests\n\n\n");
		List<String> list = getFriendsRequests();

		System.out.println("After showNewFriendsRequests\n\n\n");

		list.forEach(friendUserName -> {
			try {
				addFreund(friendUserName);
			} catch (FieldEmptyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private List<String> getFriendsRequests() throws ServerException {

		return myNetwork.getFriendsRequests();
	}

	public ChatInfo getChatInfo(String friendUsername, PropertyChangeListener listener) throws ServerException {
		if (!model.getChatThreads().containsKey(friendUsername)) {
			User friend = model.getFirends().stream().filter(f -> f.getUsername().equals(friendUsername)).findFirst()
					.get();
			ChatInfo ret = new ChatInfo(friend);
			ret.addPropertyChangeListener(listener);
			System.out.println("created new chat info in getChatInfo");

			boolean chatAllowed = myNetwork.IsChatAllowed(friendUsername);
			ret.setChatAllowed(chatAllowed);
			model.getChatThreads().put(friendUsername, ret);
		}
		ChatInfo ret = model.getChatThreads().get(friendUsername);
		if (!ret.isChatAllowed()) {
			boolean chatAllowed = myNetwork.IsChatAllowed(friendUsername);
			ret.setChatAllowed(chatAllowed);
		}
		return ret;
	}

	public void sendStringMessage(String friendUsername, String message) throws ServerException, FieldEmptyException {
		if (message.isEmpty())
			throw new FieldEmptyException("you should fill all fields");

		Message<String> m = new Message<>(message, EMessageType.TEXT);

		myNetwork.sendMessage(friendUsername, m);

	}

	public HBox setNewMessageIntoData(ChatInfo chatInfo, Message message, EMessageOwner who) {

		HBox hBox = new HBox();
		if (who == EMessageOwner.ME)
			hBox.setAlignment(Pos.TOP_RIGHT);
		else {
			hBox.setAlignment(Pos.TOP_LEFT);
		}

		synchronized (getClass()) {

			if (message.getMessageType() == EMessageType.TEXT) {
				Label messageLabel = new Label(((String) message.getData()));
				messageLabel.setWrapText(true);
				hBox.getChildren().add(messageLabel);
				messageLabel.setMaxWidth(600);
			}
//		DoubleProperty num = new SimpleDoubleProperty(-100);
//		messageLabel.maxWidthProperty().add(chatCenter.widthProperty());
//		chatCenter.widthProperty().add(num);

			// ChatInfo info = controller.getChatInfo(friendUsername.getText());
			chatInfo.addNewMessageNode(hBox);

			if (who == EMessageOwner.FRIEND) {
				chatInfo.getChangesSupporter().firePropertyChange("newMessage", null,
						new LabledMessage(chatInfo.getFriend().getUsername(), hBox));
			}

		} // synchronized
		return hBox;

	}

	public void beginRequestingNewInfo() {

		// schedular.scheduleAtFixedRate(() -> getMessages(), 0, 3, TimeUnit.SECONDS);
		getMessages();

		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				// TODO Auto-generated method stub
				for (var eachItem : service.getValue())
					for (var eachEntry : eachItem.entrySet()) {

						System.err.println("new add funktioniert beginRequestingNewInfo");
						for (var m : eachEntry.getValue())
							setNewMessageIntoData(eachEntry.getKey(), m, EMessageOwner.FRIEND);
					}
			}
		});

		service.setPeriod(Duration.seconds(2));
		service.start();

	}

	public void setCurrentFriendUserNmae(String friendUsername) {
		this.currentFriendUsername = friendUsername;
	}

	public void shutDownSchedular() {
		// schedular.shutdown();
		if (service != null)
			service.cancel();
	}

	public void getMessages() {

		service = new ScheduledService<List<Map<ChatInfo, List<Message>>>>() {

			@Override
			protected Task<List<Map<ChatInfo, List<Message>>>> createTask() {
				return new Task<List<Map<ChatInfo, List<Message>>>>() {

					protected List<Map<ChatInfo, List<Message>>> call() {

						List<Map<ChatInfo, List<Message>>> ret = new ArrayList<>();
						System.out.println("funktioniert beginRequestingNewInfo");

						for (Entry<String, ChatInfo> eachEntry : model.getChatThreads().entrySet()) {

							List<Message> messageslist = new ArrayList<>();
							try {
								messageslist = myNetwork.requestMessage(eachEntry.getKey());
							} catch (ServerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ret.add(Map.of(eachEntry.getValue(), messageslist));

						}

						return ret;
					}
				};
			};

		};

	}

}
