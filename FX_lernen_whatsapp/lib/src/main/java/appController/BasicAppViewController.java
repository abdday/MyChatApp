package appController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import data.ChatInfo;
import data.EMessageOwner;
import data.EMessageType;
import data.LabledMessage;
import data.Message;
import data.MyDataSet;
import data.User;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import myExceptions.FieldEmptyException;
import myExceptions.ServerException;

public class BasicAppViewController implements PropertyChangeListener {

	private AppController controller;

	private ChatInfo currentChatInfo;
	// TODO
	// private MyDataSet model;

	public void setAppController(AppController controller) {
		this.controller = controller;
		controller.beginRequestingNewInfo();
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private BorderPane rootPane;

	@FXML
	private MenuBar menuBar;

	@FXML
	private BorderPane outerCenter;

	@FXML
	private HBox chatHeader;

	@FXML
	private ImageView userImage;

	@FXML
	private Label firendName;

	@FXML
	private Label friendUsername;

	@FXML
	private HBox chatFooter;

	@FXML
	private Button attachBtn;

	@FXML
	private TextField messageField;

	@FXML
	private Button sendBtn;

	@FXML
	private ScrollPane chatCenterScroll;

	@FXML
	private VBox chatCenter;

	@FXML
	private Label myNameLabel;

	@FXML
	private Label myUserNameLabel;

	@FXML
	private Button addFriendBtn;

	@FXML
	private Button removeFriendBtn;

	@FXML
	private ListView<Pane> friendsList;

	private ObservableList<Pane> list;

	@FXML
	private Button refreschFriendsBtn;

	@FXML
	void initialize() {
		assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'BasicAppView.fxml'.";
		assert menuBar != null : "fx:id=\"menuBar\" was not injected: check your FXML file 'BasicAppView.fxml'.";
		assert friendsList != null
				: "fx:id=\"friendsList\" was not injected: check your FXML file 'BasicAppView.fxml'.";
		assert outerCenter != null
				: "fx:id=\"outerCenter\" was not injected: check your FXML file 'BasicAppView.fxml'.";
		assert chatHeader != null : "fx:id=\"chatHeader\" was not injected: check your FXML file 'BasicAppView.fxml'.";
		assert userImage != null : "fx:id=\"userImage\" was not injected: check your FXML file 'BasicAppView.fxml'.";
		// assert userName != null : "fx:id=\"userName\" was not injected: check your
		// FXML file 'BasicAppView.fxml'.";
		assert chatFooter != null : "fx:id=\"chatFooter\" was not injected: check your FXML file 'BasicAppView.fxml'.";
		assert attachBtn != null : "fx:id=\"attachBtn\" was not injected: check your FXML file 'BasicAppView.fxml'.";
		assert messageField != null
				: "fx:id=\"messageField\" was not injected: check your FXML file 'BasicAppView.fxml'.";
		assert sendBtn != null : "fx:id=\"sendBtn\" was not injected: check your FXML file 'BasicAppView.fxml'.";
		assert chatCenterScroll != null
				: "fx:id=\"chatCenterScroll\" was not injected: check your FXML file 'BasicAppView.fxml'.";
		assert chatCenter != null : "fx:id=\"chatCenter\" was not injected: check your FXML file 'BasicAppView.fxml'.";

		outerCenter.setVisible(false);

		System.out.println("intialized");

		setButtonsGraphic();
		prepareListview();

	}

	private void setButtonsGraphic() {

		/*
		 * BackgroundImage backgroundImage = new BackgroundImage( new
		 * Image(getClass().getResource("mail_senden.png").toExternalForm()),
		 * BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
		 * BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT); Background background =
		 * new Background(backgroundImage);
		 * 
		 * Image paperClipImage = new
		 * Image(getClass().getResourceAsStream("paper_clip.png"));
		 * attachBtn.setGraphic(new ImageView(paperClipImage));
		 * 
		 * sendBtn.setBackground(background);
		 */
		Image sendenImage = new Image(App.class.getResourceAsStream("mail_senden.png"));
		ImageView sendIV = new ImageView(sendenImage);

		sendIV.setFitHeight(35);
		sendIV.setFitWidth(35);

		sendBtn.setGraphic(sendIV);

		Image paperClipImage = new Image(App2_forTesting.class.getResourceAsStream("paper_clip.png"));
		ImageView attachIV = new ImageView(paperClipImage);
		attachIV.setFitHeight(35);
		attachIV.setFitWidth(35);

		attachBtn.setGraphic(attachIV);

		Image refreshImage = new Image(App.class.getResourceAsStream("refresh.png"));
		ImageView refreshIV = new ImageView(refreshImage);
		refreshIV.setFitHeight(25);
		refreshIV.setFitWidth(25);

		refreschFriendsBtn.setGraphic(refreshIV);

	}

	@FXML
	public void sendHandel(ActionEvent e) {

		try {
			addMessageToView(messageField.getText());
			controller.sendStringMessage(friendUsername.getText(), messageField.getText());
			messageField.clear();

		} catch (ServerException | FieldEmptyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			showError(e1.getMessage());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			showError("error : cannot connect to server.\nDetails " + e1.getMessage());
		}

		System.out.println("send jaa");
	}

	private void addMessageToView(String message) {
		System.out.println("add message to view jaa");

		HBox hBox = controller.setNewMessageIntoData(currentChatInfo, new Message<String>(message, EMessageType.TEXT),
				EMessageOwner.ME);

//		HBox hBox = new HBox();
//		hBox.setAlignment(Pos.TOP_RIGHT);
//		Label messageLabel = new Label(message);
//		messageLabel.setWrapText(true);
//		hBox.getChildren().add(messageLabel);
//
//		messageLabel.setMaxWidth(600);
//
////		DoubleProperty num = new SimpleDoubleProperty(-100);
////		messageLabel.maxWidthProperty().add(chatCenter.widthProperty());
////		chatCenter.widthProperty().add(num);
//
//		// ChatInfo info = controller.getChatInfo(friendUsername.getText());
//		currentChatInfo.addNewMessageNode(hBox);
		System.out.println("jaa added to VBox in info");

		chatCenter.getChildren().add(hBox);

	}

	@FXML
	void addFriend(ActionEvent event) {

		Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			controller.addFreund(ShowAndGetFriendUserNameWindow(parentStage));
		} catch (ServerException | FieldEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showError(e.getMessage());
		}
		System.out.println("addd button");
	}

