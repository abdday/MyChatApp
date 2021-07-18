package appController;

import data.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class FriendViewController {

	@FXML
	private ImageView friend_img;

	@FXML
	private Label name_lb;

	@FXML
	private Label last_lb;
	@FXML
	private Label data_lb;
	@FXML
	private Label sender_lb;

	public void setFriendData(User friend) {
		name_lb.setText(friend.getName());
		// last_lb.setText(friend.getLastMessage());
		last_lb.setText("nothing");
		sender_lb.setText("ahmed");
		data_lb.setText("gestern");

	}

}
