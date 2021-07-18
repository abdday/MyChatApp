package appController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddFriendWindowController {

	@FXML
	private TextField friendUsername;

	@FXML
	void addAndClose(ActionEvent event) {
		Stage myStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//		Stage ownerstage = (Stage) myStage.getOwner();
//		Window owner = myStage.getOwner();
		myStage.close();

	}

	public TextField getUsernameTextField() {
		return this.friendUsername;
	}

}