	@FXML
	void removeFriend(ActionEvent event) {

		controller.getMessages();

	}

	public void setModelListener(MyDataSet model) {
		System.out.println("listener is added");
		model.addListener(this);
		// this.model = model;

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("aufgerufen jaa1");
		// TODO Auto-generated method stub
		switch (evt.getPropertyName()) {
		case "myInfo": {
			System.out.println("aufgerufen ja2a");
			if (evt.getNewValue() instanceof User) {
				User user = (User) evt.getNewValue();
				showMyInfo(user);
			}
			break;
		}
		case "newFriend": {
			System.out.println("new friend fired ");
			if (evt.getNewValue() instanceof User) {
				User friend = (User) evt.getNewValue();
				addNewFriendToList(friend);
			}

			break;
		}

		}

	}

	private void showMyInfo(User user) {
		System.out.println("aufgerufen jaa3");
		myNameLabel.setText(user.getName());
		myUserNameLabel.setText(user.getUsername());
	}

	public void showError(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Fehler");
		alert.setHeaderText("There are somthing that went wrong");
		alert.setContentText(message);

		alert.show();

	}

	private String ShowAndGetFriendUserNameWindow(Stage parentStage) {
		String ret = "";
//		VBox root = new VBox();
//		root.setPadding(new Insets(40, 40, 40, 40));
//		TextField username = new TextField();
//		username.setPromptText("type your friend's username please");
//		Button addBtn = new Button("add Friend");
//		root.setSpacing(20);
//
//		root.getChildren().addAll(username, addBtn);
//
//		Scene scene = new Scene(root, 200, 200);
//
//		Stage stage = new Stage();
//		stage.initModality(Modality.APPLICATION_MODAL);
//		stage.initOwner(parentStage);
//
//		addBtn.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				// TODO Auto-generated method stub
//				Stage myStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
////				Stage ownerstage = (Stage) myStage.getOwner();
////				Window owner = myStage.getOwner();
//				myStage.close();
//			}
//		});

		FXMLLoader lod = new FXMLLoader(getClass().getResource("AddFriendView.fxml"));
		Parent root;
		try {
			root = lod.load();
			AddFriendWindowController cont = lod.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(parentStage);

			stage.setTitle("add freind");
			stage.setScene(scene);
			stage.showAndWait();
			ret = cont.getUsernameTextField().getText();
			System.out.println("ret2 value is : " + ret);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("ret value is : " + ret);

		return ret;

	}

	private void addNewFriendToList(User friend) {
		System.out.println("addNewFriendToList!");
		try {
			FXMLLoader lod = new FXMLLoader(getClass().getResource("friend_view.fxml"));
			Pane item = lod.load();
			FriendViewController cont = lod.getController();
			cont.setFriendData(friend);
			item.setId(friend.getUsername());
			friendsList.getItems().add(item);

			System.out.println("firend should be added!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void prepareListview() {
		list = FXCollections.observableArrayList();
		friendsList.setItems(list);
		friendsList.getSelectionModel().selectedItemProperty().addListener(itemSelectedListener);

	}

	final private PropertyChangeListener listenerForNewMessagesOnCurrrentThread = (PropertyChangeEvent evt) -> {

		System.out.println("listenerForNewMessagesOnCurrrentThread");

		if (evt.getPropertyName().equals("newMessage")) {
			if (evt.getNewValue() instanceof LabledMessage) {
				LabledMessage message = (LabledMessage) evt.getNewValue();
				System.out.println("before last if " + message.getFriendUsername() + " "
						+ currentChatInfo.getFriend().getUsername());
				if (message.getFriendUsername().equals(currentChatInfo.getFriend().getUsername())) {
					System.out.println("last if before funktionert");

					chatCenter.getChildren().add(message.getMessageBox());

					System.out.println("last if after funktionert");
				}
			}

		}

	};

	final private ChangeListener<Pane> itemSelectedListener = (observable, old, New) -> {
		try {
			currentChatInfo = controller.getChatInfo(New.getId(), listenerForNewMessagesOnCurrrentThread);
			if (currentChatInfo.isChatAllowed()) {

				System.out.println("children size of another ist : " + currentChatInfo.getChatNodes().size()
						+ "my old is " + chatCenter.getChildren().size());

				chatCenter.getChildren().clear();
				chatCenter.getChildren().addAll(currentChatInfo.getChatNodes());
				// info.getChatBox();

				outerCenter.setVisible(true);

				firendName.setText(currentChatInfo.getFriend().getName());
				friendUsername.setText(currentChatInfo.getFriend().getUsername());
				controller.setCurrentFriendUserNmae(currentChatInfo.getFriend().getUsername());

			} else {
				showError("Your Friends has not add you as a friend yet");
				outerCenter.setVisible(false);
			}
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showError(e.getMessage());
			outerCenter.setVisible(false);
		}

		System.out.println("\n\n item selected whoo: " + New.getId() + "\n\n\n");
	};

	@FXML
	void refreshFreinds(ActionEvent event) {
		System.out.println("refreshFreinds button");
		friendsList.getSelectionModel().clearSelection();
		outerCenter.setVisible(false);

		try {
			controller.showNewFriendsRequests();
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			System.out.println("refreshFreinds catched Exception \n\n\n");
			e.printStackTrace();
			showError(e.getMessage());
		}
	}

}
